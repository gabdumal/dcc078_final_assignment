/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import java.io.BufferedReader;
import java.io.PrintWriter;

public abstract class UserInterface {

    protected BufferedReader receiveFromServer;
    protected BufferedReader scanner;
    protected PrintWriter    sendToServer;

    protected abstract UserInterfaceType getUserInterfaceType();

    public void start(BufferedReader scanner, PrintWriter sendToServer, BufferedReader receiveFromServer) {
        this.scanner = scanner;
        this.sendToServer = sendToServer;
        this.receiveFromServer = receiveFromServer;

        this.run();
    }

    protected abstract void run();

}
