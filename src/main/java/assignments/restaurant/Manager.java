/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

/*
 * Design Pattern: Singleton
 *
 * This class implements the Singleton design pattern to manage restaurant operations.
 * Only one instance of this class can exist at any time.
 */

package assignments.restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to manage restaurant operations.
 */
public class Manager {

    private static Manager      instance;
    private final  List<String> orders = new ArrayList<>();

    private Manager() {
    }

    public static synchronized Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    public synchronized void addOrder(String order) {
        orders.add(order);
        System.out.println("Current Orders: " + orders);
    }

    public String getHost() {
        return "localhost";
    }

    public int getMaximumOfClients() {
        return 5;
    }

    public int getSocketPort() {
        return 12345;
    }

}
