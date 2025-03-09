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
import assignments.restaurant.meal.italianCuisine.ItalianCuisineAppetizer;
import assignments.restaurant.meal.italianCuisine.ItalianCuisineBeverage;
import assignments.restaurant.meal.italianCuisine.ItalianCuisineDessert;
import assignments.restaurant.meal.italianCuisine.ItalianCuisineMainCourse;

/**
 * Factory class for creating Italian cuisine meal components.
 * Implements the CuisineFactory interface to provide specific
 * implementations for Italian cuisine appetizers, beverages,
 * desserts, and main courses.
 */
public class ItalianCuisineFactory
        implements CuisineFactory {

    /**
     * Returns the name of the cuisine.
     *
     * @return A string representing the name of the cuisine.
     */
    public static String getCuisine() {
        return "Culinária italiana";
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
     * Creates an Italian cuisine beverage.
     *
     * @return An instance of ItalianCuisineBeverage.
     */
    @Override
    public Beverage createBeverage() {
        return new ItalianCuisineBeverage();
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
     * Creates an Italian cuisine main course.
     *
     * @return An instance of ItalianCuisineMainCourse.
     */
    @Override
    public MainCourse createMainCourse() {
        return new ItalianCuisineMainCourse();
    }

}