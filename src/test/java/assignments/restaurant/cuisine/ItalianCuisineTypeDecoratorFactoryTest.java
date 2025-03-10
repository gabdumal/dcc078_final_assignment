/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.cuisine;

import assignments.restaurant.component.*;
import assignments.restaurant.component.italianCuisine.ItalianCuisineAppetizerDecorator;
import assignments.restaurant.component.italianCuisine.ItalianCuisineBeverageDecorator;
import assignments.restaurant.component.italianCuisine.ItalianCuisineDessertDecorator;
import assignments.restaurant.component.italianCuisine.ItalianCuisineMainCourseDecorator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItalianCuisineTypeDecoratorFactoryTest {

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
    public void shouldCreateAppetizerDecorator() {
        Appetizer appetizer = this.italianCuisineFactory.createAppetizer();
        MenuComponent menuComponent = this.italianCuisineFactory.createAppetizerDecorator(appetizer);
        assertNotNull(menuComponent);
        assertSame(ItalianCuisineAppetizerDecorator.class, menuComponent.getClass());
        assertEquals(CuisineType.Italian, menuComponent.getCuisine());
        assertEquals(CategoryType.Appetizer, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateBeverageDecorator() {
        Beverage beverage = this.italianCuisineFactory.createBeverage();
        MenuComponent menuComponent = this.italianCuisineFactory.createBeverageDecorator(beverage);
        assertNotNull(menuComponent);
        assertSame(ItalianCuisineBeverageDecorator.class, menuComponent.getClass());
        assertEquals(CuisineType.Italian, menuComponent.getCuisine());
        assertEquals(CategoryType.Beverage, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateDessert() {
        Dessert dessert = this.italianCuisineFactory.createDessert();
        MenuComponent menuComponent = this.italianCuisineFactory.createDessertDecorator(dessert);
        assertNotNull(menuComponent);
        assertSame(ItalianCuisineDessertDecorator.class, menuComponent.getClass());
        assertEquals(CuisineType.Italian, menuComponent.getCuisine());
        assertEquals(CategoryType.Dessert, menuComponent.getCategory());
    }

    @Test
    public void shouldCreateMainCourse() {
        MainCourse mainCourse = this.italianCuisineFactory.createMainCourse();
        MenuComponent menuComponent = this.italianCuisineFactory.createMainCourseDecorator(mainCourse);
        assertNotNull(menuComponent);
        assertSame(ItalianCuisineMainCourseDecorator.class, menuComponent.getClass());
        assertEquals(CuisineType.Italian, menuComponent.getCuisine());
        assertEquals(CategoryType.MainCourse, menuComponent.getCategory());
    }

}
