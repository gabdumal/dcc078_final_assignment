/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order;

import assignments.restaurant.component.Appetizer;
import assignments.restaurant.component.Beverage;
import assignments.restaurant.component.Dessert;
import assignments.restaurant.component.MainCourse;
import assignments.restaurant.order.state.OrderContext;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an order in the restaurant.
 */
public class Order
        implements Serializable {

    @Serial
    private static final long       serialVersionUID = 1L;
    private              Appetizer  appetizer;
    private              Beverage   beverage;
    private              String     customerName;
    private              Dessert    dessert;
    private              MainCourse mainCourse;

    /**
     * Protected constructor to prevent direct instantiation except for the builder.
     */
    protected Order() {
        OrderContext context = new OrderContext();
    }

    /**
     * Gets the appetizer of the order.
     *
     * @return The appetizer.
     */
    public Appetizer getAppetizer() {
        return this.appetizer;
    }

    /**
     * Sets the appetizer of the order.
     *
     * @param appetizer The appetizer to set.
     */
    protected void setAppetizer(Appetizer appetizer) {
        this.appetizer = appetizer;
    }

    /**
     * Gets the beverage of the order.
     *
     * @return The beverage.
     */
    public Beverage getBeverage() {
        return this.beverage;
    }

    /**
     * Sets the beverage of the order.
     *
     * @param beverage The beverage to set.
     */
    protected void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    /**
     * Gets the customer name of the order.
     *
     * @return The customer name.
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * Sets the customer name of the order.
     *
     * @param customerName The customer name to set.
     */
    protected void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the dessert of the order.
     *
     * @return The dessert.
     */
    public Dessert getDessert() {
        return this.dessert;
    }

    /**
     * Sets the dessert of the order.
     *
     * @param dessert The dessert to set.
     */
    protected void setDessert(Dessert dessert) {
        this.dessert = dessert;
    }

    /**
     * Gets the main course of the order.
     *
     * @return The main course.
     */
    public MainCourse getMainCourse() {
        return this.mainCourse;
    }

    /**
     * Sets the main course of the order.
     *
     * @param mainCourse The main course to set.
     */
    protected void setMainCourse(MainCourse mainCourse) {
        this.mainCourse = mainCourse;
    }

    public String toString() {
        return "{" + "Cliente: \"" + this.customerName + "\", " + "Entrada: " + this.appetizer + ", " +
               "Prato principal: " + this.mainCourse + ", " + "Bebida: " + this.beverage + ", " + "Sobremesa: " +
               this.dessert + "}";
    }

}