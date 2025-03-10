/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static final String        customerInterface       = "-c";
    private static final String        employeeInterface       = "-e";
    private static final String        invalidArgumentsMessage = "You must provide a valid interface: " +
                                                                 employeeInterface + " (employer) or " +
                                                                 customerInterface + " (customer)!";
    private static       UserInterface userInterface;

    public static void main(String[] args) {
        Manager manager = Manager.getInstance();
        try (
                Socket socket = new Socket(manager.getHost(), manager.getSocketPort());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            var userInterfaceType = Client.processArguments(args);
            switch (userInterfaceType) {
                //                case Employee -> userInterface = new Employee();
                case Customer -> userInterface = new Customer();
            }
            Client.userInterface.start(consoleInput, out, in);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static UserInterfaceType processArguments(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException(Client.invalidArgumentsMessage);
        }

        var interfaceType = args[0];
        if (interfaceType.equals(Client.employeeInterface)) {
            System.out.println("Employee Interface");
            return UserInterfaceType.Employee;
        }
        else if (interfaceType.equals(Client.customerInterface)) {
            System.out.println("Customer Interface");
            return UserInterfaceType.Customer;
        }
        else {
            throw new IllegalArgumentException(Client.invalidArgumentsMessage);
        }
    }

}
