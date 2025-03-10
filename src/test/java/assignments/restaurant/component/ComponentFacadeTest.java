/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component;

import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineAppetizer;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineAppetizerDecorator;
import assignments.restaurant.cuisine.BrazilianCuisineFactory;
import assignments.restaurant.cuisine.Cuisine;
import org.junit.jupiter.api.Test;

import static assignments.restaurant.component.ComponentFacade.getCuisineFactory;
import static org.junit.jupiter.api.Assertions.*;

public class ComponentFacadeTest {

    @Test
    public void createAppetizer() {
        Appetizer appetizer = ComponentFacade.createAppetizer(
                Cuisine.Brazilian,
                "Pão de alho",
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                6.0d
                                                             );

        assertNotNull(appetizer);
        assertSame(BrazilianCuisineAppetizer.class, appetizer.getClass());
        assertEquals(Cuisine.Brazilian, appetizer.getCuisine());
        assertEquals("Entrada", appetizer.getCategory());
        assertEquals("Pão de alho", appetizer.getName());
        assertEquals(
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                appetizer.getDescription()
                    );
        assertEquals(6.0d, appetizer.getCost());
    }

    @Test
    public void createAppetizerDecorator() {
        Appetizer appetizer = ComponentFacade.createAppetizer(
                Cuisine.Brazilian,
                "Pão de alho",
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                6.0d
                                                             );

        Appetizer decorator = ComponentFacade.createAppetizerDecorator(
                appetizer,
                Cuisine.Brazilian,
                "Muçarela",
                "Fatias finas de queijo muçarela.",
                2.0d
                                                                      );

        assertNotNull(decorator);
        assertSame(BrazilianCuisineAppetizerDecorator.class, decorator.getClass());
        assertEquals(Cuisine.Brazilian, decorator.getCuisine());
        assertEquals("Entrada", decorator.getCategory());
        assertEquals("Muçarela", decorator.getName());
        assertEquals("Fatias finas de queijo muçarela.", decorator.getDescription());
        assertEquals(8.0d, decorator.getCost());
    }

    @Test
    public void createComponent() {
        BrazilianCuisineFactory brazilianCuisineFactory = new BrazilianCuisineFactory();

        Component component = ComponentFacade.createComponent(
                brazilianCuisineFactory::createAppetizer,
                "Pão de alho",
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                6.0d
                                                             );

        assertNotNull(component);
        assertSame(BrazilianCuisineAppetizer.class, component.getClass());
        assertEquals(Cuisine.Brazilian, component.getCuisine());
        assertEquals("Entrada", component.getCategory());
        assertEquals("Pão de alho", component.getName());
        assertEquals(
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                component.getDescription()
                    );
        assertEquals(6.0d, component.getCost());
    }

    @Test
    public void createComponentDecorator() {
        BrazilianCuisineFactory brazilianCuisineFactory = new BrazilianCuisineFactory();

        Component component = ComponentFacade.createComponent(
                brazilianCuisineFactory::createAppetizer,
                "Pão de alho",
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                6.0d
                                                             );

        Component decorator = ComponentFacade.createComponentDecorator(
                component,
                lambdaComponent -> getCuisineFactory(Cuisine.Brazilian).createAppetizerDecorator((Appetizer) lambdaComponent),
                "Muçarela",
                "Fatias finas de queijo muçarela.",
                2.0d
                                                                      );

        assertNotNull(decorator);
        assertSame(BrazilianCuisineAppetizerDecorator.class, decorator.getClass());
        assertEquals(Cuisine.Brazilian, decorator.getCuisine());
        assertEquals("Entrada", decorator.getCategory());
        assertEquals("Muçarela", decorator.getName());
        assertEquals("Fatias finas de queijo muçarela.", decorator.getDescription());
        assertEquals(8.0d, decorator.getCost());
    }

}
