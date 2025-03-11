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

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Employee
        extends UserInterface {

    @Override
    protected UserInterfaceType getUserInterfaceType() {
        return UserInterfaceType.Employee;
    }

    @Override
    public void run() {
        System.out.println("Boas-vindas ao Restaurante!");

        try {
            this.pickOrder();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pickOrder()
            throws IOException {
        System.out.println("Pedidos:");
        this.printOrders();
    }

    private void printOrders()
            throws IOException {
        var orders = this.fetchOrders();

        if (orders.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        for (
                int i = 0;
                i < orders.size();
                i++
        ) {
            var order = orders.get(i);
            System.out.print((i + 1) + ". ");
            Employee.printOrder(order);
        }
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

    private static void printOrder(Order order) {
        System.out.println(order.getCustomerName());
        Employee.printMenuComponent(order.getAppetizer());
    }

    private static void printMenuComponent(MenuComponent menuComponent) {
        System.out.print(menuComponent.getName());
        System.out.print(" - R$");
        System.out.print(menuComponent.getCost());

        if (menuComponent instanceof Decorator) {
            System.out.print(" - Extras:");
        }
        while (menuComponent instanceof Decorator) {
            System.out.print(" " + ((Decorator) menuComponent).getDecorationName() + ".");
            menuComponent = ((Decorator) menuComponent).getDecorated();
        }
    }

}
