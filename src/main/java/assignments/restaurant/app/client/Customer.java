/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.app.server.Request;
import assignments.restaurant.component.CategoryType;
import assignments.restaurant.cuisine.CuisineType;
import assignments.restaurant.data.*;
import assignments.restaurant.order.Order;
import assignments.restaurant.order.OrderBuilder;
import assignments.restaurant.order.category.OrderCategoryType;
import assignments.restaurant.order.payment.Cash;
import assignments.restaurant.order.payment.CreditCard;
import assignments.restaurant.order.payment.PaymentType;
import assignments.restaurant.order.payment.Pix;

import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

public class Customer
        extends UserInterface {

    private CuisineType cuisineType;
    private String customerName;
    private OrderBuilder orderBuilder;

    public Customer(
            BufferedReader scanner,
            ObjectInputStream receiveFromServer,
            ObjectOutputStream sendToServer,
            PrintStream clientPrintStream
                   ) {
        super(scanner, receiveFromServer, sendToServer, clientPrintStream);
    }

    @Override
    protected UserInterfaceType getUserInterfaceType() {
        return UserInterfaceType.Customer;
    }

    @Override
    protected void greet() {
        this.clientPrintStream.println("Boas-vindas à interface de pedidos do Restaurante!");
    }

    @Override
    protected void authenticate()
            throws IOException {
        this.customerName = this.readString(
                "Qual é seu nome?",
                "Por favor, insira um nome válido.",
                String::strip,
                s -> null != s && !s.isBlank()
                                           );
    }

    @Override
    protected void interact()
            throws IOException {
        this.makeOrder();
    }

    @Override
    protected void finish() {
        this.clientPrintStream.println("Foi um prazer atendê-lo! Até a próxima.");
    }

    private void makeOrder()
            throws IOException {
        this.pickOrderCategory();
        this.clientPrintStream.println();

        this.orderBuilder.setCustomerName(this.customerName);
        this.pickCuisine();
        this.clientPrintStream.println();

        this.pickMenuComponent(CategoryType.Appetizer);
        this.clientPrintStream.println();
        this.decorateMenuComponent(CategoryType.Appetizer);
        this.clientPrintStream.println();
        this.pickMenuComponent(CategoryType.MainCourse);
        this.clientPrintStream.println();
        this.decorateMenuComponent(CategoryType.MainCourse);
        this.clientPrintStream.println();
        this.pickMenuComponent(CategoryType.Beverage);
        this.clientPrintStream.println();
        this.decorateMenuComponent(CategoryType.Beverage);
        this.clientPrintStream.println();
        this.pickMenuComponent(CategoryType.Dessert);
        this.clientPrintStream.println();
        this.decorateMenuComponent(CategoryType.Dessert);
        this.clientPrintStream.println();

        this.pickPaymentStrategy();
        this.clientPrintStream.println();

        Order order = this.orderBuilder.build();
        Request request = Request.sendOrder(order);
        this.sendToServer.writeObject(request);
        this.sendToServer.flush();

        this.clientPrintStream.println("Seu pedido foi recebido com sucesso!");
    }

    private void pickOrderCategory()
            throws IOException {
        this.clientPrintStream.println("Tipos de pedido:");
        this.clientPrintStream.println("1. " + OrderCategoryType.Delivery);
        this.clientPrintStream.println("2. " + OrderCategoryType.Takeaway);
        this.clientPrintStream.println("3. " + OrderCategoryType.DineIn);

        var orderCategoryOption = this.readString(
                "Digite o número do tipo de pedido que deseja:",
                "Por favor, escolha uma opção válida.",
                String::strip,
                s -> null != s && !s.isBlank() && Customer.isValidOption(s, 3)
                                                 );

        var orderCategoryType = switch (orderCategoryOption) {
            case "1" -> OrderCategoryType.Delivery;
            case "2" -> OrderCategoryType.Takeaway;
            case "3" -> OrderCategoryType.DineIn;
            default -> throw new IllegalStateException("Unexpected value: " + orderCategoryOption);
        };

        this.orderBuilder = new OrderBuilder(orderCategoryType);
    }

    private void pickCuisine()
            throws IOException {
        this.clientPrintStream.println("Culinárias:");
        this.clientPrintStream.println("1. " + CuisineType.Brazilian);
        this.clientPrintStream.println("2. " + CuisineType.Italian);

        var cuisineOption = this.readString(
                "Escolha a culinária que deseja pedir:",
                "Por favor, escolha uma opção válida.",
                String::strip,
                s -> null != s && !s.isBlank() && Customer.isValidOption(s, 2)
                                           );

        this.cuisineType = cuisineOption.equals("1") ? CuisineType.Brazilian : CuisineType.Italian;
    }

    private void pickMenuComponent(
            CategoryType categoryType
                                  )
            throws IOException {
        this.clientPrintStream.println(categoryType.toString() + ":");

        var menuComponentsRecords = Query.fetchAllMenuComponents(
                RestrictByCuisine.convertCuisineType(this.cuisineType),
                RestrictByCategory.convertCategoryType(categoryType),
                RestrictByDecorator.IsNotDecorator
                                                                );
        this.printMenu(menuComponentsRecords);

        var menuComponentOption = this.menuComponentValidationLoop(menuComponentsRecords);
        var pickedMenuComponentOption = menuComponentsRecords.get(Integer.parseInt(menuComponentOption) - 1);

        BiConsumer<OrderBuilder, MenuComponentRecord> builderMethod = this.getOrderBuilderForMenuComponent(categoryType);
        builderMethod.accept(this.orderBuilder, pickedMenuComponentOption);
    }

    private void decorateMenuComponent(
            CategoryType categoryType
                                      )
            throws IOException {
        var wishToDecorate = this.readString(
                "Deseja adicionar algum acompanhamento? (S/N)",
                "Por favor, escolha uma opção válida.",
                String::strip,
                s -> s.equalsIgnoreCase("S") || s.equalsIgnoreCase("N")
                                            );
        this.clientPrintStream.println();

        if (wishToDecorate.equalsIgnoreCase("S")) {
            this.clientPrintStream.println("Acompanhamentos:");

            var menuComponentsDecoratorsRecords = Query.fetchAllMenuComponents(
                    RestrictByCuisine.convertCuisineType(this.cuisineType),
                    RestrictByCategory.convertCategoryType(categoryType),
                    RestrictByDecorator.IsDecorator
                                                                              );
            this.printMenu(menuComponentsDecoratorsRecords);

            var menuComponentDecoratorOption = this.menuComponentValidationLoop(menuComponentsDecoratorsRecords);

            var pickedMenuComponentDecorator = menuComponentsDecoratorsRecords.get(
                    Integer.parseInt(menuComponentDecoratorOption) - 1);
            BiConsumer<OrderBuilder, MenuComponentRecord> builderMethod = this.getOrderBuilderForMenuComponentDecorator(
                    categoryType);
            builderMethod.accept(this.orderBuilder, pickedMenuComponentDecorator);
        }
    }

    private void pickPaymentStrategy()
            throws IOException {
        this.clientPrintStream.println("Métodos de pagamento:");
        this.clientPrintStream.println("1. " + PaymentType.CreditCard);
        this.clientPrintStream.println("2. " + PaymentType.Pix);
        this.clientPrintStream.println("3. " + PaymentType.Cash);

        var paymentOption = this.readString(
                "Escolha um método de pagamento:",
                "Por favor, escolha uma opção válida.",
                String::strip,
                s -> null != s && !s.isBlank() && Customer.isValidOption(s, 3)
                                           );

        var paymentStrategy = switch (paymentOption) {
            case "1" -> this.typeCreditCardNumber();
            case "2" -> new Pix();
            case "3" -> new Cash();
            default -> throw new IllegalStateException("Unexpected value: " + paymentOption);
        };
        this.orderBuilder.setPaymentStrategy(paymentStrategy);
    }

    private void printMenu(CopyOnWriteArrayList<MenuComponentRecord> menu) {
        for (
                int i = 0;
                i < menu.size();
                i++
        ) {
            var menuComponentRecord = menu.get(i);
            this.clientPrintStream.print((i + 1) + ". ");
            this.printMenuComponent(menuComponentRecord);
        }
    }

    private String menuComponentValidationLoop(CopyOnWriteArrayList<MenuComponentRecord> menuComponentsRecords)
            throws IOException {
        return this.readString(
                "Digite o número do item que deseja:",
                "Por favor, escolha uma opção válida.",
                String::strip,
                s -> null != s && !s.isBlank() && Customer.isValidOption(s, menuComponentsRecords.size())
                              );
    }

    private BiConsumer<OrderBuilder, MenuComponentRecord> getOrderBuilderForMenuComponent(
            CategoryType categoryType
                                                                                         ) {
        BiConsumer<OrderBuilder, MenuComponentRecord> builderMethod;
        switch (categoryType) {
            case Appetizer -> builderMethod = OrderBuilder::setAppetizer;
            case Beverage -> builderMethod = OrderBuilder::setBeverage;
            case Dessert -> builderMethod = OrderBuilder::setDessert;
            case MainCourse -> builderMethod = OrderBuilder::setMainCourse;
            default -> throw new IllegalStateException("Unexpected value: " + categoryType);
        }
        return builderMethod;
    }

    private BiConsumer<OrderBuilder, MenuComponentRecord> getOrderBuilderForMenuComponentDecorator(
            CategoryType categoryType
                                                                                                  ) {
        BiConsumer<OrderBuilder, MenuComponentRecord> builderMethod;
        switch (categoryType) {
            case Appetizer -> builderMethod = OrderBuilder::decorateAppetizer;
            case Beverage -> builderMethod = OrderBuilder::decorateBeverage;
            case Dessert -> builderMethod = OrderBuilder::decorateDessert;
            case MainCourse -> builderMethod = OrderBuilder::decorateMainCourse;
            default -> throw new IllegalStateException("Unexpected value: " + categoryType);
        }
        return builderMethod;
    }

    private CreditCard typeCreditCardNumber()
            throws IOException {
        var creditCardNumber = this.readString(
                "Digite o número do cartão de crédito:",
                "Digite um número de cartão de crédito válido.",
                s -> s.replaceAll("\\s+", ""),
                s -> 16 == s.length() && s.matches("[0-9]+")
                                              );
        return new CreditCard(creditCardNumber);
    }

}
