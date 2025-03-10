/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component.italianCuisine;

import assignments.restaurant.component.Appetizer;
import assignments.restaurant.cuisine.Cuisine;

public class ItalianCuisineAppetizerDecorator
        extends Appetizer {

    private final Appetizer appetizer;

    public ItalianCuisineAppetizerDecorator(Appetizer appetizer) {
        this.appetizer = appetizer;
    }

    @Override
    public double getCost() {
        return this.cost + this.appetizer.getCost();
    }

    @Override
    public Cuisine getCuisine() {
        return Cuisine.Italian;
    }

}
