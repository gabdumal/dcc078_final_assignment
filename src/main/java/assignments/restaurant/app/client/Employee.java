/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.app.server.Request;
import assignments.restaurant.app.server.Response;
import assignments.restaurant.app.server.ResponseType;
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
        this.clientPrintStream.println("Boas-vindas ao Restaurante!");

        try {
            this.advanceOrder();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void advanceOrder()
            throws IOException {
        this.clientPrintStream.println("Digite o número do pedido que deseja avançar:");

        var orders = this.fetchOrders();
        this.printOrders(orders);

        String orderOption = this.orderValidationLoop(orders);
        Order pickedOrder = orders.get(Integer.parseInt(orderOption) - 1);
    }

    private CopyOnWriteArrayList<Order> fetchOrders()
            throws IOException {

        Request request = Request.retrieveOrders();
        this.sendToServer.writeObject(request);
        this.sendToServer.flush();

        try {
            Object receivedObject = this.receiveFromServer.readObject();
            if (receivedObject instanceof Response response) {
                if (ResponseType.SendOrders == response.getResponseType()) {
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
            this.clientPrintStream.println("Nenhum pedido encontrado.");
            return;
        }

        for (
                int i = 0;
                i < orders.size();
                i++
        ) {
            var order = orders.get(i);
            this.clientPrintStream.print((i + 1) + ". ");
            this.printOrder(order);
        }
    }

    private String orderValidationLoop(CopyOnWriteArrayList<Order> orders)
            throws IOException {
        String orderOption = this.scanner.readLine();
        while (null == orderOption || orderOption.isBlank() || !Employee.isValidOption(orderOption, orders.size())) {
            this.clientPrintStream.println("Por favor, escolha uma opção válida.");
            orderOption = this.scanner.readLine();
        }
        return orderOption;
    }

}
