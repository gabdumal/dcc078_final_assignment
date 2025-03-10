/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component;

import assignments.restaurant.cuisine.BrazilianCuisineFactory;
import assignments.restaurant.cuisine.CuisineFactory;
import assignments.restaurant.cuisine.CuisineType;
import assignments.restaurant.cuisine.ItalianCuisineFactory;

import java.util.function.Function;
import java.util.function.Supplier;

/*
 * Design Pattern: Facade
 *
 * This class is part of the Facade design pattern.
 * It provides a simplified interface for creating various types of components (Appetizer, Beverage, Dessert, MainCourse) for different cuisines (Brazilian, Italian).
 */

/**
 * The ComponentFacade class provides a facade for creating various types of components
 * (Appetizer, Beverage, Dessert, MainCourse) for different cuisines (Brazilian, Italian).
 */
public class ComponentFacade {

    private static final BrazilianCuisineFactory brazilianCuisineFactory = new BrazilianCuisineFactory();
    private static final ItalianCuisineFactory   italianCuisineFactory   = new ItalianCuisineFactory();

    /**
     * Creates an appetizer for the specified cuisineType.
     *
     * @param cuisineType The cuisineType type.
     * @param name        The name of the appetizer.
     * @param description The description of the appetizer.
     * @param cost        The cost of the appetizer.
     * @return The created Appetizer.
     */
    public static Appetizer createAppetizer(CuisineType cuisineType, String name, String description, double cost) {
        return (Appetizer) createMenuComponent(
                getCuisineFactory(cuisineType)::createAppetizer,
                name,
                description,
                cost
                                              );
    }

    /**
     * Creates a component using the provided supplier and sets its properties.
     *
     * @param createComponent The supplier to create the component.
     * @param name            The name of the component.
     * @param description     The description of the component.
     * @param cost            The cost of the component.
     * @return The created MenuComponent.
     */
    protected static MenuComponent createMenuComponent(
            Supplier<MenuComponent> createComponent,
            String name,
            String description,
            double cost
                                                      ) {
        MenuComponent menuComponent = createComponent.get();
        menuComponent.setName(name);
        menuComponent.setDescription(description);
        menuComponent.setCost(cost);
        return menuComponent;
    }

    /**
     * Creates an appetizer decorator for the specified cuisineType.
     *
     * @param appetizer   The original appetizer to be decorated.
     * @param cuisineType The cuisineType type.
     * @param name        The name of the decorated appetizer.
     * @param description The description of the decorated appetizer.
     * @param cost        The cost of the decorated appetizer.
     * @return The decorated Appetizer.
     */
    public static Appetizer createAppetizerDecorator(
            Appetizer appetizer,
            CuisineType cuisineType,
            String name,
            String description,
            double cost
                                                    ) {
        return (Appetizer) createMenuComponentDecorator(
                appetizer,
                lambdaMenuComponent -> getCuisineFactory(cuisineType).createAppetizerDecorator((Appetizer) lambdaMenuComponent),
                name,
                description,
                cost
                                                       );
    }

    /**
     * Creates a menuComponent decorator and sets its properties.
     *
     * @param menuComponent            The original menuComponent to be decorated.
     * @param createComponentDecorator The function to create the menuComponent decorator.
     * @param name                     The name of the decorated menuComponent.
     * @param description              The description of the decorated menuComponent.
     * @param cost                     The cost of the decorated menuComponent.
     * @return The decorated MenuComponent.
     */
    protected static MenuComponent createMenuComponentDecorator(
            MenuComponent menuComponent,
            Function<MenuComponent, MenuComponent> createComponentDecorator,
            String name,
            String description,
            double cost
                                                               ) {
        MenuComponent decorator = createComponentDecorator.apply(menuComponent);
        decorator.setName(name);
        decorator.setDescription(description);
        decorator.setCost(cost);
        return decorator;
    }

