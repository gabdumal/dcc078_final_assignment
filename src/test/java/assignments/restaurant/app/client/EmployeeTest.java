/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.Manager;
import assignments.restaurant.app.server.Request;
import assignments.restaurant.app.server.Server;
import assignments.restaurant.component.Appetizer;
import assignments.restaurant.component.ComponentFacade;
import assignments.restaurant.component.MenuComponent;
import assignments.restaurant.data.MenuComponentRecord;
import assignments.restaurant.data.Query;
import assignments.restaurant.order.Order;
import assignments.restaurant.order.OrderBuilder;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {

    private static final MenuComponentRecord   appetizerRecord           = Query.fetchMenuComponentById("pao_de_alho")
                                                                                .getFirst();
    private static final MenuComponentRecord   appetizerRecordDecorator  = Query.fetchMenuComponentById("mucarela")
                                                                                .getFirst();
    private static final MenuComponentRecord   beverageRecord            = Query.fetchMenuComponentById("caipirinha")
                                                                                .getFirst();
    private static final MenuComponentRecord   beverageRecordDecorator   = Query.fetchMenuComponentById("mel")
                                                                                .getFirst();
    private static final String                customerName              = "Alice Andrade";
    private static final MenuComponentRecord   dessertRecord             = Query.fetchMenuComponentById("brigadeiro")
                                                                                .getFirst();
    private static final MenuComponentRecord   dessertRecordDecorator    = Query.fetchMenuComponentById("doce_de_leite")
                                                                                .getFirst();
    private static final MenuComponentRecord   mainCourseRecord          = Query.fetchMenuComponentById("feijoada")
                                                                                .getFirst();
    private static final MenuComponentRecord   mainCourseRecordDecorator = Query.fetchMenuComponentById("farofa")
                                                                                .getFirst();
    private final static Manager               manager                   = Manager.getInstance();
    private static       ExecutorService       clientExecutor;
    private static       OrderBuilder          orderBuilder              = new OrderBuilder();
    private static       ExecutorService       serverExecutor;
    private              ByteArrayOutputStream employeeByteArrayOutputStream;
    private              ByteArrayOutputStream serverByteArrayOutputStream;

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
        clientExecutor.shutdown();
        serverExecutor.shutdown();
    }

    @AfterAll
    static void tearDown() {
        Client.setClientPrintStream(System.out);
        Server.setServerPrintStream(System.out);
    }

    @AfterEach
    void clearOutputStreams() {
        employeeByteArrayOutputStream.reset();
        serverByteArrayOutputStream.reset();
    }

    @BeforeEach
    void setOutputStreams() {
        employeeByteArrayOutputStream = new ByteArrayOutputStream();
        Client.setClientPrintStream(new PrintStream(employeeByteArrayOutputStream));
        serverByteArrayOutputStream = new ByteArrayOutputStream();
        Server.setServerPrintStream(new PrintStream(serverByteArrayOutputStream));
    }

    @BeforeEach
    void setUp() {
        clientExecutor = Executors.newFixedThreadPool(2);
    }

    @Test
    void shouldFetchOrdersFromServer()
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

        String result = simulatedCustomerFuture.get(5, TimeUnit.MINUTES);
        assertEquals("Pedido enviado com sucesso.", result);
        Thread.sleep(2000);

        Future<String> employeeFuture = clientExecutor.submit(() -> {
            try {
                Client.main(new String[]{"-e"});
                return "A interface de usuário do funcionário foi executada com sucesso.";
            }
            catch (Exception e) {
                return "A execução da interface de usuário do funcionário falhou: " + e.getMessage();
            }
        });

        // Wait for customer execution
        result = employeeFuture.get(5, TimeUnit.MINUTES);
        assertEquals("A interface de usuário do funcionário foi executada com sucesso.", result);
        Thread.sleep(2000);

        String employeeOutput = employeeByteArrayOutputStream.toString();
        assertEquals(
                "Boas-vindas ao Restaurante!\n" + "Pedidos:\n" + "1. Alice Andrade\n" +
                "     Pão de alho - R$8.0 - Extras: Muçarela.\n" + "     Feijoada - R$35.0 - Extras: Farofa.\n" +
                "     Caipirinha - R$13.0 - Extras: Mel.\n" + "     Brigadeiro - R$9.0 - Extras: Doce de leite.\n" +
                "2. Alice Andrade\n" + "     Pão de alho - R$8.0 - Extras: Muçarela.\n" +
                "     Feijoada - R$35.0 - Extras: Farofa.\n" + "     Caipirinha - R$13.0 - Extras: Mel.\n" +
                "     Brigadeiro - R$9.0 - Extras: Doce de leite.\n" + "3. Alice Andrade\n" +
                "     Pão de alho - R$10.0 - Extras: Muçarela. Muçarela.\n" +
                "     Feijoada - R$35.0 - Extras: Farofa.\n" + "     Caipirinha - R$13.0 - Extras: Mel.\n" +
                "     Brigadeiro - R$9.0 - Extras: Doce de leite.\n", employeeOutput
                    );

        String serverOutput = serverByteArrayOutputStream.toString();
        assertEquals(
                "Um novo cliente se conectou.\n" +
                "Pedido recebido: {Cliente: \"Alice Andrade\", Entrada: {Nome: \"Pão de alho\", Descrição: \"Pão francês assado ao molho de alho, azeite e ervas.\", Custo: R$6.0, Categoria: \"Entrada\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Muçarela\", Descrição: \"Fatias finas de queijo muçarela.\", Custo: R$2.0, Categoria: \"Entrada\", Cozinha: \"Culinária brasileira\"}, Extra: {Nome: \"Muçarela\", Descrição: \"Fatias finas de queijo muçarela.\", Custo: R$2.0, Categoria: \"Entrada\", Cozinha: \"Culinária brasileira\"}}, Prato principal: {Nome: \"Feijoada\", Descrição: \"Feijoada completa com arroz, couve, farofa, laranja e torresmo.\", Custo: R$30.0, Categoria: \"Prato principal\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Farofa\", Descrição: \"Farinha de mandioca torrada com bacon e ovos.\", Custo: R$5.0, Categoria: \"Prato principal\", Cozinha: \"Culinária brasileira\"}}, Bebida: {Nome: \"Caipirinha\", Descrição: \"Cachaça, limão, açúcar e gelo.\", Custo: R$10.0, Categoria: \"Bebida\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Mel\", Descrição: \"Mel puro.\", Custo: R$3.0, Categoria: \"Bebida\", Cozinha: \"Culinária brasileira\"}}, Sobremesa: {Nome: \"Brigadeiro\", Descrição: \"Doce de chocolate com leite condensado e chocolate granulado.\", Custo: R$5.0, Categoria: \"Sobremesa\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Doce de leite\", Descrição: \"Doce de leite pastoso.\", Custo: R$4.0, Categoria: \"Sobremesa\", Cozinha: \"Culinária brasileira\"}}}\n" +
                "Um cliente se desconectou.\n" + "Um novo cliente se conectou.\n" + "Um cliente se desconectou.\n",
                serverOutput
                    );
    }

    private Order createOrder() {
        orderBuilder.setCustomerName(customerName);
        orderBuilder.setAppetizer(appetizerRecord);
        orderBuilder.decorateAppetizer(appetizerRecordDecorator);
        orderBuilder.decorateAppetizer(appetizerRecordDecorator);
        orderBuilder.setBeverage(beverageRecord);
        orderBuilder.decorateBeverage(beverageRecordDecorator);
        orderBuilder.setMainCourse(mainCourseRecord);
        orderBuilder.decorateMainCourse(mainCourseRecordDecorator);
        orderBuilder.setDessert(dessertRecord);
        orderBuilder.decorateDessert(dessertRecordDecorator);
        return orderBuilder.build();
    }

    @Test
    public void shouldPrintAppetizer() {
        Employee employee = new Employee(null, null, null, new PrintStream(employeeByteArrayOutputStream));
        MenuComponent component = ComponentFacade.createAppetizer(
                appetizerRecord.cuisine(),
                appetizerRecord.name(),
                appetizerRecord.description(),
                appetizerRecord.cost()
                                                                 );
        employee.printMenuComponent(component);
        String employeeOutput = employeeByteArrayOutputStream.toString();
        assertEquals("     Pão de alho - R$6.0\n", employeeOutput);
    }

    @Test
    public void shouldPrintDecoratedAppetizer() {
        Employee employee = new Employee(null, null, null, new PrintStream(employeeByteArrayOutputStream));

        Appetizer appetizer = ComponentFacade.createAppetizer(
                appetizerRecord.cuisine(),
                appetizerRecord.name(),
                appetizerRecord.description(),
                appetizerRecord.cost()
                                                             );
        Appetizer decoratedAppetizer = ComponentFacade.createAppetizerDecorator(
                appetizer,
                appetizerRecord.cuisine(),
                appetizerRecordDecorator.name(),
                appetizerRecordDecorator.description(),
                appetizerRecordDecorator.cost()
                                                                               );

        employee.printMenuComponent(decoratedAppetizer);
        String employeeOutput = employeeByteArrayOutputStream.toString();
        assertEquals("     Pão de alho - R$8.0 - Extras: Muçarela.\n", employeeOutput);
    }

    @Test
    public void shouldPrintOrder() {
        Employee employee = new Employee(null, null, null, new PrintStream(employeeByteArrayOutputStream));
        var order = this.createOrder();
        employee.printOrder(order);
        String employeeOutput = employeeByteArrayOutputStream.toString();
        assertEquals(
                "Alice Andrade\n" + "     Pão de alho - R$10.0 - Extras: Muçarela. Muçarela.\n" +
                "     Feijoada - R$35.0 - Extras: Farofa.\n" + "     Caipirinha - R$13.0 - Extras: Mel.\n" +
                "     Brigadeiro - R$9.0 - Extras: Doce de leite.\n", employeeOutput
                    );
    }

    @Test
    public void shouldPrintOrders() {
        Employee employee = new Employee(null, null, null, new PrintStream(employeeByteArrayOutputStream));

        var firstOrder = this.createOrder();
        var secondOrder = this.createOrder();

        CopyOnWriteArrayList<Order> orders = new CopyOnWriteArrayList<>();
        orders.add(firstOrder);
        orders.add(secondOrder);

        employee.printOrders(orders);
        String employeeOutput = employeeByteArrayOutputStream.toString();
        assertEquals(
                "1. Alice Andrade\n" + "     Pão de alho - R$10.0 - Extras: Muçarela. Muçarela.\n" +
                "     Feijoada - R$35.0 - Extras: Farofa.\n" + "     Caipirinha - R$13.0 - Extras: Mel.\n" +
                "     Brigadeiro - R$9.0 - Extras: Doce de leite.\n" + "2. Alice Andrade\n" +
                "     Pão de alho - R$10.0 - Extras: Muçarela. Muçarela.\n" +
                "     Feijoada - R$35.0 - Extras: Farofa.\n" + "     Caipirinha - R$13.0 - Extras: Mel.\n" +
                "     Brigadeiro - R$9.0 - Extras: Doce de leite.\n", employeeOutput
                    );
    }

}

/* Testing path

 */