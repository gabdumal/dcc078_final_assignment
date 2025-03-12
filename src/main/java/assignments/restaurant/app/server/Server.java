/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.Manager;
import assignments.restaurant.order.Order;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static PrintStream     serverPrintStream = System.out;
    private final  ServerContext   serverContext;
    private final  ExecutorService threadPool;

    public Server() {
        this.threadPool = Executors.newFixedThreadPool(Manager.getInstance().getMaximumOfClients());
        this.serverContext = new ServerContext();
    }

    public static void main(String[] args) {
        var arguments = processArguments(args);
        new Server().run(arguments.getPort());
    }

    protected static ServerArguments processArguments(String[] args) {
        if (1 != args.length) {
            throw new IllegalArgumentException("Você deve fornecer uma porta!");
        }

        int port = ServerArguments.findPort(args[0]);

        return new ServerArguments(port);
    }

    public static void setServerPrintStream(PrintStream printStream) {
        Server.serverPrintStream = printStream;
    }

    public ConcurrentHashMap<Integer, Order> getOrders() {
        return this.serverContext.getOrders();
    }

    public int run(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            this.runSocket(serverSocket);
        }
        catch (IOException e) {
            if (e.getMessage().contains("Address already in use")) {
                serverPrintStream.println("A porta " + port + " já está em uso. Tentando outra porta...");
                return this.run(port + 1);
            }
            else {
                e.printStackTrace();
            }
        }
        return port;
    }

    protected void runSocket(ServerSocket serverSocket) {
        try {
            serverPrintStream.println(
                    "O servidor do Restaurante está rodando na porta " + serverSocket.getLocalPort() + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serverPrintStream.println("Um novo cliente se conectou.");

                // Run the client handler in a separate thread
                this.threadPool.execute(new ClientHandler(clientSocket, this.serverContext, serverPrintStream));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