    /**
     * Retrieves the appropriate CuisineFactory based on the specified cuisineType.
     *
     * @param cuisineType The cuisineType type.
     * @return The corresponding CuisineFactory.
     * @throws IllegalArgumentException if the cuisineType is invalid.
     */
    protected static CuisineFactory getCuisineFactory(CuisineType cuisineType) {
        if (cuisineType == CuisineType.Brazilian) {
            return brazilianCuisineFactory;
        }
        else if (cuisineType == CuisineType.Italian) {
            return italianCuisineFactory;
        }
        else {
            throw new IllegalArgumentException("Invalid cuisineType!");
        }
    }

    /**
     * Creates a beverage for the specified cuisineType.
     *
     * @param cuisineType The cuisineType type.
     * @param name        The name of the beverage.
     * @param description The description of the beverage.
     * @param cost        The cost of the beverage.
     * @return The created Beverage.
     */
    public static Beverage createBeverage(CuisineType cuisineType, String name, String description, double cost) {
        return (Beverage) createMenuComponent(getCuisineFactory(cuisineType)::createBeverage, name, description, cost);
    }

    /**
     * Creates a beverage decorator for the specified cuisineType.
     *
     * @param appetizer   The original beverage to be decorated.
     * @param cuisineType The cuisineType type.
     * @param name        The name of the decorated beverage.
     * @param description The description of the decorated beverage.
     * @param cost        The cost of the decorated beverage.
     * @return The decorated Beverage.
     */
    public static Beverage createBeverageDecorator(
            Beverage appetizer,
            CuisineType cuisineType,
            String name,
            String description,
            double cost
                                                  ) {
        return (Beverage) createMenuComponentDecorator(
                appetizer,
                component -> getCuisineFactory(cuisineType).createBeverageDecorator((Beverage) component),
                name,
                description,
                cost
                                                      );
    }

    /**
     * Creates a dessert for the specified cuisineType.
     *
     * @param cuisineType The cuisineType type.
     * @param name        The name of the dessert.
     * @param description The description of the dessert.
     * @param cost        The cost of the dessert.
     * @return The created Dessert.
     */
    public static Dessert createDessert(CuisineType cuisineType, String name, String description, double cost) {
        return (Dessert) createMenuComponent(getCuisineFactory(cuisineType)::createDessert, name, description, cost);
    }

    /**
     * Creates a dessert decorator for the specified cuisineType.
     *
     * @param appetizer   The original dessert to be decorated.
     * @param cuisineType The cuisineType type.
     * @param name        The name of the decorated dessert.
     * @param description The description of the decorated dessert.
     * @param cost        The cost of the decorated dessert.
     * @return The decorated Dessert.
     */
    public static Dessert createDessertDecorator(
            Dessert appetizer,
            CuisineType cuisineType,
            String name,
            String description,
            double cost
                                                ) {
        return (Dessert) createMenuComponentDecorator(
                appetizer,
                lambdaMenuComponent -> getCuisineFactory(cuisineType).createDessertDecorator((Dessert) lambdaMenuComponent),
                name,
                description,
                cost
                                                     );
    }

    /**
     * Creates a main course for the specified cuisineType.
     *
     * @param cuisineType The cuisineType type.
     * @param name        The name of the main course.
     * @param description The description of the main course.
     * @param cost        The cost of the main course.
     * @return The created MainCourse.
     */
    public static MainCourse createMainCourse(CuisineType cuisineType, String name, String description, double cost) {
        return (MainCourse) createMenuComponent(
                getCuisineFactory(cuisineType)::createMainCourse,
                name,
                description,
                cost
                                               );
    }

    /**
     * Creates a main course decorator for the specified cuisineType.
     *
     * @param appetizer   The original main course to be decorated.
     * @param cuisineType The cuisineType type.
     * @param name        The name of the decorated main course.
     * @param description The description of the decorated main course.
     * @param cost        The cost of the decorated main course.
     * @return The decorated MainCourse.
     */
    public static MainCourse createMainCourseDecorator(
            MainCourse appetizer,
            CuisineType cuisineType,
            String name,
            String description,
            double cost
                                                      ) {
        return (MainCourse) createMenuComponentDecorator(
                appetizer,
                lambdaMenuComponent -> getCuisineFactory(cuisineType).createMainCourseDecorator((MainCourse) lambdaMenuComponent),
                name,
                description,
                cost
                                                        );
    }

}
