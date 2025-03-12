/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.order.Order;

import java.io.Serial;
import java.io.Serializable;

public class Request
        implements Serializable {

    @Serial
    private static final long        serialVersionUID = 1L;
    private final        Order       order;
    private final        int         orderId;
    private final        RequestType requestType;

    private Request(RequestType requestType) {
        this.requestType = requestType;
        this.order = null;
        this.orderId = 0;
    }

    private Request(RequestType requestType, Order order) {
        this.requestType = requestType;
        this.order = order;
        this.orderId = 0;
    }

    private Request(RequestType requestType, int orderId) {
        this.requestType = requestType;
        this.order = null;
        this.orderId = orderId;
    }

    public static Request advanceOrder(int orderId) {
        return new Request(RequestType.AdvanceOrder, orderId);
    }

    public static Request finishConnection() {
        return new Request(RequestType.FinishConnection);
    }

    public static Request retrieveOrders() {
        return new Request(RequestType.RetrieveOrders);
    }

    public static Request sendOrder(Order order) {
        return new Request(RequestType.SendOrder, order);
    }

    public Order getOrder() {
        return this.order;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public RequestType getRequestType() {
        return this.requestType;
    }

}
