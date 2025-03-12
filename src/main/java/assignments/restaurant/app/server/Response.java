/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.order.Order;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class Response
        implements Serializable {

    @Serial
    private static final long                              serialVersionUID = 1L;
    private final        ConcurrentHashMap<Integer, Order> orders;
    private final        ResponseType                      responseType;

    private Response(ResponseType responseType, ConcurrentHashMap<Integer, Order> orders) {
        this.responseType = responseType;
        this.orders = orders;
    }

    private Response(ResponseType responseType) {
        this.responseType = responseType;
        this.orders = new ConcurrentHashMap<>();
    }

    public static Response confirmAdvancedOrder() {
        return new Response(ResponseType.ConfirmAdvancedOrder);
    }

    public static Response confirmFinishedConnection() {
        return new Response(ResponseType.confirmFinishedConnection);
    }

    public static Response confirmReceivedOrder() {
        return new Response(ResponseType.ConfirmReceivedOrder);
    }

    public static Response sendOrders(ConcurrentHashMap<Integer, Order> orders) {
        return new Response(ResponseType.SendOrders, orders);
    }

    public final ConcurrentHashMap<Integer, Order> getOrders() {
        return this.orders;
    }

    public final ResponseType getResponseType() {
        return this.responseType;
    }

}
