/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.order.Order;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Design Pattern: Observer
 *
 * This class is part of the Observer design pattern.
 * It keeps track of the server context, in order to update the orders list on the listening clients.
 */

public class ResponseSender
        implements Observer {

    private final ObjectOutputStream sendToClient;
    private final ServerContext      serverContext;
    private final PrintStream        serverPrintStream;

    public ResponseSender(ObjectOutputStream sendToClient, ServerContext serverContext, PrintStream serverPrintStream) {
        this.sendToClient = sendToClient;
        this.serverContext = serverContext;
        this.serverPrintStream = serverPrintStream;
        this.serverContext.addObserver(this);
    }

    protected void confirmAdvancedOrder()
            throws IOException {
        Response response = Response.confirmAdvancedOrder();
        this.send(this.sendToClient, response);
    }

    private void send(ObjectOutputStream sendToClient, Response response)
            throws IOException {
        sendToClient.writeObject(response);
        sendToClient.flush();
    }

    protected void confirmFinishedConnection()
            throws IOException {
        Response response = Response.confirmFinishedConnection();
        this.send(this.sendToClient, response);
        //        this.serverPrintStream.println("Conexão finalizada.");
    }

    protected void confirmReceivedOrder()
            throws IOException {
        Response response = Response.confirmReceivedOrder();
        this.send(this.sendToClient, response);
    }

    @Override
    public void update(Observable serverContext, Object object) {
        try {
            var orders = this.serverContext.getOrders();
            var clonedOrders = new ConcurrentHashMap<>(orders);
            this.sendOrders(clonedOrders);
        }
        catch (IOException e) {
            this.serverPrintStream.println("Erro ao enviar pedidos para o cliente.");
        }
    }

    protected void sendOrders(ConcurrentHashMap<Integer, Order> orders)
            throws IOException {
        Response response = Response.sendOrders(orders);
        this.send(this.sendToClient, response);
    }

}
