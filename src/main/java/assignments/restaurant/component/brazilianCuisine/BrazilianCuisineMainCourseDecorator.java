/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component.brazilianCuisine;

import assignments.restaurant.component.MainCourse;
import assignments.restaurant.cuisine.Cuisine;

public class BrazilianCuisineMainCourseDecorator
        extends MainCourse {

    private final MainCourse mainCourse;

    public BrazilianCuisineMainCourseDecorator(MainCourse mainCourse) {
        this.mainCourse = mainCourse;
    }

    @Override
    public double getCost() {
        return this.cost + this.mainCourse.getCost();
    }

    @Override
    public Cuisine getCuisine() {
        return Cuisine.Brazilian;
    }

}
