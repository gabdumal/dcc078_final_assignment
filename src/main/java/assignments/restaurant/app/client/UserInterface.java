/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.component.Decorator;
import assignments.restaurant.component.MenuComponent;
import assignments.restaurant.data.MenuComponentRecord;
import assignments.restaurant.order.Order;

import java.io.*;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

/*
 * Design Pattern: Template Method
 *
 * This class is part of the Template Method design pattern.
 * It defines the template method run() and the abstract methods greet(), authenticate(), interact(), and finish() to manage the user interfaces.
 */
public abstract class UserInterface {

    protected final PrintStream        clientPrintStream;
    protected final ObjectInputStream  receiveFromServer;
    protected final BufferedReader     scanner;
    protected final ObjectOutputStream sendToServer;

    public UserInterface(
            BufferedReader scanner,
            ObjectInputStream receiveFromServer,
            ObjectOutputStream sendToServer,
            PrintStream clientPrintStream
                        ) {
        this.scanner = scanner;
        this.receiveFromServer = receiveFromServer;
        this.sendToServer = sendToServer;
        this.clientPrintStream = clientPrintStream;
    }

    protected static boolean isValidOption(String option, int size) {
        try {
            int parsedOption = Integer.parseInt(option);
            return 1 <= parsedOption && parsedOption <= size;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    protected static boolean isValidOption(String option, Integer[] validOptions) {
        try {
            int parsedOption = Integer.parseInt(option);
            return Arrays.stream(validOptions).anyMatch(v -> v == parsedOption);
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    protected abstract UserInterfaceType getUserInterfaceType();

    protected void printMenuComponent(MenuComponentRecord menuComponentRecord) {
        this.clientPrintStream.println(menuComponentRecord.name());
        this.clientPrintStream.println("     R$" + menuComponentRecord.cost());
        this.clientPrintStream.println("     " + menuComponentRecord.description());
    }

    protected void printOrder(Order order) {
        this.clientPrintStream.print(order.getStateType());
        this.clientPrintStream.print(": ");
        this.clientPrintStream.print(order.getCustomerName());
        this.clientPrintStream.print(" - R$");
        this.clientPrintStream.print(order.getTotalCost());
        this.clientPrintStream.print(" via ");
        this.clientPrintStream.println(order.getPaymentType());

        this.printMenuComponent(order.getAppetizer());
        this.printMenuComponent(order.getMainCourse());
        this.printMenuComponent(order.getBeverage());
        this.printMenuComponent(order.getDessert());
    }

    protected void printMenuComponent(MenuComponent menuComponent) {
        this.clientPrintStream.print("     " + menuComponent.getName());
        this.clientPrintStream.print(" - R$");
        this.clientPrintStream.print(menuComponent.getCost());

        if (menuComponent instanceof Decorator) {
            this.clientPrintStream.print(" - Extras:");
        }
        while (menuComponent instanceof Decorator) {
            this.clientPrintStream.print(" " + ((Decorator) menuComponent).getDecorationName() + ".");
            menuComponent = ((Decorator) menuComponent).getDecorated();
        }

        this.clientPrintStream.println();
    }

    protected String readString(
            String askMessage,
            String afterErrorMessage,
            Function<String, String> process,
            Predicate<String> validate
                               )
            throws IOException {
        if (null != askMessage) {
            this.clientPrintStream.println(askMessage);
        }
        String typedText = process.apply(this.scanner.readLine());
        while (!validate.test(typedText)) {
            if (null != afterErrorMessage) {
                this.clientPrintStream.println(afterErrorMessage);
            }
            typedText = process.apply(this.scanner.readLine());
        }
        return typedText;
    }

    protected void run() {
        try {
            this.greet();
            this.clientPrintStream.println();
            this.authenticate();
            this.clientPrintStream.println();
            this.interact();
            this.clientPrintStream.println();
            this.finish();
            this.clientPrintStream.println();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void greet()
            throws IOException;

    protected abstract void authenticate()
            throws IOException;

    protected abstract void interact()
            throws IOException;

    protected abstract void finish()
            throws IOException;

}
