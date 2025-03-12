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
import java.util.concurrent.*;

public class Employee
        extends UserInterface {

    private final ExecutorService executor;
    private final Thread interactiveThread;
    private final Thread listenerThread;
    private       boolean askedForFinish = false;
    private       String employeeName;
    private       ConcurrentHashMap<Integer, Order> orders;
    private       boolean running        = true;

    public Employee(
            BufferedReader scanner,
            ObjectInputStream receiveFromServer,
            ObjectOutputStream sendToServer,
            PrintStream clientPrintStream
                   ) {
        super(scanner, receiveFromServer, sendToServer, clientPrintStream);
        this.executor = Executors.newSingleThreadExecutor();
        this.orders = new ConcurrentHashMap<>();

        this.listenerThread = new Thread(() -> {
            try {
                while (this.running) {
                    this.listenForUpdates();
                }
            }
            catch (IOException |
                   ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        this.interactiveThread = new Thread(() -> {
            try {
                while (!this.askedForFinish) {
                    this.interactionLoop();
                }
            }
            catch (InterruptedException |
                   IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void listenForUpdates()
            throws IOException, ClassNotFoundException {

        Future<Object> future = this.executor.submit(this.receiveFromServer::readObject);

        Object receivedObject = null;
        try {
            receivedObject = future.get(5, TimeUnit.SECONDS);
        }
        catch (TimeoutException e) {
            future.cancel(true); // Cancel the task
        }
        catch (ExecutionException |
               InterruptedException e) {
            e.printStackTrace();
        }

        if (receivedObject instanceof Response response) {
            if (ResponseType.SendOrders == response.getResponseType()) {
                var orders = response.getOrders();
                this.orders = new ConcurrentHashMap<>(orders);
                this.printOrders(this.orders);
            }
            if (ResponseType.confirmFinishedConnection == response.getResponseType()) {
                this.running = false;
            }
        }
        try {
            Thread.sleep(1000);  // Wait for updates
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void interactionLoop()
            throws IOException, InterruptedException {
        var orders = new ConcurrentHashMap<>(this.orders);
        Integer[] keys;

        if (orders.isEmpty()) {
            try {
                Thread.sleep(1000);  // Wait for updates
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            keys = new Integer[]{0};
        }
        else {
            keys = orders.keySet().toArray(new Integer[0]);
        }

        int orderId = this.pickOrder(keys);

        if (0 == orderId) {
            this.finishConnection();
            return;
        }

        this.advanceOrder(orderId);
    }

    protected void printOrders(ConcurrentHashMap<Integer, Order> orders) {
        this.clientPrintStream.println();
        this.clientPrintStream.println("=-= PEDIDOS =-=");
        this.clientPrintStream.println();

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

    private void finishConnection()
            throws IOException {
        this.askedForFinish = true;
        Request request = Request.finishConnection();
        this.sendToServer.writeObject(request);
        this.sendToServer.flush();
    }

    private void advanceOrder(int orderId)
            throws IOException {
        Request request = Request.advanceOrder(orderId);
        this.sendToServer.writeObject(request);
        this.sendToServer.flush();
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
        this.listenerThread.start();
        this.retrieveOrders();

        this.clientPrintStream.println("Escolha o pedido cujo estado deseja atualizar (0 para sair).");
        this.clientPrintStream.println();

        this.interactiveThread.start();

        while (true) {
            if (!this.running) {
                this.executor.shutdown();
                this.executor.close();
                return;
            }
        }
    }

    @Override
    protected void finish() {
        try {
            this.listenerThread.join();
            this.interactiveThread.join();
            this.clientPrintStream.println("Agradecemos por usar a interface de gerenciamento do Restaurante!");
            //            System.out.println("Finish!");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void retrieveOrders()
            throws IOException {
        Request request = Request.retrieveOrders();
        this.sendToServer.writeObject(request);
        this.sendToServer.flush();
    }

}
