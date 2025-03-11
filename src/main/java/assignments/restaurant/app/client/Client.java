/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import java.io.*;
import java.net.Socket;

public class Client {

    private static final String      customerInterface = "-c";
    private static final String      employeeInterface = "-e";
    private static final String      invalidArgumentsMessage = "Você deve fornecer uma interface de usuário válida: " +
                                                               employeeInterface + " (funcionário) ou " +
                                                               customerInterface + " (cliente)!";
    private static       PrintStream clientPrintStream = System.out;

    protected Client() {
    }

    public static void main(String[] args) {
        var arguments = processArguments(args);
        new Client().run(arguments.getHost(), arguments.getPort(), arguments.getUserInterface());
    }

    protected static ClientArguments processArguments(String[] args) {
        if (3 != args.length) {
            throw new IllegalArgumentException("Você deve fornecer um host, uma porta e uma interface de usuário!");
        }

        String host = getHost(args[0]);
        int port = ClientArguments.findPort(args[1]);

        UserInterfaceType userInterfaceType = switch (args[2]) {
            case "-e" -> UserInterfaceType.Employee;
            case "-c" -> UserInterfaceType.Customer;
            default -> throw new IllegalArgumentException(invalidArgumentsMessage);
        };

        return new ClientArguments(host, port, userInterfaceType);
    }

    protected void run(String host, int port, UserInterfaceType userInterfaceType) {
        try {
            Socket socket = new Socket(host, port);

            ObjectOutputStream sendToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream receiveFromServer = new ObjectInputStream(socket.getInputStream());
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

            UserInterface userInterface = getUserInterface(
                    userInterfaceType,
                    scanner,
                    receiveFromServer,
                    sendToServer,
                    clientPrintStream
                                                          );

            userInterface.run();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getHost(String host) {
        if (null == host || host.isEmpty()) {
            throw new IllegalArgumentException("O host não pode ser nulo ou vazio!");
        }
        return host;
    }

    protected static UserInterface getUserInterface(
            UserInterfaceType userInterfaceType,
            BufferedReader scanner,
            ObjectInputStream receiveFromServer,
            ObjectOutputStream sendToServer,
            PrintStream clientPrintStream
                                                   ) {
        UserInterface userInterface;
        switch (userInterfaceType) {
            case Customer -> {
                userInterface = new Customer(scanner, receiveFromServer, sendToServer, clientPrintStream);
            }
            case Employee -> {
                userInterface = new Employee(scanner, receiveFromServer, sendToServer, clientPrintStream);
            }
            default -> throw new IllegalArgumentException("Tipo de interface não suportado.");
        }
        return userInterface;
    }

    public static void setClientPrintStream(PrintStream printStream) {
        Client.clientPrintStream = printStream;
    }

}