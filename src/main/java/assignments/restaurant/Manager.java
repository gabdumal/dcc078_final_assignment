/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant;

import assignments.restaurant.order.Order;

import java.util.concurrent.CopyOnWriteArrayList;

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

    private static Manager                     instance;
    private final  CopyOnWriteArrayList<Order> orders = new CopyOnWriteArrayList<>();

    private Manager() {
    }

    public static synchronized Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    public synchronized void addOrder(Order order) {
        orders.add(order);
    }

    public synchronized String getHost() {
        return "localhost";
    }

    public synchronized int getMaximumOfClients() {
        return 5;
    }

    public synchronized int getSocketPort() {
        return 29100;
    }

}
