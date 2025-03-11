/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component;

public abstract class DessertDecorator
        extends Dessert
        implements Decorator {

    protected Dessert dessert;

    public DessertDecorator(Dessert dessert) {
        this.dessert = dessert;
    }

    @Override
    public double getCost() {
        return this.cost + this.dessert.getCost();
    }

    @Override
    public String getDescription() {
        return this.dessert.getDescription() + " " + this.description;
    }

    @Override
    public String getName() {
        return this.dessert.getName();
    }

    @Override
    public String toString() {
        var decoratedEncoding = this.dessert.toString().substring(0, this.dessert.toString().length() - 1);
        return decoratedEncoding + ", Extra: {" + "Nome: \"" + this.name + "\", " + "Descrição: \"" + this.description +
               "\", " + "Custo: R$" + this.cost + ", " + "Categoria: \"" + this.getCategory() + "\", " + "Cozinha: \"" +
               this.getCuisine() + "\"}}";
    }

    @Override
    public MenuComponent getDecorated() {
        return this.dessert;
    }

    @Override
    public String getDecorationName() {
        return this.name;
    }

}
