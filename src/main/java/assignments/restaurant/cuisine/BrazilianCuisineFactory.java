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
import assignments.restaurant.component.brazilianCuisine.*;

/**
 * Factory class for creating Brazilian cuisine component components.
 * Implements the CuisineFactory interface to provide specific
 * implementations for Brazilian cuisine appetizers, beverages,
 * desserts, and main courses.
 */
public class BrazilianCuisineFactory
        implements CuisineFactory {

    /**
     * Retrieves the cuisine type.
     *
     * @return The cuisine type.
     */
    public static CuisineType getCuisine() {
        return CuisineType.Brazilian;
    }

    /**
     * Creates a Brazilian cuisine appetizer.
     *
     * @return An instance of BrazilianCuisineAppetizer.
     */
    @Override
    public Appetizer createAppetizer() {
        return new BrazilianCuisineAppetizer();
    }

    /**
     * Creates a Brazilian cuisine appetizer decorator.
     *
     * @return An instance of BrazilianCuisineAppetizerDecorator.
     */
    @Override
    public Appetizer createAppetizerDecorator(Appetizer appetizer) {
        return new BrazilianCuisineAppetizerDecorator(appetizer);
    }

    /**
     * Creates a Brazilian cuisine beverage.
     *
     * @return An instance of BrazilianCuisineBeverage.
     */
    @Override
    public Beverage createBeverage() {
        return new BrazilianCuisineBeverage();
    }

    /**
     * Creates a Brazilian cuisine beverage decorator.
     *
     * @return An instance of BrazilianCuisineBeverageDecorator.
     */
    @Override
    public Beverage createBeverageDecorator(Beverage beverage) {
        return new BrazilianCuisineBeverageDecorator(beverage);
    }

    /**
     * Creates a Brazilian cuisine dessert.
     *
     * @return An instance of BrazilianCuisineDessert.
     */
    @Override
    public Dessert createDessert() {
        return new BrazilianCuisineDessert();
    }

    /**
     * Creates a Brazilian cuisine dessert decorator.
     *
     * @return An instance of BrazilianCuisineDessertDecorator.
     */
    @Override
    public Dessert createDessertDecorator(Dessert dessert) {
        return new BrazilianCuisineDessertDecorator(dessert);
    }

    /**
     * Creates a Brazilian cuisine main course.
     *
     * @return An instance of BrazilianCuisineMainCourse.
     */
    @Override
    public MainCourse createMainCourse() {
        return new BrazilianCuisineMainCourse();
    }

    /**
     * Creates a Brazilian cuisine main course decorator.
     *
     * @return An instance of BrazilianCuisineMainCourseDecorator.
     */
    @Override
    public MainCourse createMainCourseDecorator(MainCourse mainCourse) {
        return new BrazilianCuisineMainCourseDecorator(mainCourse);
    }

}