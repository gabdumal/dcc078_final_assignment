/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component.brazilianCuisine;

import assignments.restaurant.component.Beverage;
import assignments.restaurant.cuisine.BrazilianCuisineFactory;

public class BrazilianCuisineBeverageDecorator
        extends Beverage {

    private final Beverage beverage;

    public BrazilianCuisineBeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double getCost() {
        return this.beverage.getCost();
    }

    @Override
    public String getCuisine() {
        return BrazilianCuisineFactory.getCuisine();
    }

}
