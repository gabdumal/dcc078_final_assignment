/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order.category;

import assignments.restaurant.order.Order;

/*
 * Design Pattern: Factory Method
 *
 * This class is part of the Factory Method design pattern.
 * It represents a factory for creating orders of the categories: DineIn, Delivery, and Takeaway.
 */

public class OrderFactory {

    public static Order create(OrderCategoryType categoryType) {
        return switch (categoryType) {
            case DineIn -> new DineInOrder();
            case Delivery -> new DeliveryOrder();
            case Takeaway -> new TakeawayOrder();
            default -> throw new IllegalArgumentException("Tipo de pedido inválido!");
        };
    }

}
