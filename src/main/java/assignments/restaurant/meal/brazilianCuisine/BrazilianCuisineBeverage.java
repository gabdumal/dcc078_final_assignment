/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.meal.brazilianCuisine;

import assignments.restaurant.cuisine.BrazilianCuisineFactory;
import assignments.restaurant.meal.Beverage;

public class BrazilianCuisineBeverage
        extends Beverage {

    @Override
    public double getCost() {
        return 0;
    }

    @Override
    public String getCuisine() {
        return BrazilianCuisineFactory.getCuisine();
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

}
