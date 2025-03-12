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
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseSender
        implements Observer {

    private final ObjectOutputStream sendToClient;
    private final PrintStream        serverPrintStream;
    private final Socket             socket;

    public ResponseSender(Socket socket, PrintStream serverPrintStream) {
        this.serverPrintStream = serverPrintStream;

        this.socket = socket;
        try {
            this.sendToClient = new ObjectOutputStream(this.socket.getOutputStream());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void confirmAdvancedOrder()
            throws IOException {
        Response response = Response.confirmAdvancedOrder();
        this.sendToClient(this.sendToClient, response);
    }

    private void sendToClient(ObjectOutputStream sendToClient, Response response)
            throws IOException {
        if (!this.socket.isClosed() && this.socket.isConnected()) {
            sendToClient.writeObject(response);
            sendToClient.flush();
        }
    }

    protected void confirmReceivedOrder()
            throws IOException {
        Response response = Response.confirmReceivedOrder();
        this.sendToClient(this.sendToClient, response);
    }

    protected void sendOrders(ConcurrentHashMap<Integer, Order> orders)
            throws IOException {
        Response response = Response.sendOrders(orders);
        this.sendToClient(this.sendToClient, response);
    }

    @Override
    public void update(Observable serverContext, Object arg) {
        this.serverPrintStream.println("Sending response to client...");
        this.serverPrintStream.println(serverContext);
        this.serverPrintStream.println(arg);
    }

}
