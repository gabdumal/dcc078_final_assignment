/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.Manager;
import assignments.restaurant.app.server.Server;
import assignments.restaurant.component.CategoryType;
import assignments.restaurant.cuisine.CuisineType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    private static final String                customerName = "Alice Andrade";
    private final static Manager               manager      = Manager.getInstance();
    private static       ExecutorService       clientExecutor;
    private static       ExecutorService       serverExecutor;
    private final        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeAll
    static void startServer() {
        serverExecutor = Executors.newSingleThreadExecutor();
        serverExecutor.submit(() -> {
            Server.main(new String[]{});
        });

        // Wait for server to start
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterAll
    static void stopServer() {
        serverExecutor.shutdownNow();
        clientExecutor.shutdownNow();
    }

    @BeforeEach
    void setUp() {
        clientExecutor = Executors.newFixedThreadPool(2);
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void shouldCreateOrderOnServer()
            throws Exception {
        String simulatedInput = "Alice Andrade\n" + "1\n" + "1\n" + "S\n" + "1\n" + "1\n" + "S\n" + "1\n" + "1\n" +
                                "S\n" + "1\n" + "1\n" + "S\n" + "1\n" + "\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Future<String> customerFuture = clientExecutor.submit(() -> {
            try {
                Client.main(new String[]{"-c"});
                return "A interface de usuário do cliente foi executada com sucesso.";
            }
            catch (Exception e) {
                return "A execução da interface de usuário do cliente falhou: " + e.getMessage();
            }
        });

        var ordersSize = manager.getOrders().size();

        // Wait for client execution
        String result = customerFuture.get(5, TimeUnit.MINUTES);
        assertEquals("A interface de usuário do cliente foi executada com sucesso.", result);

        // Allow time for order processing on server
        Thread.sleep(2000);

        // Check if order was processed
        var orders = manager.getOrders();
        assertEquals(ordersSize + 1, orders.size());

        var order = orders.getFirst();
        assertEquals(customerName, order.getCustomerName());

        assertEquals(CategoryType.Appetizer, order.getAppetizer().getCategory());
        assertEquals(CuisineType.Brazilian, order.getAppetizer().getCuisine());
        assertEquals("Pão de alho", order.getAppetizer().getName());
        assertEquals(
                "Pão francês assado ao molho de alho, azeite e ervas. Fatias finas de queijo muçarela.",
                order.getAppetizer().getDescription()
                    );
        assertEquals(8.0d, order.getAppetizer().getCost(), 0.001d);

        assertEquals(CategoryType.Beverage, order.getBeverage().getCategory());
        assertEquals(CuisineType.Brazilian, order.getBeverage().getCuisine());
        assertEquals("Caipirinha", order.getBeverage().getName());
        assertEquals("Cachaça, limão, açúcar e gelo. Mel puro.", order.getBeverage().getDescription());
        assertEquals(13.0d, order.getBeverage().getCost(), 0.001d);

        assertEquals(CategoryType.Dessert, order.getDessert().getCategory());
        assertEquals(CuisineType.Brazilian, order.getDessert().getCuisine());
        assertEquals("Brigadeiro", order.getDessert().getName());
        assertEquals(
                "Doce de chocolate com leite condensado e chocolate granulado. Doce de leite pastoso.",
                order.getDessert().getDescription()
                    );
        assertEquals(9.0d, order.getDessert().getCost(), 0.001d);

        assertEquals(CategoryType.MainCourse, order.getMainCourse().getCategory());
        assertEquals(CuisineType.Brazilian, order.getMainCourse().getCuisine());
        assertEquals("Feijoada", order.getMainCourse().getName());
        assertEquals(
                "Feijoada completa com arroz, couve, farofa, laranja e torresmo. Farinha de mandioca torrada com bacon e ovos.",
                order.getMainCourse().getDescription()
                    );
        assertEquals(35.0d, order.getMainCourse().getCost(), 0.001d);
    }

}

/* Testing path
Alice Andrade
1
1
S
1
1
S
1
1
S
1
1
S
1

 */