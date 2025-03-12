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

    protected static int                               betweenLoopsDelay = 0;
    private          String                            employeeName;
    private          ConcurrentHashMap<Integer, Order> orders;
    private volatile boolean                           running           = true;

    public Employee(
            BufferedReader scanner,
            ObjectInputStream receiveFromServer,
            ObjectOutputStream sendToServer,
            PrintStream clientPrintStream
                   ) {
        super(scanner, receiveFromServer, sendToServer, clientPrintStream);
        this.orders = new ConcurrentHashMap<>();
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
        Thread listenerThread = new Thread(this::listenForUpdates);
        listenerThread.start();

        this.retrieveOrders();

        this.clientPrintStream.println("Escolha o pedido cujo estado deseja atualizar (X para sair).");
        this.clientPrintStream.println();

        while (this.running) {
            try {
                this.interactionLoop();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void finish() {
        this.clientPrintStream.println("Agradecemos por usar a interface de gerenciamento do Restaurante!");
        this.running = false;
    }

    private void listenForUpdates() {
        while (this.running) {
            try {
                Object receivedObject = this.receiveFromServer.readObject();
                if (receivedObject instanceof Response response &&
                    ResponseType.SendOrders == response.getResponseType()) {
                    var orders = response.getOrders();
                    this.orders = new ConcurrentHashMap<>(orders);
                    this.printOrders(this.orders);
                }
            }
            catch (IOException |
                   ClassNotFoundException e) {
                if (this.running) {
                    e.printStackTrace();
                }
                this.running = false;
            }
        }
    }

    private void retrieveOrders()
            throws IOException {
        Request request = Request.retrieveOrders();
        this.sendToServer.writeObject(request);
        this.sendToServer.flush();
    }

    private void interactionLoop()
            throws IOException, InterruptedException {
        var orders = new ConcurrentHashMap<>(this.orders);
        if (orders.isEmpty()) {
            try {
                Thread.sleep(1000);  // Wait for updates
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        var keys = orders.keySet().toArray(new Integer[0]);
        int orderId = this.pickOrder(keys);

        if (0 == orderId) {
            this.running = false;
            return;
        }

        this.advanceOrder(orderId);

        Thread.sleep(betweenLoopsDelay);
    }

    protected void printOrders(ConcurrentHashMap<Integer, Order> orders) {
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

    private Integer pickOrder(Integer[] orderIds)
            throws IOException {
        var orderOption = this.readString(
                null,
                null,
                String::strip,
                s -> null != s && !s.isBlank() &&
                     (Employee.isValidOption(s, orderIds) || "0".equals(s))
                                         );
        return Integer.parseInt(orderOption);
    }

    private void advanceOrder(int orderId)
            throws IOException {
        Request request = Request.advanceOrder(orderId);
        this.sendToServer.writeObject(request);
        this.sendToServer.flush();
    }

}
