/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.cuisine;

import assignments.restaurant.component.CategoryType;
import assignments.restaurant.component.MenuComponent;
import assignments.restaurant.component.italianCuisine.ItalianCuisineAppetizer;
import assignments.restaurant.component.italianCuisine.ItalianCuisineBeverage;
import assignments.restaurant.component.italianCuisine.ItalianCuisineDessert;
import assignments.restaurant.component.italianCuisine.ItalianCuisineMainCourse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItalianCuisineTypeFactoryTest {

    private ItalianCuisineFactory italianCuisineFactory;

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
        MenuComponent menuComponent = this.italianCuisineFactory.createAppetizer();
        assertNotNull(menuComponent);
        assertSame(ItalianCuisineAppetizer.class, menuComponent.getClass());
        assertEquals(CuisineType.Italian, menuComponent.getCuisine());
        assertEquals(CategoryType.Appetizer, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateBeverage() {
        MenuComponent menuComponent = this.italianCuisineFactory.createBeverage();
        assertNotNull(menuComponent);
        assertSame(ItalianCuisineBeverage.class, menuComponent.getClass());
        assertEquals(CuisineType.Italian, menuComponent.getCuisine());
        assertEquals(CategoryType.Beverage, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateDessert() {
        MenuComponent menuComponent = this.italianCuisineFactory.createDessert();
        assertNotNull(menuComponent);
        assertSame(ItalianCuisineDessert.class, menuComponent.getClass());
        assertEquals(CuisineType.Italian, menuComponent.getCuisine());
        assertEquals(CategoryType.Dessert, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateMainCourse() {
        MenuComponent menuComponent = this.italianCuisineFactory.createMainCourse();
        assertNotNull(menuComponent);
        assertSame(ItalianCuisineMainCourse.class, menuComponent.getClass());
        assertEquals(CuisineType.Italian, menuComponent.getCuisine());
        assertEquals(CategoryType.MainCourse, menuComponent.getCategory());
    }

}
