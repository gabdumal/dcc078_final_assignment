/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.app.server.Request;
import assignments.restaurant.app.server.Response;
import assignments.restaurant.app.server.ResponseType;
import assignments.restaurant.component.Decorator;
import assignments.restaurant.component.MenuComponent;
import assignments.restaurant.order.Order;

import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Employee
        extends UserInterface {

    public Employee(
            BufferedReader scanner,
            ObjectInputStream receiveFromServer,
            ObjectOutputStream sendToServer,
            PrintStream clientPrintStream
                   ) {
        super(scanner, receiveFromServer, sendToServer, clientPrintStream);
    }

    @Override
    protected UserInterfaceType getUserInterfaceType() {
        return UserInterfaceType.Employee;
    }

    @Override
    public void run() {
        clientPrintStream.println("Boas-vindas ao Restaurante!");

        try {
            this.pickOrder();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pickOrder()
            throws IOException {
        clientPrintStream.println("Pedidos:");
        var orders = this.fetchOrders();
        this.printOrders(orders);
    }

    private CopyOnWriteArrayList<Order> fetchOrders()
            throws IOException {

        Request request = Request.retrieveOrders();
        sendToServer.writeObject(request);
        sendToServer.flush();

        try {
            Object receivedObject = receiveFromServer.readObject();
            if (receivedObject instanceof Response response) {
                if (response.getResponseType() == ResponseType.SendOrders) {
                    return response.getOrders();
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new CopyOnWriteArrayList<>();
    }

    protected void printOrders(CopyOnWriteArrayList<Order> orders) {
        if (orders.isEmpty()) {
            clientPrintStream.println("Nenhum pedido encontrado.");
            return;
        }

        for (
                int i = 0;
                i < orders.size();
                i++
        ) {
            var order = orders.get(i);
            clientPrintStream.print((i + 1) + ". ");
            this.printOrder(order);
        }
    }

    protected void printOrder(Order order) {
        clientPrintStream.println(order.getCustomerName());
        this.printMenuComponent(order.getAppetizer());
        this.printMenuComponent(order.getMainCourse());
        this.printMenuComponent(order.getBeverage());
        this.printMenuComponent(order.getDessert());
    }

    protected void printMenuComponent(MenuComponent menuComponent) {
        clientPrintStream.print("     " + menuComponent.getName());
        clientPrintStream.print(" - R$");
        clientPrintStream.print(menuComponent.getCost());

        if (menuComponent instanceof Decorator) {
            clientPrintStream.print(" - Extras:");
        }
        while (menuComponent instanceof Decorator) {
            clientPrintStream.print(" " + ((Decorator) menuComponent).getDecorationName() + ".");
            menuComponent = ((Decorator) menuComponent).getDecorated();
        }

        clientPrintStream.println();
    }

}
