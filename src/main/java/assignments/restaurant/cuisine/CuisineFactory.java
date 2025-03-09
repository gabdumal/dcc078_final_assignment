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

/*
 * Design Pattern: Abstract Factory
 *
 * This interface is part of the Abstract Factory design pattern.
 * It is a factory for creating various types of cuisine components.
 */

/*
 * Design Pattern: Decorator
 *
 * This interface is part of the Decorator design pattern.
 * It provides methods for creating decorators for cuisine components.
 */

/**
 * Factory for creating various types of cuisine components.
 */
public interface CuisineFactory {

    /**
     * Returns the name of the cuisine.
     *
     * @return A string representing the name of the cuisine.
     */
    static String getCuisine() {
        return "Generic";
    }

    /**
     * Creates an appetizer for the cuisine.
     *
     * @return An instance of Appetizer.
     */
    Appetizer createAppetizer();

    /**
     * Creates an appetizer decorator for the cuisine.
     *
     * @param appetizer The appetizer to be decorated.
     * @return An instance of AppetizerDecorator.
     */
    Appetizer createAppetizerDecorator(Appetizer appetizer);

    /**
     * Creates a beverage for the cuisine.
     *
     * @return An instance of Beverage.
     */
    Beverage createBeverage();

    /**
     * Creates a beverage decorator for the cuisine.
     *
     * @param beverage The beverage to be decorated.
     * @return An instance of BeverageDecorator.
     */
    Beverage createBeverageDecorator(Beverage beverage);

    /**
     * Creates a dessert for the cuisine.
     *
     * @return An instance of Dessert.
     */
    Dessert createDessert();

    /**
     * Creates a dessert decorator for the cuisine.
     *
     * @param dessert The dessert to be decorated.
     * @return An instance of DessertDecorator.
     */
    Dessert createDessertDecorator(Dessert dessert);

    /**
     * Creates a main course for the cuisine.
     *
     * @return An instance of MainCourse.
     */
    MainCourse createMainCourse();

    /**
     * Creates a main course decorator for the cuisine.
     *
     * @param mainCourse The main course to be decorated.
     * @return An instance of MainCourseDecorator.
     */
    MainCourse createMainCourseDecorator(MainCourse mainCourse);

}