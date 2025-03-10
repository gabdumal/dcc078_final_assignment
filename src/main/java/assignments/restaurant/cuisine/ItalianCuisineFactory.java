/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.cuisine;

import assignments.restaurant.component.Appetizer;
import assignments.restaurant.component.Beverage;
import assignments.restaurant.component.Dessert;
import assignments.restaurant.component.MainCourse;
import assignments.restaurant.component.italianCuisine.*;

/**
 * Factory class for creating Italian cuisine component components.
 * Implements the CuisineFactory interface to provide specific
 * implementations for Italian cuisine appetizers, beverages,
 * desserts, and main courses.
 */
public class ItalianCuisineFactory
        implements CuisineFactory {

    /**
     * Retrieves the cuisine type.
     *
     * @return The cuisine type.
     */
    public static Cuisine getCuisine() {
        return Cuisine.Italian;
    }

    /**
     * Creates an Italian cuisine appetizer.
     *
     * @return An instance of ItalianCuisineAppetizer.
     */
    @Override
    public Appetizer createAppetizer() {
        return new ItalianCuisineAppetizer();
    }

    /**
     * Creates a Italian cuisine appetizer decorator.
     *
     * @return An instance of ItalianCuisineAppetizerDecorator.
     */
    @Override
    public Appetizer createAppetizerDecorator(Appetizer appetizer) {
        return new ItalianCuisineAppetizerDecorator(appetizer);
    }

    /**
     * Creates an Italian cuisine beverage.
     *
     * @return An instance of ItalianCuisineBeverage.
     */
    @Override
    public Beverage createBeverage() {
        return new ItalianCuisineBeverage();
    }

    /**
     * Creates a Italian cuisine beverage decorator.
     *
     * @return An instance of ItalianCuisineBeverageDecorator.
     */
    @Override
    public Beverage createBeverageDecorator(Beverage beverage) {
        return new ItalianCuisineBeverageDecorator(beverage);
    }

    /**
     * Creates an Italian cuisine dessert.
     *
     * @return An instance of ItalianCuisineDessert.
     */
    @Override
    public Dessert createDessert() {
        return new ItalianCuisineDessert();
    }

    /**
     * Creates a Italian cuisine dessert decorator.
     *
     * @return An instance of ItalianCuisineDessertDecorator.
     */
    @Override
    public Dessert createDessertDecorator(Dessert dessert) {
        return new ItalianCuisineDessertDecorator(dessert);
    }

    /**
     * Creates an Italian cuisine main course.
     *
     * @return An instance of ItalianCuisineMainCourse.
     */
    @Override
    public MainCourse createMainCourse() {
        return new ItalianCuisineMainCourse();
    }

    /**
     * Creates a Italian cuisine main course decorator.
     *
     * @return An instance of ItalianCuisineMainCourseDecorator.
     */
    @Override
    public MainCourse createMainCourseDecorator(MainCourse mainCourse) {
        return new ItalianCuisineMainCourseDecorator(mainCourse);
    }

}