/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app;

import assignments.restaurant.Manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final Manager         manager    = Manager.getInstance();
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(manager.getMaximumOfClients());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(manager.getSocketPort())) {
            System.out.println("Restaurant Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected!");

                // Handle each client in a new thread
                Server.threadPool.execute(new ClientHandler(clientSocket));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
