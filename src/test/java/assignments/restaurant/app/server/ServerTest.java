/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.server;

import assignments.restaurant.Manager;
import assignments.restaurant.component.CategoryType;
import assignments.restaurant.cuisine.CuisineType;
import assignments.restaurant.data.MenuComponentRecord;
import assignments.restaurant.data.Query;
import assignments.restaurant.order.Order;
import assignments.restaurant.order.OrderBuilder;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {

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
    private final static Manager             manager                   = Manager.getInstance();
    private static       ExecutorService     clientExecutor;
    private static       OrderBuilder        orderBuilder              = new OrderBuilder();
    private static       ExecutorService     serverExecutor;
    ByteArrayOutputStream serverByteArrayOutputStream;

    @BeforeAll
    public static void createBuilder() {
        orderBuilder = new OrderBuilder();
    }

    @AfterAll
    public static void disposeBuilder() {
        orderBuilder = null;
    }

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
        serverExecutor.shutdown();
    }

    @AfterAll
    static void tearDown() {
        Server.setServerPrintStream(System.out);
    }

    @AfterEach
    void clearServerOutputStream() {
        serverByteArrayOutputStream.reset();
    }

    @BeforeEach
    void setServerOutputStream() {
        serverByteArrayOutputStream = new ByteArrayOutputStream();
        Server.setServerPrintStream(new PrintStream(serverByteArrayOutputStream));
    }

    @BeforeEach
    void setUp() {
        clientExecutor = Executors.newFixedThreadPool(2);
    }

    @Test
    void shouldSendAnOrderThroughSocket()
            throws Exception {
        Future<String> simulatedCustomerFuture = clientExecutor.submit(() -> {
            try (
                    Socket socket = new Socket(manager.getHost(), manager.getSocketPort());
                    ObjectOutputStream sendToServer = new ObjectOutputStream(socket.getOutputStream())
            ) {
                // Create and send an order to the server
                Order order = this.createOrder();
                Request request = Request.sendOrder(order);
                sendToServer.writeObject(request);
                sendToServer.flush();

                return "Pedido enviado com sucesso.";
            }
        });

        var ordersSize = manager.getOrders().size();

        // Wait for client execution
        String result = simulatedCustomerFuture.get(5, TimeUnit.MINUTES);
        assertEquals("Pedido enviado com sucesso.", result);
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

        assertEquals("Pedido enviado com sucesso.", result);

        String serverOutput = serverByteArrayOutputStream.toString();
        assertEquals(
                "Um novo cliente se conectou.\n" +
                "Pedido recebido: {Cliente: \"Alice Andrade\", Entrada: {Nome: \"Pão de alho\", Descrição: \"Pão francês assado ao molho de alho, azeite e ervas.\", Custo: R$6.0, Categoria: \"Entrada\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Muçarela\", Descrição: \"Fatias finas de queijo muçarela.\", Custo: R$2.0, Categoria: \"Entrada\", Cozinha: \"Culinária brasileira\"}}, Prato principal: {Nome: \"Feijoada\", Descrição: \"Feijoada completa com arroz, couve, farofa, laranja e torresmo.\", Custo: R$30.0, Categoria: \"Prato principal\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Farofa\", Descrição: \"Farinha de mandioca torrada com bacon e ovos.\", Custo: R$5.0, Categoria: \"Prato principal\", Cozinha: \"Culinária brasileira\"}}, Bebida: {Nome: \"Caipirinha\", Descrição: \"Cachaça, limão, açúcar e gelo.\", Custo: R$10.0, Categoria: \"Bebida\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Mel\", Descrição: \"Mel puro.\", Custo: R$3.0, Categoria: \"Bebida\", Cozinha: \"Culinária brasileira\"}}, Sobremesa: {Nome: \"Brigadeiro\", Descrição: \"Doce de chocolate com leite condensado e chocolate granulado.\", Custo: R$5.0, Categoria: \"Sobremesa\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Doce de leite\", Descrição: \"Doce de leite pastoso.\", Custo: R$4.0, Categoria: \"Sobremesa\", Cozinha: \"Culinária brasileira\"}}}\n" +
                "Um cliente se desconectou.\n", serverOutput
                    );
    }

    private Order createOrder() {
        orderBuilder.setCustomerName(customerName);
        orderBuilder.setAppetizer(appetizerRecord);
        orderBuilder.decorateAppetizer(appetizerRecordDecorator);
        orderBuilder.setBeverage(beverageRecord);
        orderBuilder.decorateBeverage(beverageRecordDecorator);
        orderBuilder.setMainCourse(mainCourseRecord);
        orderBuilder.decorateMainCourse(mainCourseRecordDecorator);
        orderBuilder.setDessert(dessertRecord);
        orderBuilder.decorateDessert(dessertRecordDecorator);
        return orderBuilder.build();
    }

}
