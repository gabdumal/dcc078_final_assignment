/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.order.Order;

import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerContext
        extends Observable {

    private final AtomicInteger                     orderCounter = new AtomicInteger(0);
    private final ConcurrentHashMap<Integer, Order> orders       = new ConcurrentHashMap<>();

    protected synchronized void addOrder(Order order) {
        int orderId = this.orderCounter.incrementAndGet();
        this.orders.put(orderId, order);
        this.setChanged();
        this.notifyObservers();
    }

    protected synchronized void advanceOrder(int orderId) {
        var order = this.orders.get(orderId);
        if (null != order) {
            order.advance();
        }
        this.setChanged();
        this.notifyObservers();
    }

    protected synchronized ConcurrentHashMap<Integer, Order> getOrders() {
        return this.orders;
    }

}
