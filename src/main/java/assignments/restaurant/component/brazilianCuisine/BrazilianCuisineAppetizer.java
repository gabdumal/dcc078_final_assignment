/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component.brazilianCuisine;

import assignments.restaurant.component.Appetizer;
import assignments.restaurant.cuisine.BrazilianCuisineFactory;

public class BrazilianCuisineAppetizer
        extends Appetizer {

    @Override
    public double getCost() {
        return 0;
    }

    @Override
    public String getCuisine() {
        return BrazilianCuisineFactory.getCuisine();
    }

}
