/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.app.ConnectionArguments;

public final class ClientArguments
        extends ConnectionArguments {

    private final String            host;
    private final UserInterfaceType userInterface;

    ClientArguments(String host, int port, UserInterfaceType userInterface) {
        super(port);
        this.host = host;
        this.userInterface = userInterface;
    }

    public String getHost() {
        return this.host;
    }

    public UserInterfaceType getUserInterface() {
        return this.userInterface;
    }

}