/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component;

import assignments.restaurant.cuisine.BrazilianCuisineFactory;
import assignments.restaurant.cuisine.Cuisine;
import assignments.restaurant.cuisine.CuisineFactory;
import assignments.restaurant.cuisine.ItalianCuisineFactory;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The ComponentFacade class provides a facade for creating various types of components
 * (Appetizer, Beverage, Dessert, MainCourse) for different cuisines (Brazilian, Italian).
 */
public class ComponentFacade {

    private static final BrazilianCuisineFactory brazilianCuisineFactory = new BrazilianCuisineFactory();
    private static final ItalianCuisineFactory   italianCuisineFactory   = new ItalianCuisineFactory();

    /**
     * Creates an appetizer for the specified cuisine.
     *
     * @param cuisine     The cuisine type.
     * @param name        The name of the appetizer.
     * @param description The description of the appetizer.
     * @param cost        The cost of the appetizer.
     * @return The created Appetizer.
     */
    public static Appetizer createAppetizer(Cuisine cuisine, String name, String description, double cost) {
        return (Appetizer) createComponent(getCuisineFactory(cuisine)::createAppetizer, name, description, cost);
    }

    /**
     * Creates a component using the provided supplier and sets its properties.
     *
     * @param createComponent The supplier to create the component.
     * @param name            The name of the component.
     * @param description     The description of the component.
     * @param cost            The cost of the component.
     * @return The created Component.
     */
    protected static Component createComponent(
            Supplier<Component> createComponent,
            String name,
            String description,
            double cost
                                              ) {
        Component component = createComponent.get();
        component.setName(name);
        component.setDescription(description);
        component.setCost(cost);
        return component;
    }

    /**
     * Creates an appetizer decorator for the specified cuisine.
     *
     * @param appetizer   The original appetizer to be decorated.
     * @param cuisine     The cuisine type.
     * @param name        The name of the decorated appetizer.
     * @param description The description of the decorated appetizer.
     * @param cost        The cost of the decorated appetizer.
     * @return The decorated Appetizer.
     */
    public static Appetizer createAppetizerDecorator(
            Appetizer appetizer,
            Cuisine cuisine,
            String name,
            String description,
            double cost
                                                    ) {
        return (Appetizer) createComponentDecorator(
                appetizer,
                component -> getCuisineFactory(cuisine).createAppetizerDecorator((Appetizer) component),
                name,
                description,
                cost
                                                   );
    }

    /**
     * Creates a component decorator and sets its properties.
     *
     * @param component                The original component to be decorated.
     * @param createComponentDecorator The function to create the component decorator.
     * @param name                     The name of the decorated component.
     * @param description              The description of the decorated component.
     * @param cost                     The cost of the decorated component.
     * @return The decorated Component.
     */
    protected static Component createComponentDecorator(
            Component component,
            Function<Component, Component> createComponentDecorator,
            String name,
            String description,
            double cost
                                                       ) {
        Component decorator = createComponentDecorator.apply(component);
        decorator.setName(name);
        decorator.setDescription(description);
        decorator.setCost(cost);
        return decorator;
    }

    /**
     * Retrieves the appropriate CuisineFactory based on the specified cuisine.
     *
     * @param cuisine The cuisine type.
     * @return The corresponding CuisineFactory.
     * @throws IllegalArgumentException if the cuisine is invalid.
     */
    protected static CuisineFactory getCuisineFactory(Cuisine cuisine) {
        if (cuisine == Cuisine.Brazilian) {
            return brazilianCuisineFactory;
        }
        else if (cuisine == Cuisine.Italian) {
            return italianCuisineFactory;
        }
        else {
            throw new IllegalArgumentException("Invalid cuisine!");
        }
    }

    /**
     * Creates a beverage for the specified cuisine.
     *
     * @param cuisine     The cuisine type.
     * @param name        The name of the beverage.
     * @param description The description of the beverage.
     * @param cost        The cost of the beverage.
     * @return The created Beverage.
     */
    public static Beverage createBeverage(Cuisine cuisine, String name, String description, double cost) {
        return (Beverage) createComponent(getCuisineFactory(cuisine)::createBeverage, name, description, cost);
    }

    /**
     * Creates a beverage decorator for the specified cuisine.
     *
     * @param appetizer   The original beverage to be decorated.
     * @param cuisine     The cuisine type.
     * @param name        The name of the decorated beverage.
     * @param description The description of the decorated beverage.
     * @param cost        The cost of the decorated beverage.
     * @return The decorated Beverage.
     */
    public static Beverage createBeverageDecorator(
            Beverage appetizer,
            Cuisine cuisine,
            String name,
            String description,
            double cost
                                                  ) {
        return (Beverage) createComponentDecorator(
                appetizer,
                component -> getCuisineFactory(cuisine).createBeverageDecorator((Beverage) component),
                name,
                description,
                cost
                                                  );
    }

    /**
     * Creates a dessert for the specified cuisine.
     *
     * @param cuisine     The cuisine type.
     * @param name        The name of the dessert.
     * @param description The description of the dessert.
     * @param cost        The cost of the dessert.
     * @return The created Dessert.
     */
    public static Dessert createDessert(Cuisine cuisine, String name, String description, double cost) {
        return (Dessert) createComponent(getCuisineFactory(cuisine)::createDessert, name, description, cost);
    }

    /**
     * Creates a dessert decorator for the specified cuisine.
     *
     * @param appetizer   The original dessert to be decorated.
     * @param cuisine     The cuisine type.
     * @param name        The name of the decorated dessert.
     * @param description The description of the decorated dessert.
     * @param cost        The cost of the decorated dessert.
     * @return The decorated Dessert.
     */
    public static Dessert createDessertDecorator(
            Dessert appetizer,
            Cuisine cuisine,
            String name,
            String description,
            double cost
                                                ) {
        return (Dessert) createComponentDecorator(
                appetizer,
                component -> getCuisineFactory(cuisine).createDessertDecorator((Dessert) component),
                name,
                description,
                cost
                                                 );
    }

    /**
     * Creates a main course for the specified cuisine.
     *
     * @param cuisine     The cuisine type.
     * @param name        The name of the main course.
     * @param description The description of the main course.
     * @param cost        The cost of the main course.
     * @return The created MainCourse.
     */
    public static MainCourse createMainCourse(Cuisine cuisine, String name, String description, double cost) {
        return (MainCourse) createComponent(getCuisineFactory(cuisine)::createMainCourse, name, description, cost);
    }

    /**
     * Creates a main course decorator for the specified cuisine.
     *
     * @param appetizer   The original main course to be decorated.
     * @param cuisine     The cuisine type.
     * @param name        The name of the decorated main course.
     * @param description The description of the decorated main course.
     * @param cost        The cost of the decorated main course.
     * @return The decorated MainCourse.
     */
    public static MainCourse createMainCourseDecorator(
            MainCourse appetizer,
            Cuisine cuisine,
            String name,
            String description,
            double cost
                                                      ) {
        return (MainCourse) createComponentDecorator(
                appetizer,
                component -> getCuisineFactory(cuisine).createMainCourseDecorator((MainCourse) component),
                name,
                description,
                cost
                                                    );
    }

}
