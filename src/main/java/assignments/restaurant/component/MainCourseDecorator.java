/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component;

public abstract class MainCourseDecorator
        extends MainCourse {

    protected MainCourse mainCourse;

    public MainCourseDecorator(MainCourse mainCourse) {
        this.mainCourse = mainCourse;
    }

    @Override
    public double getCost() {
        return this.cost + this.mainCourse.getCost();
    }

    @Override
    public String getDescription() {
        return this.mainCourse.getDescription() + " " + this.description;
    }

    @Override
    public String getName() {
        return this.mainCourse.getName();
    }

}
