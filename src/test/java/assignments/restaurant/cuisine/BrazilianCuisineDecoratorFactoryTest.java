/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.cuisine;

import assignments.restaurant.component.*;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineAppetizerDecorator;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineBeverageDecorator;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineDessertDecorator;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineMainCourseDecorator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BrazilianCuisineDecoratorFactoryTest {

    private BrazilianCuisineFactory brazilianCuisineFactory;

    @AfterEach
    public void afterEach() {
        this.brazilianCuisineFactory = null;
    }

    @BeforeEach
    public void beforeEach() {
        this.brazilianCuisineFactory = new BrazilianCuisineFactory();
    }

    @Test
    public void shouldCreateAppetizerDecorator() {
        Appetizer appetizer = this.brazilianCuisineFactory.createAppetizer();
        Component component = this.brazilianCuisineFactory.createAppetizerDecorator(appetizer);
        assertNotNull(component);
        assertSame(BrazilianCuisineAppetizerDecorator.class, component.getClass());
        assertEquals(Cuisine.Brazilian, component.getCuisine());
        assertEquals("Entrada", component.getCategory());
    }

    @Test
    public void shouldCreateBeverageDecorator() {
        Beverage beverage = this.brazilianCuisineFactory.createBeverage();
        Component component = this.brazilianCuisineFactory.createBeverageDecorator(beverage);
        assertNotNull(component);
        assertSame(BrazilianCuisineBeverageDecorator.class, component.getClass());
        assertEquals(Cuisine.Brazilian, component.getCuisine());
        assertEquals("Bebida", component.getCategory());
    }

    @Test
    public void shouldCreateDessert() {
        Dessert dessert = this.brazilianCuisineFactory.createDessert();
        Component component = this.brazilianCuisineFactory.createDessertDecorator(dessert);
        assertNotNull(component);
        assertSame(BrazilianCuisineDessertDecorator.class, component.getClass());
        assertEquals(Cuisine.Brazilian, component.getCuisine());
        assertEquals("Sobremesa", component.getCategory());
    }

    @Test
    public void shouldCreateMainCourse() {
        MainCourse mainCourse = this.brazilianCuisineFactory.createMainCourse();
        Component component = this.brazilianCuisineFactory.createMainCourseDecorator(mainCourse);
        assertNotNull(component);
        assertSame(BrazilianCuisineMainCourseDecorator.class, component.getClass());
        assertEquals(Cuisine.Brazilian, component.getCuisine());
        assertEquals("Prato principal", component.getCategory());
    }

}
