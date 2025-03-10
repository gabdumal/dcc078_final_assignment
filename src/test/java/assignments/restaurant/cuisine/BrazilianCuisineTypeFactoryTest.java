/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.cuisine;

import assignments.restaurant.component.CategoryType;
import assignments.restaurant.component.MenuComponent;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineAppetizer;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineBeverage;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineDessert;
import assignments.restaurant.component.brazilianCuisine.BrazilianCuisineMainCourse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BrazilianCuisineTypeFactoryTest {

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
    public void shouldCreateAppetizer() {
        MenuComponent menuComponent = this.brazilianCuisineFactory.createAppetizer();
        assertNotNull(menuComponent);
        assertSame(BrazilianCuisineAppetizer.class, menuComponent.getClass());
        assertEquals(CuisineType.Brazilian, menuComponent.getCuisine());
        assertEquals(CategoryType.Appetizer, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateBeverage() {
        MenuComponent menuComponent = this.brazilianCuisineFactory.createBeverage();
        assertNotNull(menuComponent);
        assertSame(BrazilianCuisineBeverage.class, menuComponent.getClass());
        assertEquals(CuisineType.Brazilian, menuComponent.getCuisine());
        assertEquals(CategoryType.Beverage, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateDessert() {
        MenuComponent menuComponent = this.brazilianCuisineFactory.createDessert();
        assertNotNull(menuComponent);
        assertSame(BrazilianCuisineDessert.class, menuComponent.getClass());
        assertEquals(CuisineType.Brazilian, menuComponent.getCuisine());
        assertEquals(CategoryType.Dessert, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateMainCourse() {
        MenuComponent menuComponent = this.brazilianCuisineFactory.createMainCourse();
        assertNotNull(menuComponent);
        assertSame(BrazilianCuisineMainCourse.class, menuComponent.getClass());
        assertEquals(CuisineType.Brazilian, menuComponent.getCuisine());
        assertEquals(CategoryType.MainCourse, menuComponent.getCategory());
    }

}
