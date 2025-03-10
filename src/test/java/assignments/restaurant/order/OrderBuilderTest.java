/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order;

import assignments.restaurant.component.CategoryType;
import assignments.restaurant.cuisine.CuisineType;
import assignments.restaurant.data.Query;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderBuilderTest {

    private static final OrderBuilder orderBuilder = new OrderBuilder();

    @Test
    public void shouldSetAppetizer() {
        var menuComponentRecord = Query.fetchMenuComponentById("pao_de_alho").getFirst();
        orderBuilder.setAppetizer(menuComponentRecord);
        var order = orderBuilder.build();
        assertEquals(CategoryType.Appetizer, order.getAppetizer().getCategory());
        assertEquals(CuisineType.Brazilian, order.getAppetizer().getCuisine());
        assertEquals("Pão de alho", order.getAppetizer().getName());
        assertEquals("Pão francês assado ao molho de alho, azeite e ervas.", order.getAppetizer().getDescription());
        assertEquals(6.0d, order.getAppetizer().getCost(), 0.001d);
    }

    @Test
    public void shouldSetBeverage() {
        var menuComponentRecord = Query.fetchMenuComponentById("caipirinha").getFirst();
        orderBuilder.setBeverage(menuComponentRecord);
        var order = orderBuilder.build();
        assertEquals(CategoryType.Beverage, order.getBeverage().getCategory());
        assertEquals(CuisineType.Brazilian, order.getBeverage().getCuisine());
        assertEquals("Caipirinha", order.getBeverage().getName());
        assertEquals("Cachaça, limão, açúcar e gelo.", order.getBeverage().getDescription());
        assertEquals(10.0d, order.getBeverage().getCost(), 0.001d);
    }

    @Test
    public void shouldSetCustomerName() {
        orderBuilder.setCustomerName("Alice Andrade");
        var order = orderBuilder.build();
        assertEquals("Alice Andrade", order.getCustomerName());
    }

    @Test
    public void shouldSetDessert() {
        var menuComponentRecord = Query.fetchMenuComponentById("brigadeiro").getFirst();
        orderBuilder.setDessert(menuComponentRecord);
        var order = orderBuilder.build();
        assertEquals(CategoryType.Dessert, order.getDessert().getCategory());
        assertEquals(CuisineType.Brazilian, order.getDessert().getCuisine());
        assertEquals("Brigadeiro", order.getDessert().getName());
        assertEquals(
                "Doce de chocolate com leite condensado e chocolate granulado.",
                order.getDessert().getDescription()
                    );
        assertEquals(5.0d, order.getDessert().getCost(), 0.001d);
    }

    @Test
    public void shouldSetMainCourse() {
        var menuComponentRecord = Query.fetchMenuComponentById("feijoada").getFirst();
        orderBuilder.setMainCourse(menuComponentRecord);
        var order = orderBuilder.build();
        assertEquals(CategoryType.MainCourse, order.getMainCourse().getCategory());
        assertEquals(CuisineType.Brazilian, order.getMainCourse().getCuisine());
        assertEquals("Feijoada", order.getMainCourse().getName());
        assertEquals(
                "Feijoada completa com arroz, couve, farofa, laranja e torresmo.",
                order.getMainCourse().getDescription()
                    );
        assertEquals(30.0d, order.getMainCourse().getCost(), 0.001d);
    }

}
