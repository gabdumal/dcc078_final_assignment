/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component;

import assignments.restaurant.cuisine.CuisineType;

import java.io.Serial;
import java.io.Serializable;

public abstract class MenuComponent
        implements Serializable {

    @Serial
    private static final long   serialVersionUID = 1L;
    protected            double cost             = 0.0d;
    protected            String description      = "";
    protected            String name             = "";

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" + "Nome: \"" + this.name + "\", " + "Descrição: \"" + this.description + "\", " + "Custo: R$" +
               this.cost + ", " + "Categoria: \"" + this.getCategory() + "\", " + "Cozinha: \"" + this.getCuisine() +
               "\"}";
    }

    public abstract CategoryType getCategory();

    public abstract CuisineType getCuisine();

}
