/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.order.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler
        implements Runnable {

    private final ObjectInputStream receiveFromClient;
    private final ResponseSender    responseSender;
    private final ServerContext     serverContext;
    private final PrintStream       serverPrintStream;
    private final Socket            socket;

    public ClientHandler(Socket socket, ServerContext serverContext, PrintStream serverPrintStream) {
        this.serverPrintStream = serverPrintStream;

        this.serverContext = serverContext;
        this.socket = socket;
        try {
            var sendToClient = new ObjectOutputStream(this.socket.getOutputStream());
            this.receiveFromClient = new ObjectInputStream(this.socket.getInputStream());
            this.responseSender = new ResponseSender(sendToClient, serverContext, serverPrintStream);
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
                        case FinishConnection -> {
                            this.responseSender.confirmFinishedConnection();
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
        return this.serverContext.getOrders();
    }

    private void addOrder(Order order) {
        this.serverContext.addOrder(order);
    }

    private void advanceOrder(Integer orderId) {
        this.serverContext.advanceOrder(orderId);
    }

}

