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
import java.util.concurrent.ConcurrentHashMap;

public class Employee
        extends UserInterface {

    private String employeeName;

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
    protected void greet() {
        this.clientPrintStream.println("Boas-vindas à interface de gerenciamento do Restaurante!");
    }

    @Override
    protected void authenticate()
            throws IOException {
        this.employeeName = this.readString(
                "Qual é seu nome?",
                "Por favor, insira um nome válido.",
                String::strip,
                s -> null != s && !s.isBlank()
                                           );
    }

    @Override
    protected void interact()
            throws IOException {
        this.advanceOrder();
    }

    @Override
    protected void finish() {
        this.clientPrintStream.println("Agradecemos por usar a interface de gerenciamento do Restaurante!");
    }

    private void advanceOrder()
            throws IOException {
        var orders = this.fetchOrders();
        this.printOrders(orders);

        var keys = orders.keySet().toArray(new Integer[0]);
        var orderOption = this.readString(
                "Escolha o pedido que deseja avançar:",
                "Por favor, escolha um pedido válido:",
                String::strip,
                s -> null != s && !s.isBlank() && Employee.isValidOption(s, keys)
                                         );
        Order pickedOrder = orders.get(Integer.parseInt(orderOption));
    }

    private ConcurrentHashMap<Integer, Order> fetchOrders()
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

        return new ConcurrentHashMap<>();
    }

    protected void printOrders(ConcurrentHashMap<Integer, Order> orders) {
        this.clientPrintStream.println("Pedidos:");

        if (orders.isEmpty()) {
            this.clientPrintStream.println("Nenhum pedido encontrado.");
            return;
        }

        for (var entry : orders.entrySet()) {
            int orderId = entry.getKey();
            Order order = entry.getValue();
            this.clientPrintStream.print(orderId + ". ");
            this.printOrder(order);
        }
    }

}
