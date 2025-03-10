/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order;

import assignments.restaurant.component.CategoryType;
import assignments.restaurant.component.ComponentFacade;
import assignments.restaurant.data.MenuComponentRecord;

/*
 * Design Pattern: Builder
 *
 * This class is part of the Builder design pattern.
 * It is a builder for creating and decorating an Order.
 */

/**
 * Builder class for creating and decorating an Order.
 */
public class OrderBuilder {

    private Order order;

    /**
     * Constructs a new OrderBuilder and initializes the order.
     */
    public OrderBuilder() {
        this.reset();
    }

    /**
     * Resets the builder by creating a new Order instance.
     */
    public void reset() {
        this.order = new Order();
    }

    /**
     * Builds and returns the current Order instance.
     *
     * @return The built Order.
     */
    public Order build() {
        if (this.order.getCustomerName() == null) {
            throw new IllegalStateException("O nome do cliente é obrigatório!");
        }
        if (this.order.getAppetizer() == null) {
            throw new IllegalStateException("Um pedido deve ter uma entrada!");
        }
        if (this.order.getBeverage() == null) {
            throw new IllegalStateException("Um pedido deve ter uma bebida!");
        }
        if (this.order.getDessert() == null) {
            throw new IllegalStateException("Um pedido deve ter uma sobremesa!");
        }
        if (this.order.getMainCourse() == null) {
            throw new IllegalStateException("Um pedido deve ter um prato principal!");
        }

        var builtOrder = this.order;
        this.reset();
        return builtOrder;
    }

    /**
     * Decorates the appetizer of the order with the specified decorator record.
     *
     * @param appetizerDecoratorRecord The record containing the decorator details.
     * @throws IllegalStateException    if there is no appetizer to decorate.
     * @throws IllegalArgumentException if the record is invalid.
     */
    public void decorateAppetizer(MenuComponentRecord appetizerDecoratorRecord) {
        this.validateMenuComponentDecoratorRecord(appetizerDecoratorRecord);

        var decorator = ComponentFacade.createAppetizerDecorator(
                this.order.getAppetizer(),
                appetizerDecoratorRecord.cuisine(),
                appetizerDecoratorRecord.name(),
                appetizerDecoratorRecord.description(),
                appetizerDecoratorRecord.cost()
                                                                );
        this.order.setAppetizer(decorator);
    }

    /**
     * Validates the provided menu component decorator record.
     *
     * @param mainCourseDecoratorRecord The record to validate.
     * @throws IllegalStateException if there is no corresponding component to decorate.
     */
    private void validateMenuComponentDecoratorRecord(MenuComponentRecord mainCourseDecoratorRecord) {
        switch (mainCourseDecoratorRecord.category()) {
            case Appetizer -> {
                if (this.order.getAppetizer() == null) {
                    throw new IllegalStateException("Não há entrada para acrescentar extras!");
                }
                OrderBuilder.validateAppetizerRecord(mainCourseDecoratorRecord);
            }
            case Beverage -> {
                if (this.order.getBeverage() == null) {
                    throw new IllegalStateException("Não há bebida para acrescentar extras!");
                }
                OrderBuilder.validateBeverageRecord(mainCourseDecoratorRecord);
            }
            case Dessert -> {
                if (this.order.getDessert() == null) {
                    throw new IllegalStateException("Não há sobremesa para acrescentar extras!");
                }
                OrderBuilder.validateDessertRecord(mainCourseDecoratorRecord);
            }
            case MainCourse -> {
                if (this.order.getMainCourse() == null) {
                    throw new IllegalStateException("Não há prato principal para acrescentar extras!");
                }
                OrderBuilder.validateMainCourseRecord(mainCourseDecoratorRecord);
            }
        }
    }

    /**
     * Validates the provided appetizer decorator record.
     *
     * @param appetizerDecoratorRecord The record to validate.
     * @throws IllegalArgumentException if the record is not an appetizer.
     */
    private static void validateAppetizerRecord(MenuComponentRecord appetizerDecoratorRecord) {
        OrderBuilder.validateMenuComponentRecord(appetizerDecoratorRecord);
        if (appetizerDecoratorRecord.category() != CategoryType.Appetizer) {
            throw new IllegalArgumentException("O componente de menu deve ser uma entrada!");
        }
    }

    /**
     * Validates the provided beverage record.
     *
     * @param beverageRecord The record to validate.
     * @throws IllegalArgumentException if the record is not a beverage.
     */
    private static void validateBeverageRecord(MenuComponentRecord beverageRecord) {
        OrderBuilder.validateMenuComponentRecord(beverageRecord);
        if (beverageRecord.category() != CategoryType.Beverage) {
            throw new IllegalArgumentException("O componente de menu deve ser uma bebida!");
        }
    }

    /**
     * Validates the provided dessert record.
     *
     * @param dessertRecord The record to validate.
     * @throws IllegalArgumentException if the record is not a dessert.
     */
    private static void validateDessertRecord(MenuComponentRecord dessertRecord) {
        OrderBuilder.validateMenuComponentRecord(dessertRecord);
        if (dessertRecord.category() != CategoryType.Dessert) {
            throw new IllegalArgumentException("O componente de menu deve ser uma sobremesa!");
        }
    }

    /**
     * Validates the provided main course record.
     *
     * @param mainCourseRecord The record to validate.
     * @throws IllegalArgumentException if the record is not a main course.
     */
    private static void validateMainCourseRecord(MenuComponentRecord mainCourseRecord) {
        OrderBuilder.validateMenuComponentRecord(mainCourseRecord);
        if (mainCourseRecord.category() != CategoryType.MainCourse) {
            throw new IllegalArgumentException("O componente de menu deve ser um prato principal!");
        }
    }

