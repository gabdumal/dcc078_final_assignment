/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component.brazilianCuisine;

import assignments.restaurant.component.Appetizer;
import assignments.restaurant.cuisine.BrazilianCuisineFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrazilianCuisineTypeAppetizerTest {

    private final BrazilianCuisineFactory brazilianCuisineFactory = new BrazilianCuisineFactory();
    private final Appetizer               paoDeAlho               = this.createAppetizer(
            "Pão de alho",
            "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
            6.0d
                                                                                        );
    private final Appetizer               paoDeAlhoComMucarela    = this.createAppetizerDecorator(
            this.paoDeAlho,
            "Muçarela",
            "Fatias finas de queijo muçarela.",
            2.0d
                                                                                                 );

    private Appetizer createAppetizer(String name, String description, double cost) {
        Appetizer appetizer = this.createAppetizer();
        appetizer.setName(name);
        appetizer.setDescription(description);
        appetizer.setCost(cost);
        return appetizer;
    }

    private Appetizer createAppetizer() {
        return this.brazilianCuisineFactory.createAppetizer();
    }

    private Appetizer createAppetizerDecorator(Appetizer appetizer, String name, String description, double cost) {
        Appetizer appetizerDecorator = this.createAppetizerDecorator(appetizer);
        appetizerDecorator.setName(name);
        appetizerDecorator.setDescription(description);
        appetizerDecorator.setCost(cost);
        return appetizerDecorator;
    }

    private Appetizer createAppetizerDecorator(Appetizer appetizer) {
        return this.brazilianCuisineFactory.createAppetizerDecorator(appetizer);
    }

    @Test
    public void shouldGetCostOfAppetizer() {
        assertEquals(6.0d, this.paoDeAlho.getCost());
    }

    @Test
    public void shouldGetCostOfDecoratedAppetizer() {
        assertEquals(8.0d, this.paoDeAlhoComMucarela.getCost());
    }

    @Test
    public void shouldGetDescriptionOfAppetizer() {
        assertEquals(
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                paoDeAlho.getDescription()
                    );
    }

    @Test
    public void shouldGetDescriptionOfDecoratedAppetizer() {
        assertEquals(
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno." + " " +
                "Fatias finas de queijo muçarela.", paoDeAlhoComMucarela.getDescription()
                    );
    }

    @Test
    public void shouldGetNameOfAppetizer() {
        assertEquals("Pão de alho", paoDeAlho.getName());
    }

    @Test
    public void shouldGetNameOfDecoratedAppetizer() {
        assertEquals("Pão de alho", paoDeAlhoComMucarela.getName());
    }

}
