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

import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

public class Customer
        extends UserInterface {

    private CuisineType  cuisineType;
    private String       customerName;
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
    public void run() {
        this.clientPrintStream.println("Boas-vindas ao Restaurante!");

        try {
            this.setAccount();
            this.makeOrder();
            this.keepUpOrder();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setAccount()
            throws IOException {
        this.clientPrintStream.println("Qual é seu nome?");

        String customerName = this.scanner.readLine();
        while (null == customerName || customerName.isBlank()) {
            this.clientPrintStream.println("Por favor, insira um nome válido.");
            customerName = this.scanner.readLine();
        }
        this.customerName = customerName;
        this.clientPrintStream.println();
    }

    private void makeOrder()
            throws IOException {
        this.pickOrderCategory();

        this.orderBuilder.setCustomerName(this.customerName);
        this.pickCuisine();

        this.pickMenuComponent(CategoryType.Appetizer);
        this.decorateMenuComponent(CategoryType.Appetizer);
        this.pickMenuComponent(CategoryType.MainCourse);
        this.decorateMenuComponent(CategoryType.MainCourse);
        this.pickMenuComponent(CategoryType.Beverage);
        this.decorateMenuComponent(CategoryType.Beverage);
        this.pickMenuComponent(CategoryType.Dessert);
        this.decorateMenuComponent(CategoryType.Dessert);

        Order order = this.orderBuilder.build();
        Request request = Request.sendOrder(order);
        this.sendToServer.writeObject(request);
        this.sendToServer.flush();

        this.clientPrintStream.println("Seu pedido foi recebido com sucesso!");
        this.clientPrintStream.println();
    }

    private void keepUpOrder() {
        //        this.clientPrintStream.println("Acompanhe seu pedido:");
    }

    private void pickOrderCategory()
            throws IOException {
        this.clientPrintStream.println("Escolha o tipo de pedido:");
        this.clientPrintStream.println("1. " + OrderCategoryType.Delivery);
        this.clientPrintStream.println("2. " + OrderCategoryType.Takeaway);
        this.clientPrintStream.println("3. " + OrderCategoryType.DineIn);

        String orderCategoryOption = this.scanner.readLine();
        while (!orderCategoryOption.equals("1") && !orderCategoryOption.equals("2") &&
               !orderCategoryOption.equals("3")) {
            this.clientPrintStream.println("Por favor, escolha uma opção válida.");
            orderCategoryOption = this.scanner.readLine();
        }

        var orderCategoryType = switch (orderCategoryOption) {
            case "1" -> OrderCategoryType.Delivery;
            case "2" -> OrderCategoryType.Takeaway;
            case "3" -> OrderCategoryType.DineIn;
            default -> throw new IllegalStateException("Unexpected value: " + orderCategoryOption);
        };

        this.orderBuilder = new OrderBuilder(orderCategoryType);
        this.clientPrintStream.println();
    }

    private void pickCuisine()
            throws IOException {
        this.clientPrintStream.println("Escolha a culinária para montar seu pedido:");
        this.clientPrintStream.println("1. " + CuisineType.Brazilian);
        this.clientPrintStream.println("2. " + CuisineType.Italian);

        String cuisineOption = this.scanner.readLine();
        while (!cuisineOption.equals("1") && !cuisineOption.equals("2")) {
            this.clientPrintStream.println("Por favor, escolha uma opção válida.");
            cuisineOption = this.scanner.readLine();
        }

        this.cuisineType = cuisineOption.equals("1") ? CuisineType.Brazilian : CuisineType.Italian;
        this.clientPrintStream.println();
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

        String menuComponentOption = this.menuComponentValidationLoop(menuComponentsRecords);
        var pickedMenuComponentOption = menuComponentsRecords.get(Integer.parseInt(menuComponentOption) - 1);

        BiConsumer<OrderBuilder, MenuComponentRecord> builderMethod = this.getOrderBuilderForMenuComponent(categoryType);
        builderMethod.accept(this.orderBuilder, pickedMenuComponentOption);

        this.clientPrintStream.println();
    }

    private void decorateMenuComponent(
            CategoryType categoryType
                                      )
            throws IOException {
        this.clientPrintStream.println("Deseja adicionar algum acompanhamento? (S/N)");
        var wishToDecorate = this.scanner.readLine();

        while (!wishToDecorate.equalsIgnoreCase("S") && !wishToDecorate.equalsIgnoreCase("N")) {
            this.clientPrintStream.println("Por favor, escolha uma opção válida.");
            wishToDecorate = this.scanner.readLine();
        }

        if (wishToDecorate.equalsIgnoreCase("S")) {
            this.clientPrintStream.println("Escolha um acompanhamento:");

            var menuComponentsDecoratorsRecords = Query.fetchAllMenuComponents(
                    RestrictByCuisine.convertCuisineType(this.cuisineType),
                    RestrictByCategory.convertCategoryType(categoryType),
                    RestrictByDecorator.IsDecorator
                                                                              );
            this.printMenu(menuComponentsDecoratorsRecords);

            String menuComponentDecoratorOption = this.menuComponentValidationLoop(menuComponentsDecoratorsRecords);

            var pickedMenuComponentDecorator = menuComponentsDecoratorsRecords.get(
                    Integer.parseInt(menuComponentDecoratorOption) - 1);
            BiConsumer<OrderBuilder, MenuComponentRecord> builderMethod = this.getOrderBuilderForMenuComponentDecorator(
                    categoryType);
            builderMethod.accept(this.orderBuilder, pickedMenuComponentDecorator);
            this.clientPrintStream.println();
        }
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
        String menuComponentOption = this.scanner.readLine();
        while (null == menuComponentOption || menuComponentOption.isBlank() ||
               !Customer.isValidOption(menuComponentOption, menuComponentsRecords.size())) {
            this.clientPrintStream.println("Por favor, escolha uma opção válida.");
            menuComponentOption = this.scanner.readLine();
        }
        return menuComponentOption;
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

}
