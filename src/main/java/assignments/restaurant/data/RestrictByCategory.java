/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.data;

import assignments.restaurant.component.CategoryType;

public enum RestrictByCategory {
    Appetizer,
    Beverage,
    Dessert,
    MainCourse;

    public static RestrictByCategory convertCategoryType(CategoryType categoryType) {
        switch (categoryType) {
            case Appetizer -> {
                return RestrictByCategory.Appetizer;
            }
            case Beverage -> {
                return RestrictByCategory.Beverage;
            }
            case Dessert -> {
                return RestrictByCategory.Dessert;
            }
            case MainCourse -> {
                return RestrictByCategory.MainCourse;
            }
        }
        throw new IllegalArgumentException("Unknown category type: " + categoryType);
    }
}