    /**
     * Validates the provided menu component record.
     *
     * @param menuComponentRecord The record to validate.
     * @throws IllegalArgumentException if the record is null or has no category.
     */
    private static void validateMenuComponentRecord(MenuComponentRecord menuComponentRecord) {
        if (menuComponentRecord == null) {
            throw new IllegalArgumentException("O componente de menu é obrigatório!");
        }
        if (menuComponentRecord.category() == null) {
            throw new IllegalArgumentException("O componente de menu deve ter uma categoria!");
        }
    }

    /**
     * Decorates the beverage of the order with the specified decorator record.
     *
     * @param beverageDecoratorRecord The record containing the decorator details.
     * @throws IllegalStateException    if there is no beverage to decorate.
     * @throws IllegalArgumentException if the record is invalid.
     */
    public void decorateBeverage(MenuComponentRecord beverageDecoratorRecord) {
        this.validateMenuComponentDecoratorRecord(beverageDecoratorRecord);

        var decorator = ComponentFacade.createBeverageDecorator(
                this.order.getBeverage(),
                beverageDecoratorRecord.cuisine(),
                beverageDecoratorRecord.name(),
                beverageDecoratorRecord.description(),
                beverageDecoratorRecord.cost()
                                                               );
        this.order.setBeverage(decorator);
    }

    /**
     * Decorates the dessert of the order with the specified decorator record.
     *
     * @param dessertDecoratorRecord The record containing the decorator details.
     * @throws IllegalStateException    if there is no dessert to decorate.
     * @throws IllegalArgumentException if the record is invalid.
     */
    public void decorateDessert(MenuComponentRecord dessertDecoratorRecord) {
        this.validateMenuComponentDecoratorRecord(dessertDecoratorRecord);

        var decorator = ComponentFacade.createDessertDecorator(
                this.order.getDessert(),
                dessertDecoratorRecord.cuisine(),
                dessertDecoratorRecord.name(),
                dessertDecoratorRecord.description(),
                dessertDecoratorRecord.cost()
                                                              );
        this.order.setDessert(decorator);
    }

    /**
     * Decorates the main course of the order with the specified decorator record.
     *
     * @param mainCourseDecoratorRecord The record containing the decorator details.
     * @throws IllegalStateException    if there is no main course to decorate.
     * @throws IllegalArgumentException if the record is invalid.
     */
    public void decorateMainCourse(MenuComponentRecord mainCourseDecoratorRecord) {
        this.validateMenuComponentDecoratorRecord(mainCourseDecoratorRecord);

        var decorator = ComponentFacade.createMainCourseDecorator(
                this.order.getMainCourse(),
                mainCourseDecoratorRecord.cuisine(),
                mainCourseDecoratorRecord.name(),
                mainCourseDecoratorRecord.description(),
                mainCourseDecoratorRecord.cost()
                                                                 );
        this.order.setMainCourse(decorator);
    }

    /**
     * Sets the appetizer of the order with the specified record.
     *
     * @param appetizerRecord The record containing the appetizer details.
     * @throws IllegalArgumentException if the record is invalid.
     */
    public void setAppetizer(MenuComponentRecord appetizerRecord) {
        OrderBuilder.validateMenuComponentRecord(appetizerRecord);
        validateAppetizerRecord(appetizerRecord);

        var component = ComponentFacade.createAppetizer(
                appetizerRecord.cuisine(),
                appetizerRecord.name(),
                appetizerRecord.description(),
                appetizerRecord.cost()
                                                       );
        this.order.setAppetizer(component);
    }

    /**
     * Sets the beverage of the order with the specified record.
     *
     * @param beverageRecord The record containing the beverage details.
     * @throws IllegalArgumentException if the record is invalid.
     */
    public void setBeverage(MenuComponentRecord beverageRecord) {
        OrderBuilder.validateBeverageRecord(beverageRecord);

        var component = ComponentFacade.createBeverage(
                beverageRecord.cuisine(),
                beverageRecord.name(),
                beverageRecord.description(),
                beverageRecord.cost()
                                                      );
        this.order.setBeverage(component);
    }

    /**
     * Sets the customer name of the order.
     *
     * @param customerName The customer name to set.
     * @throws IllegalArgumentException if the customer name is null or blank.
     */
    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório!");
        }
        this.order.setCustomerName(customerName);
    }

    /**
     * Sets the dessert of the order with the specified record.
     *
     * @param dessertRecord The record containing the dessert details.
     * @throws IllegalArgumentException if the record is invalid.
     */
    public void setDessert(MenuComponentRecord dessertRecord) {
        OrderBuilder.validateDessertRecord(dessertRecord);

        var component = ComponentFacade.createDessert(
                dessertRecord.cuisine(),
                dessertRecord.name(),
                dessertRecord.description(),
                dessertRecord.cost()
                                                     );
        this.order.setDessert(component);
    }

    /**
     * Sets the main course of the order with the specified record.
     *
     * @param mainCourseRecord The record containing the main course details.
     * @throws IllegalArgumentException if the record is invalid.
     */
    public void setMainCourse(MenuComponentRecord mainCourseRecord) {
        OrderBuilder.validateMainCourseRecord(mainCourseRecord);

        var component = ComponentFacade.createMainCourse(
                mainCourseRecord.cuisine(),
                mainCourseRecord.name(),
                mainCourseRecord.description(),
                mainCourseRecord.cost()
                                                        );
        this.order.setMainCourse(component);
    }

}