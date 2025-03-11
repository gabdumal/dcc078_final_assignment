/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.order.Order;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class Response
        implements Serializable {

    @Serial
    private static final long                        serialVersionUID = 1L;
    private final        CopyOnWriteArrayList<Order> orders;
    private final        ResponseType                responseType;

    private Response(ResponseType responseType, CopyOnWriteArrayList<Order> orders) {
        this.responseType = responseType;
        this.orders = orders;
    }

    public static Response confirmReceivedOrder() {
        return new Response(ResponseType.ConfirmReceivedOrder, null);
    }

    public static Response sendOrders(CopyOnWriteArrayList<Order> orders) {
        return new Response(ResponseType.SendOrders, orders);
    }

    public CopyOnWriteArrayList<Order> getOrders() {
        return this.orders;
    }

    public ResponseType getResponseType() {
        return this.responseType;
    }

}
