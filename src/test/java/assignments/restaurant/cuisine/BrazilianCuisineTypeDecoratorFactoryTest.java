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

public class BrazilianCuisineTypeDecoratorFactoryTest {

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
        MenuComponent menuComponent = this.brazilianCuisineFactory.createAppetizerDecorator(appetizer);
        assertNotNull(menuComponent);
        assertSame(BrazilianCuisineAppetizerDecorator.class, menuComponent.getClass());
        assertEquals(CuisineType.Brazilian, menuComponent.getCuisine());
        assertEquals(CategoryType.Appetizer, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateBeverageDecorator() {
        Beverage beverage = this.brazilianCuisineFactory.createBeverage();
        MenuComponent menuComponent = this.brazilianCuisineFactory.createBeverageDecorator(beverage);
        assertNotNull(menuComponent);
        assertSame(BrazilianCuisineBeverageDecorator.class, menuComponent.getClass());
        assertEquals(CuisineType.Brazilian, menuComponent.getCuisine());
        assertEquals(CategoryType.Beverage, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateDessert() {
        Dessert dessert = this.brazilianCuisineFactory.createDessert();
        MenuComponent menuComponent = this.brazilianCuisineFactory.createDessertDecorator(dessert);
        assertNotNull(menuComponent);
        assertSame(BrazilianCuisineDessertDecorator.class, menuComponent.getClass());
        assertEquals(CuisineType.Brazilian, menuComponent.getCuisine());
        assertEquals(CategoryType.Dessert, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateMainCourse() {
        MainCourse mainCourse = this.brazilianCuisineFactory.createMainCourse();
        MenuComponent menuComponent = this.brazilianCuisineFactory.createMainCourseDecorator(mainCourse);
        assertNotNull(menuComponent);
        assertSame(BrazilianCuisineMainCourseDecorator.class, menuComponent.getClass());
        assertEquals(CuisineType.Brazilian, menuComponent.getCuisine());
        assertEquals(CategoryType.MainCourse, menuComponent.getCategory());
    }

}
