/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.Manager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler
        implements Runnable {

    private final PrintStream        serverPrintStream;
    private final Socket             socket;
    private       ObjectInputStream  receiveFromClient;
    private       ObjectOutputStream sendToClient;

    public ClientHandler(Socket socket, PrintStream serverPrintStream) {
        this.socket = socket;
        this.serverPrintStream = serverPrintStream;
    }

    @Override
    public void run() {
        try {
            this.sendToClient = new ObjectOutputStream(socket.getOutputStream());
            this.receiveFromClient = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Object receivedObject = receiveFromClient.readObject();

                if (receivedObject instanceof Request request) {
                    switch (request.getRequestType()) {
                        case RetrieveOrders -> {
                            var orders = Manager.getInstance().getOrders();
                            Response response = Response.sendOrders(orders);
                            sendToClient.writeObject(response);
                            sendToClient.flush();
                        }
                        case SendOrder -> {
                            var order = request.getOrder();
                            serverPrintStream.println("Pedido recebido: " + order);
                            Manager.getInstance().addOrder(order);
                            Response response = Response.confirmReceivedOrder();
                            sendToClient.writeObject(response);
                            sendToClient.flush();
                        }
                        default -> {
                            serverPrintStream.println("Requisição desconhecida recebida: " + request);
                            break;
                        }
                    }
                }
                else {
                    serverPrintStream.println("Objeto desconhecido recebido: " + receivedObject);
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                receiveFromClient.close();
                socket.close();
                serverPrintStream.println("Um cliente se desconectou.");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

