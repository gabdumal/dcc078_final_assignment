/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app;

import assignments.restaurant.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static final String customerInterface       = "-c";
    private static final String employeeInterface       = "-e";
    private static final String invalidArgumentsMessage = "You must provide a valid interface: " + employeeInterface +
                                                          " (employer) or " + customerInterface + " (customer)!";

    public static void main(String[] args) {
        Manager manager = Manager.getInstance();
        try (
                Socket socket = new Socket(manager.getHost(), manager.getSocketPort());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))
        ) {

            if (args.length != 1) {
                throw new IllegalArgumentException(Client.invalidArgumentsMessage);
            }

            var interfaceType = args[0];
            if (interfaceType.equals(Client.employeeInterface)) {
                System.out.println("Restaurant Interface");
                Client.loop(in, out, consoleInput);
            }
            else if (interfaceType.equals(Client.customerInterface)) {
                System.out.println("Customer Interface");
                Client.loop(in, out, consoleInput);
            }
            else {
                throw new IllegalArgumentException(Client.invalidArgumentsMessage);
            }

            System.out.println(in.readLine()); // Welcome message
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loop(BufferedReader in, PrintWriter out, BufferedReader consoleInput) {
        try {
            while (true) {
                System.out.print("Enter order (or 'exit' to quit): ");
                String order = consoleInput.readLine();
                if (order.equalsIgnoreCase("exit")) {
                    break;
                }

                out.println(order);
                System.out.println("Server: " + in.readLine()); // Response from restaurant
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
