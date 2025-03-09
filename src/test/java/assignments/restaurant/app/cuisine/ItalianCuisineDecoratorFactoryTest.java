/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.cuisine;

import assignments.restaurant.component.*;
import assignments.restaurant.component.italianCuisine.ItalianCuisineAppetizerDecorator;
import assignments.restaurant.component.italianCuisine.ItalianCuisineBeverageDecorator;
import assignments.restaurant.component.italianCuisine.ItalianCuisineDessertDecorator;
import assignments.restaurant.component.italianCuisine.ItalianCuisineMainCourseDecorator;
import assignments.restaurant.cuisine.ItalianCuisineFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItalianCuisineDecoratorFactoryTest {

    private static final String                cuisine = "Culinária italiana";
    private              ItalianCuisineFactory italianCuisineFactory;

    @AfterEach
    public void afterEach() {
        this.italianCuisineFactory = null;
    }

    @BeforeEach
    public void beforeEach() {
        this.italianCuisineFactory = new ItalianCuisineFactory();
    }

    @Test
    public void shouldCreateAppetizerDecorator() {
        Appetizer appetizer = this.italianCuisineFactory.createAppetizer();
        Component component = this.italianCuisineFactory.createAppetizerDecorator(appetizer);
        assertNotNull(component);
        assertSame(ItalianCuisineAppetizerDecorator.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Entrada", component.getCategory());
    }

    @Test
    public void shouldCreateBeverageDecorator() {
        Beverage beverage = this.italianCuisineFactory.createBeverage();
        Component component = this.italianCuisineFactory.createBeverageDecorator(beverage);
        assertNotNull(component);
        assertSame(ItalianCuisineBeverageDecorator.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Bebida", component.getCategory());
    }

    @Test
    public void shouldCreateDessert() {
        Dessert dessert = this.italianCuisineFactory.createDessert();
        Component component = this.italianCuisineFactory.createDessertDecorator(dessert);
        assertNotNull(component);
        assertSame(ItalianCuisineDessertDecorator.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Sobremesa", component.getCategory());
    }

    @Test
    public void shouldCreateMainCourse() {
        MainCourse mainCourse = this.italianCuisineFactory.createMainCourse();
        Component component = this.italianCuisineFactory.createMainCourseDecorator(mainCourse);
        assertNotNull(component);
        assertSame(ItalianCuisineMainCourseDecorator.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Prato principal", component.getCategory());
    }

}
