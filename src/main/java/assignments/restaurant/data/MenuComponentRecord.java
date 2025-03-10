/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.data;

import assignments.restaurant.component.CategoryType;
import assignments.restaurant.cuisine.CuisineType;

public record MenuComponentRecord(
        String id, CategoryType category, CuisineType cuisine, boolean isDecorator, String name, String description,
        double cost
) {

}
