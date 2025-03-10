/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryTest {

    @Test
    public void shouldFetchAllAppetizers() {
        var menuComponents = Query.fetchAllMenuComponents(null, RestrictByCategory.Appetizer, null);
        assertEquals(4, menuComponents.size());
    }

    @Test
    public void shouldFetchAllAppetizersRestrictedByBrazilianCuisine() {
        var menuComponents = Query.fetchAllMenuComponents(
                RestrictByCuisine.Brazilian,
                RestrictByCategory.Appetizer,
                null
                                                         );
        assertEquals(2, menuComponents.size());
    }

    @Test
    public void shouldFetchAllBeverages() {
        var menuComponents = Query.fetchAllMenuComponents(null, RestrictByCategory.Beverage, null);
        assertEquals(4, menuComponents.size());
    }

    @Test
    public void shouldFetchAllBeveragesRestrictedByBrazilianCuisine() {
        var menuComponents = Query.fetchAllMenuComponents(
                RestrictByCuisine.Brazilian,
                RestrictByCategory.Beverage,
                null
                                                         );
        assertEquals(2, menuComponents.size());
    }

    @Test
    public void shouldFetchAllDecoratorsMenuComponents() {
        var menuComponents = Query.fetchAllMenuComponents(null, null, RestrictByDecorator.IsDecorator);
        assertEquals(8, menuComponents.size());
    }

    @Test
    public void shouldFetchAllDesserts() {
        var menuComponents = Query.fetchAllMenuComponents(null, RestrictByCategory.Dessert, null);
        assertEquals(4, menuComponents.size());
    }

    @Test
    public void shouldFetchAllDessertsRestrictedByBrazilianCuisine() {
        var menuComponents = Query.fetchAllMenuComponents(
                RestrictByCuisine.Brazilian,
                RestrictByCategory.Dessert,
                null
                                                         );
        assertEquals(2, menuComponents.size());
    }

    @Test
    public void shouldFetchAllMainCourses() {
        var menuComponents = Query.fetchAllMenuComponents(null, RestrictByCategory.MainCourse, null);
        assertEquals(4, menuComponents.size());
    }

    @Test
    public void shouldFetchAllMainCoursesRestrictedByBrazilianCuisine() {
        var menuComponents = Query.fetchAllMenuComponents(
                RestrictByCuisine.Brazilian,
                RestrictByCategory.MainCourse,
                null
                                                         );
        assertEquals(2, menuComponents.size());
    }

    @Test
    public void shouldFetchAllMenuComponents() {
        var menuComponents = Query.fetchAllMenuComponents(null, null, null);
        assertEquals(16, menuComponents.size());
    }

    @Test
    public void shouldFetchAllMenuComponentsRestrictedByBrazilianCuisine() {
        var menuComponents = Query.fetchAllMenuComponents(RestrictByCuisine.Brazilian, null, null);
        assertEquals(8, menuComponents.size());
    }

    @Test
    public void shouldFetchAllMenuComponentsRestrictedByItalianCuisine() {
        var menuComponents = Query.fetchAllMenuComponents(RestrictByCuisine.Italian, null, null);
        assertEquals(8, menuComponents.size());
    }

    @Test
    public void shouldFetchAllNonDecoratorAppetizersRestrictedByBrazilianCuisine() {
        var menuComponents = Query.fetchAllMenuComponents(
                RestrictByCuisine.Brazilian,
                RestrictByCategory.Appetizer,
                RestrictByDecorator.IsNotDecorator
                                                         );
        assertEquals(1, menuComponents.size());
    }

    @Test
    public void shouldFetchAllNonDecoratorsMenuComponents() {
        var menuComponents = Query.fetchAllMenuComponents(null, null, RestrictByDecorator.IsNotDecorator);
        assertEquals(8, menuComponents.size());
    }

}
