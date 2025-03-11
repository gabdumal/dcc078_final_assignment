/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.Manager;

import java.io.*;
import java.net.Socket;

public class Client {

    private static final String        customerInterface       = "-c";
    private static final String        employeeInterface       = "-e";
    private static final String        invalidArgumentsMessage =
            "Você deve fornecer uma interface de usuário válida: " + employeeInterface + " (funcionário) ou " +
            customerInterface + " (cliente)!";
    private static       PrintStream   clientPrintStream       = System.out;
    private static       UserInterface userInterface;

    public static void main(String[] args) {
        Manager manager = Manager.getInstance();

        try (
                Socket socket = new Socket(manager.getHost(), manager.getSocketPort());
                ObjectOutputStream sendToServer = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream receiveFromServer = new ObjectInputStream(socket.getInputStream());
                BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in))
        ) {
            var userInterfaceType = processArguments(args);
            switch (userInterfaceType) {
                case Customer -> {
                    userInterface = new Customer(scanner, receiveFromServer, sendToServer, clientPrintStream);
                    userInterface.run();
                }
                case Employee -> {
                    userInterface = new Employee(scanner, receiveFromServer, sendToServer, clientPrintStream);
                    userInterface.run();
                }
                default -> throw new IllegalArgumentException("Tipo de interface não suportado.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static UserInterfaceType processArguments(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException(invalidArgumentsMessage);
        }

        return switch (args[0]) {
            case "-e" -> UserInterfaceType.Employee;
            case "-c" -> UserInterfaceType.Customer;
            default -> throw new IllegalArgumentException(invalidArgumentsMessage);
        };
    }

    public static void setClientPrintStream(PrintStream printStream) {
        Client.clientPrintStream = printStream;
    }

}