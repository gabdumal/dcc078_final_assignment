/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.component;

import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineAppetizer;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineAppetizerDecorator;
import assignments.restaurant.cuisine.BrazilianCuisineFactory;
import assignments.restaurant.cuisine.CuisineType;
import org.junit.jupiter.api.Test;

import static assignments.restaurant.component.ComponentFacade.getCuisineFactory;
import static org.junit.jupiter.api.Assertions.*;

public class MenuComponentFacadeTest {

    @Test
    public void createAppetizer() {
        Appetizer appetizer = ComponentFacade.createAppetizer(
                CuisineType.Brazilian,
                "Pão de alho",
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                6.0d
                                                             );

        assertNotNull(appetizer);
        assertSame(BrazilianCuisineAppetizer.class, appetizer.getClass());
        assertEquals(CuisineType.Brazilian, appetizer.getCuisine());
        assertEquals(CategoryType.Appetizer, appetizer.getCategory());
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
                CuisineType.Brazilian,
                "Pão de alho",
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                6.0d
                                                             );

        Appetizer decorator = ComponentFacade.createAppetizerDecorator(
                appetizer,
                CuisineType.Brazilian,
                "Muçarela",
                "Fatias finas de queijo muçarela.",
                2.0d
                                                                      );

        assertNotNull(decorator);
        assertSame(BrazilianCuisineAppetizerDecorator.class, decorator.getClass());
        assertEquals(CuisineType.Brazilian, decorator.getCuisine());
        assertEquals(CategoryType.Appetizer, decorator.getCategory());
        assertEquals("Pão de alho", decorator.getName());
        assertEquals(
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno." + " " +
                "Fatias finas de queijo muçarela.", decorator.getDescription()
                    );
        assertEquals(8.0d, decorator.getCost());
    }

    @Test
    public void createMenuComponent() {
        BrazilianCuisineFactory brazilianCuisineFactory = new BrazilianCuisineFactory();

        MenuComponent menuComponent = ComponentFacade.createMenuComponent(
                brazilianCuisineFactory::createAppetizer,
                "Pão de alho",
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                6.0d
                                                                         );

        assertNotNull(menuComponent);
        assertSame(BrazilianCuisineAppetizer.class, menuComponent.getClass());
        assertEquals(CuisineType.Brazilian, menuComponent.getCuisine());
        assertEquals(CategoryType.Appetizer, menuComponent.getCategory());
        assertEquals("Pão de alho", menuComponent.getName());
        assertEquals(
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                menuComponent.getDescription()
                    );
        assertEquals(6.0d, menuComponent.getCost());
    }

    @Test
    public void createMenuComponentDecorator() {
        BrazilianCuisineFactory brazilianCuisineFactory = new BrazilianCuisineFactory();

        MenuComponent menuComponent = ComponentFacade.createMenuComponent(
                brazilianCuisineFactory::createAppetizer,
                "Pão de alho",
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno.",
                6.0d
                                                                         );

        MenuComponent decorator = ComponentFacade.createMenuComponentDecorator(
                menuComponent,
                lambdaComponent -> getCuisineFactory(CuisineType.Brazilian).createAppetizerDecorator((Appetizer) lambdaComponent),
                "Muçarela",
                "Fatias finas de queijo muçarela.",
                2.0d
                                                                              );

        assertNotNull(decorator);
        assertSame(BrazilianCuisineAppetizerDecorator.class, decorator.getClass());
        assertEquals(CuisineType.Brazilian, decorator.getCuisine());
        assertEquals(CategoryType.Appetizer, decorator.getCategory());
        assertEquals("Pão de alho", decorator.getName());
        assertEquals(
                "Pão francês cortado em rodelas, recheado com pasta de alho e ervas e levado ao forno." + " " +
                "Fatias finas de queijo muçarela.", decorator.getDescription()
                    );
        assertEquals(8.0d, decorator.getCost());
    }

}
