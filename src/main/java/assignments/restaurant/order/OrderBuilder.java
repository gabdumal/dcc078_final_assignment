/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order;

import assignments.restaurant.component.CategoryType;
import assignments.restaurant.component.ComponentFacade;
import assignments.restaurant.data.MenuComponentRecord;

public class OrderBuilder {

    private Order order;

    public OrderBuilder() {
        this.reset();
    }

    public void reset() {
        this.order = new Order();
    }

    public Order build() {
        var builtOrder = this.order;
        this.reset();
        return builtOrder;
    }

    public void setAppetizer(MenuComponentRecord menuComponentRecord) {
        OrderBuilder.validateMenuComponentRecord(menuComponentRecord);
        if (menuComponentRecord.category() != CategoryType.Appetizer) {
            throw new IllegalArgumentException("The menu component must be an appetizer!");
        }

        var component = ComponentFacade.createAppetizer(
                menuComponentRecord.cuisine(),
                menuComponentRecord.name(),
                menuComponentRecord.description(),
                menuComponentRecord.cost()
                                                       );
        this.order.setAppetizer(component);
    }

    private static void validateMenuComponentRecord(MenuComponentRecord menuComponentRecord) {
        if (menuComponentRecord == null) {
            throw new IllegalArgumentException("An menu component is required!");
        }
        if (menuComponentRecord.category() == null) {
            throw new IllegalArgumentException("The menu component must have a category!");
        }
    }

    public void setBeverage(MenuComponentRecord menuComponentRecord) {
        OrderBuilder.validateMenuComponentRecord(menuComponentRecord);
        if (menuComponentRecord.category() != CategoryType.Beverage) {
            throw new IllegalArgumentException("The menu component must be a beverage!");
        }

        var component = ComponentFacade.createBeverage(
                menuComponentRecord.cuisine(),
                menuComponentRecord.name(),
                menuComponentRecord.description(),
                menuComponentRecord.cost()
                                                      );
        this.order.setBeverage(component);
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name is required!");
        }
        this.order.setCustomerName(customerName);
    }

    public void setDessert(MenuComponentRecord menuComponentRecord) {
        OrderBuilder.validateMenuComponentRecord(menuComponentRecord);
        if (menuComponentRecord.category() != CategoryType.Dessert) {
            throw new IllegalArgumentException("The menu component must be a dessert!");
        }

        var component = ComponentFacade.createDessert(
                menuComponentRecord.cuisine(),
                menuComponentRecord.name(),
                menuComponentRecord.description(),
                menuComponentRecord.cost()
                                                     );
        this.order.setDessert(component);
    }

    public void setMainCourse(MenuComponentRecord menuComponentRecord) {
        OrderBuilder.validateMenuComponentRecord(menuComponentRecord);
        if (menuComponentRecord.category() != CategoryType.MainCourse) {
            throw new IllegalArgumentException("The menu component must be a main course!");
        }

        var component = ComponentFacade.createMainCourse(
                menuComponentRecord.cuisine(),
                menuComponentRecord.name(),
                menuComponentRecord.description(),
                menuComponentRecord.cost()
                                                        );
        this.order.setMainCourse(component);
    }

}