/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.order.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ClientHandler
        implements Runnable {

    private final Consumer<Order>                             addOrder;
    private final Consumer<Integer>                           advanceOrder;
    private final Supplier<ConcurrentHashMap<Integer, Order>> getOrders;
    private final ObjectInputStream                           receiveFromClient;
    private final ResponseSender                              responseSender;
    private final PrintStream                                 serverPrintStream;
    private final Socket                                      socket;

    public ClientHandler(
            Socket socket,
            PrintStream serverPrintStream,
            Consumer<Order> addOrder,
            Supplier<ConcurrentHashMap<Integer, Order>> getOrders,
            Consumer<Integer> advanceOrder
                        ) {
        this.serverPrintStream = serverPrintStream;
        this.addOrder = addOrder;
        this.getOrders = getOrders;
        this.advanceOrder = advanceOrder;

        this.responseSender = new ResponseSender(socket, serverPrintStream);

        this.socket = socket;
        try {
            this.receiveFromClient = new ObjectInputStream(this.socket.getInputStream());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object receivedObject = this.receiveFromClient.readObject();
                if (receivedObject instanceof Request request) {
                    switch (request.getRequestType()) {
                        case RetrieveOrders -> {
                            var orders = this.getOrders();
                            this.responseSender.sendOrders(orders);
                        }
                        case SendOrder -> {
                            var order = request.getOrder();
                            this.serverPrintStream.println("Pedido recebido: " + order);
                            this.addOrder(order);
                            this.responseSender.confirmReceivedOrder();
                        }
                        case AdvanceOrder -> {
                            var orderId = request.getOrderId();
                            this.serverPrintStream.println("Pedido progredido: " + orderId);
                            this.advanceOrder(orderId);
                            this.responseSender.confirmAdvancedOrder();
                        }
                        default -> {
                            this.serverPrintStream.println("Requisição desconhecida recebida: " + request);
                            break;
                        }
                    }
                }
                else {
                    this.serverPrintStream.println("Objeto desconhecido recebido: " + receivedObject);
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                this.receiveFromClient.close();
                this.socket.close();
                this.serverPrintStream.println("Um cliente se desconectou.");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ConcurrentHashMap<Integer, Order> getOrders() {
        return this.getOrders.get();
    }

    private void addOrder(Order order) {
        this.addOrder.accept(order);
    }

    private void advanceOrder(Integer orderId) {
        this.advanceOrder.accept(orderId);
    }

}

