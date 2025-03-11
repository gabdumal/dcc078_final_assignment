/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.Manager;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final Manager         manager           = Manager.getInstance();
    private static final ExecutorService threadPool        = Executors.newFixedThreadPool(manager.getMaximumOfClients());
    private static       PrintStream     serverPrintStream = System.out;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(manager.getSocketPort())) {
            serverPrintStream.println(
                    "O servidor do Restaurante está rodando na porta " + manager.getSocketPort() + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                serverPrintStream.println("Um novo cliente se conectou.");

                // ✅ Run the client handler in a separate thread
                threadPool.execute(new ClientHandler(clientSocket, serverPrintStream));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setServerPrintStream(PrintStream printStream) {
        Server.serverPrintStream = printStream;
    }

}

