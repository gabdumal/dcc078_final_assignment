/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class UserInterface {

    protected ObjectInputStream  receiveFromServer;
    protected BufferedReader     scanner;
    protected ObjectOutputStream sendToServer;

    protected static boolean isValidOption(String option, int size) {
        try {
            int parsedOption = Integer.parseInt(option);
            return parsedOption >= 1 && parsedOption <= size;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    protected abstract UserInterfaceType getUserInterfaceType();

    public void start(BufferedReader scanner, ObjectInputStream receiveFromServer, ObjectOutputStream sendToServer) {
        this.scanner = scanner;
        this.receiveFromServer = receiveFromServer;
        this.sendToServer = sendToServer;

        this.run();
    }

    protected abstract void run();

}
