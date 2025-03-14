/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component;

public abstract class AppetizerDecorator
        extends Appetizer
        implements Decorator {

    protected final Appetizer appetizer;

    public AppetizerDecorator(Appetizer appetizer) {
        this.appetizer = appetizer;
    }

    @Override
    public double getCost() {
        return this.cost + this.appetizer.getCost();
    }

    @Override
    public String getDescription() {
        return this.appetizer.getDescription() + " " + this.description;
    }

    @Override
    public String getName() {
        return this.appetizer.getName();
    }

    @Override
    public String toString() {
        var decoratedEncoding = this.appetizer.toString().substring(0, this.appetizer.toString().length() - 1);
        return decoratedEncoding + ", Extra: {" + "Nome: \"" + this.name + "\", " + "Descrição: \"" + this.description +
               "\", " + "Custo: R$" + this.cost + ", " + "Categoria: \"" + this.getCategory() + "\", " + "Cozinha: \"" +
               this.getCuisine() + "\"}}";
    }

    @Override
    public MenuComponent getDecorated() {
        return this.appetizer;
    }

    @Override
    public String getDecorationName() {
        return this.name;
    }

}
