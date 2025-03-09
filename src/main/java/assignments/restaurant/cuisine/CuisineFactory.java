/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.cuisine;

import assignments.restaurant.meal.Appetizer;
import assignments.restaurant.meal.Beverage;
import assignments.restaurant.meal.Dessert;
import assignments.restaurant.meal.MainCourse;

/*
 * Design Pattern: Abstract Factory
 *
 * This interface is part of the Abstract Factory design pattern.
 * It is a factory for creating various types of cuisine components.
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
     * Creates a beverage for the cuisine.
     *
     * @return An instance of Beverage.
     */
    Beverage createBeverage();

    /**
     * Creates a dessert for the cuisine.
     *
     * @return An instance of Dessert.
     */
    Dessert createDessert();

    /**
     * Creates a main course for the cuisine.
     *
     * @return An instance of MainCourse.
     */
    MainCourse createMainCourse();

}