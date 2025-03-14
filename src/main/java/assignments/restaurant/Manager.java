/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant;

/*
 * Design Pattern: Singleton
 *
 * This class implements the Singleton design pattern to manage restaurant operations.
 * Only one instance of this class can exist at any time.
 */

/**
 * Singleton class to manage restaurant operations.
 */
public class Manager {

    private static Manager instance;

    private Manager() {
    }

    public static synchronized Manager getInstance() {
        if (null == instance) {
            instance = new Manager();
        }
        return instance;
    }

    public synchronized int getDefaultSocketPort() {
        return 29100;
    }

    public synchronized String getHost() {
        return "localhost";
    }

    public synchronized int getMaximumOfClients() {
        return 5;
    }

}
