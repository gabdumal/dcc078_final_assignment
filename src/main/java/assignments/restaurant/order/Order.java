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
import assignments.restaurant.order.category.OrderCategoryType;
import assignments.restaurant.order.payment.OrderPaymentContext;
import assignments.restaurant.order.payment.PaymentStrategy;
import assignments.restaurant.order.payment.PaymentType;
import assignments.restaurant.order.state.OrderStateContext;
import assignments.restaurant.order.state.StateType;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an order in the restaurant.
 */
public abstract class Order
        implements Serializable {

    @Serial
    private static final long                serialVersionUID = 1L;
    private final        OrderPaymentContext paymentContext;
    private final        OrderStateContext   stateContext;
    private              Appetizer           appetizer;
    private              Beverage            beverage;
    private              String              customerName;
    private              Dessert             dessert;
    private              MainCourse          mainCourse;

    /**
     * Protected constructor to prevent direct instantiation except for the builder.
     */
    protected Order() {
        this.paymentContext = new OrderPaymentContext();
        this.stateContext = new OrderStateContext();
    }

    /**
     * Advances the state of the order to the next state.
     */
    public void advance() {
        this.stateContext.advance();
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

    /**
     * Processes the payment for the order.
     *
     * @return A string indicating the result of the payment process.
     */
    public String pay() {
        return this.paymentContext.pay(this.getTotalCost());
    }

    /**
     * Calculates the total cost of the order.
     *
     * @return The total cost of the order, which is the sum of the costs of the appetizer, main course, beverage, and dessert.
     */
    public double getTotalCost() {
        return this.appetizer.getCost() + this.mainCourse.getCost() + this.beverage.getCost() + this.dessert.getCost();
    }

    /**
     * Sets the payment strategy for the order.
     *
     * @param paymentStrategy The payment strategy to set.
     */
    protected void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentContext.setPaymentStrategy(paymentStrategy);
    }

    /**
     * Returns a string representation of the order.
     *
     * @return A string representation of the order, including its status, customer name, appetizer, main course, beverage, and dessert.
     */
    @Override
    public String toString() {
        return "{" + "Status: \"" + this.getStateType() + "\", " + "Tipo: \"" + this.getCategory() + "\", " +
               "Cliente: \"" + this.customerName + "\", " + "Custo: " + this.getTotalCost() + ", " + "Pagamento: \"" +
               this.getPaymentType() + "\", " + "Entrada: " + this.appetizer + ", " + "Prato principal: " +
               this.mainCourse + ", " + "Bebida: " + this.beverage + ", " + "Sobremesa: " + this.dessert + "}";
    }

    /**
     * Gets the current state of the order.
     *
     * @return The current state of the order as an StateType.
     */
    public StateType getStateType() {
        return this.stateContext.getStateType();
    }

    /**
     * Gets the category of the order, whether it is a dine-in, delivery, or takeout.
     *
     * @return The category of the order as an OrderCategoryType.
     */
    public abstract OrderCategoryType getCategory();

    /**
     * Gets the current payment strategy of the order.
     *
     * @return The current payment strategy of the order as an PaymentType.
     */
    public PaymentType getPaymentType() {
        return this.paymentContext.getPaymentType();
    }

}