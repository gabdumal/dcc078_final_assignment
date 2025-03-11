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

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

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

    protected abstract void run();

}
