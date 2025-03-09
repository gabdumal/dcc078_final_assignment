/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.cuisine;

import assignments.restaurant.component.Component;
import assignments.restaurant.component.italianCuisine.ItalianCuisineAppetizer;
import assignments.restaurant.component.italianCuisine.ItalianCuisineBeverage;
import assignments.restaurant.component.italianCuisine.ItalianCuisineDessert;
import assignments.restaurant.component.italianCuisine.ItalianCuisineMainCourse;
import assignments.restaurant.cuisine.ItalianCuisineFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItalianCuisineFactoryTest {

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
    public void shouldCreateAppetizer() {
        Component component = this.italianCuisineFactory.createAppetizer();
        assertNotNull(component);
        assertSame(ItalianCuisineAppetizer.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Entrada", component.getCategory());
    }

    @Test
    public void shouldCreateBeverage() {
        Component component = this.italianCuisineFactory.createBeverage();
        assertNotNull(component);
        assertSame(ItalianCuisineBeverage.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Bebida", component.getCategory());
    }

    @Test
    public void shouldCreateDessert() {
        Component component = this.italianCuisineFactory.createDessert();
        assertNotNull(component);
        assertSame(ItalianCuisineDessert.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Sobremesa", component.getCategory());
    }

    @Test
    public void shouldCreateMainCourse() {
        Component component = this.italianCuisineFactory.createMainCourse();
        assertNotNull(component);
        assertSame(ItalianCuisineMainCourse.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Prato principal", component.getCategory());
    }

}
