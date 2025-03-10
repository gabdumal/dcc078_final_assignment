/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component.italianCuisine;

import assignments.restaurant.component.Beverage;
import assignments.restaurant.component.BeverageDecorator;
import assignments.restaurant.cuisine.CuisineType;

public class ItalianCuisineBeverageDecorator
        extends BeverageDecorator {

    public ItalianCuisineBeverageDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public CuisineType getCuisine() {
        return CuisineType.Italian;
    }

}
