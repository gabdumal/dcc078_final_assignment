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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ClientHandler
        implements Runnable {

    private final Consumer<Order>                       addOrder;
    private final Supplier<CopyOnWriteArrayList<Order>> getOrders;
    private final PrintStream                           serverPrintStream;
    private final Socket                                socket;
    private       ObjectInputStream                     receiveFromClient;

    public ClientHandler(
            Socket socket,
            PrintStream serverPrintStream,
            Consumer<Order> addOrder,
            Supplier<CopyOnWriteArrayList<Order>> getOrders
                        ) {
        this.socket = socket;
        this.serverPrintStream = serverPrintStream;
        this.addOrder = addOrder;
        this.getOrders = getOrders;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream sendToClient = new ObjectOutputStream(this.socket.getOutputStream());
            this.receiveFromClient = new ObjectInputStream(this.socket.getInputStream());

            while (true) {
                Object receivedObject = this.receiveFromClient.readObject();

                if (receivedObject instanceof Request request) {
                    switch (request.getRequestType()) {
                        case RetrieveOrders -> {
                            var orders = this.getOrders();
                            Response response = Response.sendOrders(orders);
                            sendToClient.writeObject(response);
                            sendToClient.flush();
                        }
                        case SendOrder -> {
                            var order = request.getOrder();
                            this.serverPrintStream.println("Pedido recebido: " + order);
                            this.addOrder(order);
                            Response response = Response.confirmReceivedOrder();
                            sendToClient.writeObject(response);
                            sendToClient.flush();
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

    private CopyOnWriteArrayList<Order> getOrders() {
        return this.getOrders.get();
    }

    private void addOrder(Order order) {
        this.addOrder.accept(order);
    }

}

