/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app;

public abstract class ConnectionArguments {

    private final int port;

    public ConnectionArguments(int port) {
        this.port = port;
    }

    public static int findPort(String portAsString) {
        if (null == portAsString || portAsString.isEmpty()) {
            throw new IllegalArgumentException("A porta não pode ser nula ou vazia!");
        }
        int port;
        try {
            port = Integer.parseInt(portAsString);
            if (1 > port || 65535 < port) {
                throw new IllegalArgumentException("A porta deve ser um número inteiro entre 1 e 65535!");
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("A porta deve ser um número inteiro!");
        }
        return port;
    }

    public int getPort() {
        return this.port;
    }

}