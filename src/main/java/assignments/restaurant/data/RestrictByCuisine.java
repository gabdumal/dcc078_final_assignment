/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.data;

import assignments.restaurant.cuisine.CuisineType;

public enum RestrictByCuisine {
    Brazilian,
    Italian;

    public static RestrictByCuisine convertCuisineType(CuisineType cuisineType) {
        switch (cuisineType) {
            case Brazilian -> {
                return RestrictByCuisine.Brazilian;
            }
            case Italian -> {
                return RestrictByCuisine.Italian;
            }
        }
        throw new IllegalArgumentException("Unknown cusine type: " + cuisineType);
    }
}
