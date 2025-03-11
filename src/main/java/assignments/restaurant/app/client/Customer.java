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

    private void decorateMenuComponent(
            CategoryType categoryType
                                      )
            throws IOException {
        clientPrintStream.println("Deseja adicionar algum acompanhamento? (S/N)");
        var wishToDecorate = scanner.readLine();

        while (!wishToDecorate.equalsIgnoreCase("S") && !wishToDecorate.equalsIgnoreCase("N")) {
            clientPrintStream.println("Por favor, escolha uma opção válida.");
            wishToDecorate = scanner.readLine();
        }

        if (wishToDecorate.equalsIgnoreCase("S")) {
            clientPrintStream.println("Escolha um acompanhamento:");

            var menuComponentsDecoratorsRecords = Query.fetchAllMenuComponents(
                    RestrictByCuisine.convertCuisineType(this.cuisineType),
                    RestrictByCategory.convertCategoryType(categoryType),
                    RestrictByDecorator.IsDecorator
                                                                              );
            this.printMenu(menuComponentsDecoratorsRecords);

            String menuComponentDecoratorOption = this.menuComponentValidationLoop(menuComponentsDecoratorsRecords);

            var pickedMenuComponentDecorator = menuComponentsDecoratorsRecords.get(
                    Integer.parseInt(menuComponentDecoratorOption) - 1);
            BiConsumer<OrderBuilder, MenuComponentRecord> builderMethod = getOrderBuilderForMenuComponentDecorator(
                    categoryType);
            builderMethod.accept(this.orderBuilder, pickedMenuComponentDecorator);
            clientPrintStream.println();
        }
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

    private void makeOrder()
            throws IOException {
        this.orderBuilder.setCustomerName(customerName);
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
        sendToServer.writeObject(request);
        sendToServer.flush();

        clientPrintStream.println("Seu pedido foi recebido com sucesso!");
        clientPrintStream.println();
    }

    private String menuComponentValidationLoop(CopyOnWriteArrayList<MenuComponentRecord> menuComponentsRecords)
            throws IOException {
        String menuComponentOption = this.scanner.readLine();
        while (menuComponentOption == null || menuComponentOption.isBlank() ||
               !Customer.isValidOption(menuComponentOption, menuComponentsRecords.size())) {
            clientPrintStream.println("Por favor, escolha uma opção válida.");
            menuComponentOption = this.scanner.readLine();
        }
        return menuComponentOption;
    }

    private void pickCuisine()
            throws IOException {
        clientPrintStream.println("Escolha a culinária para montar seu pedido:");
        clientPrintStream.println("1. " + CuisineType.Brazilian);
        clientPrintStream.println("2. " + CuisineType.Italian);

        String cuisineOption = scanner.readLine();
        while (!cuisineOption.equals("1") && !cuisineOption.equals("2")) {
            clientPrintStream.println("Por favor, escolha uma opção válida.");
            cuisineOption = scanner.readLine();
        }

        this.cuisineType = cuisineOption.equals("1") ? CuisineType.Brazilian : CuisineType.Italian;
        clientPrintStream.println();
    }

    private void pickMenuComponent(
            CategoryType categoryType
                                  )
            throws IOException {
        clientPrintStream.println(categoryType.toString() + ":");

        var menuComponentsRecords = Query.fetchAllMenuComponents(
                RestrictByCuisine.convertCuisineType(this.cuisineType),
                RestrictByCategory.convertCategoryType(categoryType),
                RestrictByDecorator.IsNotDecorator
                                                                );
        this.printMenu(menuComponentsRecords);

        String menuComponentOption = this.menuComponentValidationLoop(menuComponentsRecords);

        var pickedMenuComponentOption = menuComponentsRecords.get(Integer.parseInt(menuComponentOption) - 1);
        BiConsumer<OrderBuilder, MenuComponentRecord> builderMethod = getOrderBuilderForMenuComponent(categoryType);
        builderMethod.accept(this.orderBuilder, pickedMenuComponentOption);
        clientPrintStream.println();
    }

    private void printMenu(CopyOnWriteArrayList<MenuComponentRecord> menu) {
        for (
                int i = 0;
                i < menu.size();
                i++
        ) {
            var menuComponentRecord = menu.get(i);
            clientPrintStream.print((i + 1) + ". ");
            this.printMenuComponent(menuComponentRecord);
        }
    }

    private void printMenuComponent(MenuComponentRecord menuComponentRecord) {
        clientPrintStream.println(menuComponentRecord.name());
        clientPrintStream.println("     R$" + menuComponentRecord.cost());
        clientPrintStream.println("     " + menuComponentRecord.description());
    }

    @Override
    public void run() {
        clientPrintStream.println("Boas-vindas ao Restaurante!");
        this.orderBuilder = new OrderBuilder();

        try {
            this.setAccount();
            this.makeOrder();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected UserInterfaceType getUserInterfaceType() {
        return UserInterfaceType.Customer;
    }

    protected void setAccount()
            throws IOException {
        clientPrintStream.println("Qual é seu nome?");

        String customerName = scanner.readLine();
        while (customerName == null || customerName.isBlank()) {
            clientPrintStream.println("Por favor, insira um nome válido.");
            customerName = scanner.readLine();
        }
        this.customerName = customerName;
        clientPrintStream.println();
    }

}
