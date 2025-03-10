/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler
        implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {

            while (true) {
                String order = in.readLine();
                if (order == null || order.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.println("Received Order: " + order);
                Manager.getInstance().addOrder(order);

                out.println("Order Received: " + order);
            }

            System.out.println("Client disconnected.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
