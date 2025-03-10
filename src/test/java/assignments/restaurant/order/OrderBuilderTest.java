/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order;

import assignments.restaurant.component.CategoryType;
import assignments.restaurant.cuisine.CuisineType;
import assignments.restaurant.data.MenuComponentRecord;
import assignments.restaurant.data.Query;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderBuilderTest {

    private static final MenuComponentRecord appetizerRecord           = Query.fetchMenuComponentById("pao_de_alho")
                                                                              .getFirst();
    private static final MenuComponentRecord appetizerRecordDecorator  = Query.fetchMenuComponentById("mucarela")
                                                                              .getFirst();
    private static final MenuComponentRecord beverageRecord            = Query.fetchMenuComponentById("caipirinha")
                                                                              .getFirst();
    private static final MenuComponentRecord beverageRecordDecorator   = Query.fetchMenuComponentById("mel").getFirst();
    private static final String              customerName              = "Alice Andrade";
    private static final MenuComponentRecord dessertRecord             = Query.fetchMenuComponentById("brigadeiro")
                                                                              .getFirst();
    private static final MenuComponentRecord dessertRecordDecorator    = Query.fetchMenuComponentById("doce_de_leite")
                                                                              .getFirst();
    private static final MenuComponentRecord mainCourseRecord          = Query.fetchMenuComponentById("feijoada")
                                                                              .getFirst();
    private static final MenuComponentRecord mainCourseRecordDecorator = Query.fetchMenuComponentById("farofa")
                                                                              .getFirst();
    private static final OrderBuilder        orderBuilder              = new OrderBuilder();

    @Test
    public void shouldDecorateAppetizer() {
        orderBuilder.setCustomerName(customerName);
        orderBuilder.setAppetizer(appetizerRecord);
        orderBuilder.setBeverage(beverageRecord);
        orderBuilder.setMainCourse(mainCourseRecord);
        orderBuilder.setDessert(dessertRecord);

        orderBuilder.decorateAppetizer(appetizerRecordDecorator);

        var order = orderBuilder.build();

        assertEquals(CategoryType.Appetizer, order.getAppetizer().getCategory());
        assertEquals(CuisineType.Brazilian, order.getAppetizer().getCuisine());
        assertEquals("Pão de alho", order.getAppetizer().getName());
        assertEquals(
                "Pão francês assado ao molho de alho, azeite e ervas. Fatias finas de queijo muçarela.",
                order.getAppetizer().getDescription()
                    );
        assertEquals(8.0d, order.getAppetizer().getCost(), 0.001d);
    }

    @Test
    public void shouldDecorateBeverage() {
        orderBuilder.setCustomerName(customerName);
        orderBuilder.setAppetizer(appetizerRecord);
        orderBuilder.setBeverage(beverageRecord);
        orderBuilder.setDessert(dessertRecord);
        orderBuilder.setMainCourse(mainCourseRecord);

        orderBuilder.decorateBeverage(beverageRecordDecorator);

        var order = orderBuilder.build();

        assertEquals(CategoryType.Beverage, order.getBeverage().getCategory());
        assertEquals(CuisineType.Brazilian, order.getBeverage().getCuisine());
        assertEquals("Caipirinha", order.getBeverage().getName());
        assertEquals("Cachaça, limão, açúcar e gelo. Mel puro.", order.getBeverage().getDescription());
        assertEquals(13.0d, order.getBeverage().getCost(), 0.001d);
    }

    @Test
    public void shouldDecorateDessert() {
        orderBuilder.setCustomerName(customerName);
        orderBuilder.setAppetizer(appetizerRecord);
        orderBuilder.setBeverage(beverageRecord);
        orderBuilder.setDessert(dessertRecord);
        orderBuilder.setMainCourse(mainCourseRecord);

        orderBuilder.decorateDessert(dessertRecordDecorator);

        var order = orderBuilder.build();

        assertEquals(CategoryType.Dessert, order.getDessert().getCategory());
        assertEquals(CuisineType.Brazilian, order.getDessert().getCuisine());
        assertEquals("Brigadeiro", order.getDessert().getName());
        assertEquals(
                "Doce de chocolate com leite condensado e chocolate granulado. Doce de leite pastoso.",
                order.getDessert().getDescription()
                    );
        assertEquals(9.0d, order.getDessert().getCost(), 0.001d);
    }

    @Test
    public void shouldDecorateMainCourse() {
        orderBuilder.setCustomerName(customerName);
        orderBuilder.setAppetizer(appetizerRecord);
        orderBuilder.setBeverage(beverageRecord);
        orderBuilder.setDessert(dessertRecord);
        orderBuilder.setMainCourse(mainCourseRecord);

        orderBuilder.decorateMainCourse(mainCourseRecordDecorator);

        var order = orderBuilder.build();

        assertEquals(CategoryType.MainCourse, order.getMainCourse().getCategory());
        assertEquals(CuisineType.Brazilian, order.getMainCourse().getCuisine());
        assertEquals("Feijoada", order.getMainCourse().getName());
        assertEquals(
                "Feijoada completa com arroz, couve, farofa, laranja e torresmo. Farinha de mandioca torrada com bacon e ovos.",
                order.getMainCourse().getDescription()
                    );
        assertEquals(35.0d, order.getMainCourse().getCost(), 0.001d);
    }

    @Test
    public void shouldNotDecorateEmptyAppetizer() {
        var exception = assertThrows(
                IllegalStateException.class,
                () -> orderBuilder.decorateAppetizer(appetizerRecordDecorator)
                                    );
        assertEquals("Não há entrada para acrescentar extras!", exception.getMessage());
    }

    @Test
    public void shouldNotDecorateEmptyBeverage() {
        var exception = assertThrows(
                IllegalStateException.class,
                () -> orderBuilder.decorateBeverage(beverageRecordDecorator)
                                    );
        assertEquals("Não há bebida para acrescentar extras!", exception.getMessage());
    }

    @Test
    public void shouldNotDecorateEmptyDessert() {
        var exception = assertThrows(
                IllegalStateException.class,
                () -> orderBuilder.decorateDessert(dessertRecordDecorator)
                                    );
        assertEquals("Não há sobremesa para acrescentar extras!", exception.getMessage());
    }

    @Test
    public void shouldNotDecorateEmptyMainCourse() {
        var exception = assertThrows(
                IllegalStateException.class,
                () -> orderBuilder.decorateMainCourse(mainCourseRecordDecorator)
                                    );
        assertEquals("Não há prato principal para acrescentar extras!", exception.getMessage());
    }

    @Test
    public void shouldSetAppetizer() {
        orderBuilder.setCustomerName(customerName);
        orderBuilder.setBeverage(beverageRecord);
        orderBuilder.setDessert(dessertRecord);
        orderBuilder.setMainCourse(mainCourseRecord);

        orderBuilder.setAppetizer(appetizerRecord);

        var order = orderBuilder.build();

        assertEquals(CategoryType.Appetizer, order.getAppetizer().getCategory());
        assertEquals(CuisineType.Brazilian, order.getAppetizer().getCuisine());
        assertEquals("Pão de alho", order.getAppetizer().getName());
        assertEquals("Pão francês assado ao molho de alho, azeite e ervas.", order.getAppetizer().getDescription());
        assertEquals(6.0d, order.getAppetizer().getCost(), 0.001d);
    }

    @Test
    public void shouldSetBeverage() {
        orderBuilder.setCustomerName(customerName);
        orderBuilder.setAppetizer(appetizerRecord);
        orderBuilder.setDessert(dessertRecord);
        orderBuilder.setMainCourse(mainCourseRecord);

        orderBuilder.setBeverage(beverageRecord);

        var order = orderBuilder.build();

        assertEquals(CategoryType.Beverage, order.getBeverage().getCategory());
        assertEquals(CuisineType.Brazilian, order.getBeverage().getCuisine());
        assertEquals("Caipirinha", order.getBeverage().getName());
        assertEquals("Cachaça, limão, açúcar e gelo.", order.getBeverage().getDescription());
        assertEquals(10.0d, order.getBeverage().getCost(), 0.001d);
    }

    @Test
    public void shouldSetCustomerName() {
        orderBuilder.setAppetizer(appetizerRecord);
        orderBuilder.setBeverage(beverageRecord);
        orderBuilder.setDessert(dessertRecord);
        orderBuilder.setMainCourse(mainCourseRecord);

        orderBuilder.setCustomerName(customerName);

        var order = orderBuilder.build();
        assertEquals("Alice Andrade", order.getCustomerName());
    }

    @Test
    public void shouldSetDessert() {
        orderBuilder.setCustomerName(customerName);
        orderBuilder.setAppetizer(appetizerRecord);
        orderBuilder.setBeverage(beverageRecord);
        orderBuilder.setMainCourse(mainCourseRecord);

        orderBuilder.setDessert(dessertRecord);

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
        orderBuilder.setCustomerName(customerName);
        orderBuilder.setAppetizer(appetizerRecord);
        orderBuilder.setBeverage(beverageRecord);
        orderBuilder.setDessert(dessertRecord);

        orderBuilder.setMainCourse(mainCourseRecord);

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
