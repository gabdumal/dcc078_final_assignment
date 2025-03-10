/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.Manager;
import assignments.restaurant.order.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler
        implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream sendToClient = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream receiveFromClient = new ObjectInputStream(socket.getInputStream())
        ) {
            while (true) {
                Object receivedObject = receiveFromClient.readObject();

                if (receivedObject instanceof Order order) {
                    System.out.println("Pedido recebido: " + order);
                    Manager.getInstance().addOrder(order);
                    sendToClient.writeObject("Pedido recebido com sucesso!");
                }
                else {
                    System.out.println("Objeto desconhecido recebido: " + receivedObject);
                    break;
                }
            }
        }
        catch (IOException |
               ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
                System.out.println("Um cliente se desconectou.");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

