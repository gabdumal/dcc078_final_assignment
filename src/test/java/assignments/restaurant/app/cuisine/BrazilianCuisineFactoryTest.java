/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.cuisine;

import assignments.restaurant.component.Component;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineAppetizer;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineBeverage;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineDessert;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineMainCourse;
import assignments.restaurant.cuisine.BrazilianCuisineFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BrazilianCuisineFactoryTest {

    private static final String                  cuisine = "Culinária brasileira";
    private              BrazilianCuisineFactory brazilianCuisineFactory;

    @AfterEach
    public void afterEach() {
        this.brazilianCuisineFactory = null;
    }

    @BeforeEach
    public void beforeEach() {
        this.brazilianCuisineFactory = new BrazilianCuisineFactory();
    }

    @Test
    public void shouldCreateAppetizer() {
        Component component = this.brazilianCuisineFactory.createAppetizer();
        assertNotNull(component);
        assertSame(BrazilianCuisineAppetizer.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Entrada", component.getCategory());
    }

    @Test
    public void shouldCreateBeverage() {
        Component component = this.brazilianCuisineFactory.createBeverage();
        assertNotNull(component);
        assertSame(BrazilianCuisineBeverage.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Bebida", component.getCategory());
    }

    @Test
    public void shouldCreateDessert() {
        Component component = this.brazilianCuisineFactory.createDessert();
        assertNotNull(component);
        assertSame(BrazilianCuisineDessert.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Sobremesa", component.getCategory());
    }

    @Test
    public void shouldCreateMainCourse() {
        Component component = this.brazilianCuisineFactory.createMainCourse();
        assertNotNull(component);
        assertSame(BrazilianCuisineMainCourse.class, component.getClass());
        assertEquals(cuisine, component.getCuisine());
        assertEquals("Prato principal", component.getCategory());
    }

}
