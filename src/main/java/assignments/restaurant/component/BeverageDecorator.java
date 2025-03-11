/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component;

public abstract class BeverageDecorator
        extends Beverage
        implements Decorator {

    protected Beverage beverage;

    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double getCost() {
        return this.cost + this.beverage.getCost();
    }

    @Override
    public String getDescription() {
        return this.beverage.getDescription() + " " + this.description;
    }

    @Override
    public String getName() {
        return this.beverage.getName();
    }

    @Override
    public String toString() {
        var decoratedEncoding = this.beverage.toString().substring(0, this.beverage.toString().length() - 1);
        return decoratedEncoding + ", Extra: {" + "Nome: \"" + this.name + "\", " + "Descrição: \"" + this.description +
               "\", " + "Custo: R$" + this.cost + ", " + "Categoria: \"" + this.getCategory() + "\", " + "Cozinha: \"" +
               this.getCuisine() + "\"}}";
    }

    @Override
    public MenuComponent getDecorated() {
        return this.beverage;
    }

    @Override
    public String getDecorationName() {
        return this.name;
    }

}
