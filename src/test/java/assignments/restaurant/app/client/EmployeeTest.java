/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.Manager;
import assignments.restaurant.app.server.Server;
import assignments.restaurant.component.Appetizer;
import assignments.restaurant.component.ComponentFacade;
import assignments.restaurant.component.MenuComponent;
import assignments.restaurant.data.MenuComponentRecord;
import assignments.restaurant.data.Query;
import assignments.restaurant.order.Order;
import assignments.restaurant.order.OrderBuilder;
import assignments.restaurant.order.category.OrderCategoryType;
import assignments.restaurant.order.payment.CreditCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    private              ExecutorService       clientExecutor;
    private              ByteArrayOutputStream employeeByteArrayOutputStream;
    private              OrderBuilder          orderBuilder;
    private              Server                server;
    private              ByteArrayOutputStream serverByteArrayOutputStream;
    private              ExecutorService       serverExecutor;

    @BeforeEach
    void createBuilder() {
        this.orderBuilder = new OrderBuilder(OrderCategoryType.Delivery);
    }

    @AfterEach
    void disposeBuilder() {
        this.orderBuilder = null;
    }

    @AfterEach
    void resetOutputStreams() {
        Client.setClientPrintStream(System.out);
        this.employeeByteArrayOutputStream = null;
        Server.setServerPrintStream(System.out);
        this.serverByteArrayOutputStream = null;
    }

    @BeforeEach
    void setOutputStreams() {
        this.employeeByteArrayOutputStream = new ByteArrayOutputStream();
        Client.setClientPrintStream(new PrintStream(this.employeeByteArrayOutputStream));
        this.serverByteArrayOutputStream = new ByteArrayOutputStream();
        Server.setServerPrintStream(new PrintStream(this.serverByteArrayOutputStream));
    }

    @Test
    void shouldFetchOrdersFromServer()
            throws Exception {

        var customerOutput = CustomerTest.simulateCustomerActions(
                this.clientExecutor,
                EmployeeTest.findDefaultHost(),
                EmployeeTest.findDefaultPort(),
                "Alice Andrade\n" + "1\n" + "1\n" + "1\n" + "S\n" + "1\n" + "1\n" + "S\n" + "1\n" + "1\n" + "S\n" +
                "1\n" + "1\n" + "S\n" + "1\n" + "1\n" + "1234 5678 1234 5678\n" + "\n"
                                                                 );

        var employeeOutput = EmployeeTest.simulateEmployeeActions(
                this.clientExecutor,
                EmployeeTest.findDefaultHost(),
                EmployeeTest.findDefaultPort(),
                "Bruno Barros\n" + "1\n" + "0\n"
                                                                 );

        assertEquals(
                "Boas-vindas à interface de gerenciamento do Restaurante!\n" + "\n" + "Qual é seu nome?\n" + "\n" +
                "Escolha o pedido cujo estado deseja atualizar (X para sair).\n" + "\n" +
                "1. Recebido: Alice Andrade - R$65.0 via Cartão de Crédito\n" +
                "     Pão de alho - R$8.0 - Extras: Muçarela.\n" + "     Feijoada - R$35.0 - Extras: Farofa.\n" +
                "     Caipirinha - R$13.0 - Extras: Mel.\n" + "     Brigadeiro - R$9.0 - Extras: Doce de leite.\n" +
                "\n" + "Agradecemos por usar a interface de gerenciamento do Restaurante!\n" + "\n", employeeOutput
                    );

        String serverOutput = this.serverByteArrayOutputStream.toString();
        assertEquals(
                "Um novo cliente se conectou.\n" +
                "Pedido recebido: {Status: \"Recebido\", Tipo: \"Entrega\", Cliente: \"Alice Andrade\", Custo: 67.0, Pagamento: \"Cartão de Crédito\", Entrada: {Nome: \"Pão de alho\", Descrição: \"Pão francês assado ao molho de alho, azeite e ervas.\", Custo: R$6.0, Categoria: \"Entrada\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Muçarela\", Descrição: \"Fatias finas de queijo muçarela.\", Custo: R$2.0, Categoria: \"Entrada\", Cozinha: \"Culinária brasileira\"}, Extra: {Nome: \"Muçarela\", Descrição: \"Fatias finas de queijo muçarela.\", Custo: R$2.0, Categoria: \"Entrada\", Cozinha: \"Culinária brasileira\"}}, Prato principal: {Nome: \"Feijoada\", Descrição: \"Feijoada completa com arroz, couve, farofa, laranja e torresmo.\", Custo: R$30.0, Categoria: \"Prato principal\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Farofa\", Descrição: \"Farinha de mandioca torrada com bacon e ovos.\", Custo: R$5.0, Categoria: \"Prato principal\", Cozinha: \"Culinária brasileira\"}}, Bebida: {Nome: \"Caipirinha\", Descrição: \"Cachaça, limão, açúcar e gelo.\", Custo: R$10.0, Categoria: \"Bebida\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Mel\", Descrição: \"Mel puro.\", Custo: R$3.0, Categoria: \"Bebida\", Cozinha: \"Culinária brasileira\"}}, Sobremesa: {Nome: \"Brigadeiro\", Descrição: \"Doce de chocolate com leite condensado e chocolate granulado.\", Custo: R$5.0, Categoria: \"Sobremesa\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Doce de leite\", Descrição: \"Doce de leite pastoso.\", Custo: R$4.0, Categoria: \"Sobremesa\", Cozinha: \"Culinária brasileira\"}}}\n" +
                "Um cliente se desconectou.\n" + "Um novo cliente se conectou.\n", serverOutput
                    );
    }

    private static String findDefaultHost() {
        return Manager.getInstance().getHost();
    }

    private static int findDefaultPort() {
        return Manager.getInstance().getDefaultSocketPort() + 300;
    }

    public static String simulateEmployeeActions(
            ExecutorService clientExecutor,
            String host,
            int port,
            String simulatedInput
                                                )
            throws ExecutionException, InterruptedException, TimeoutException {
        ByteArrayOutputStream employeeByteArrayOutputStream = new ByteArrayOutputStream();

        Future<String> employeeFuture = clientExecutor.submit(() -> {
            try {
                Client.setClientPrintStream(new PrintStream(employeeByteArrayOutputStream));
                System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

                Client.main(new String[]{
                        host,
                        String.valueOf(port),
                        "-e"
                });

                Client.setClientPrintStream(System.out);
                System.setIn(System.in);

                return "A interface de usuário do funcionário foi executada com sucesso.";
            }
            catch (Exception e) {
                return "A execução da interface de usuário do funcionário falhou: " + e.getMessage();
            }
        });

        // Wait for client execution
        String result = employeeFuture.get(5, TimeUnit.MINUTES);
        //        assertEquals("A interface de usuário do funcionário foi executada com sucesso.", result);
        Thread.sleep(2000);

        return employeeByteArrayOutputStream.toString();
    }

    @Test
    public void shouldPrintAppetizer() {
        Employee employee = new Employee(null, null, null, new PrintStream(this.employeeByteArrayOutputStream));
        MenuComponent component = ComponentFacade.createAppetizer(
                appetizerRecord.cuisine(),
                appetizerRecord.name(),
                appetizerRecord.description(),
                appetizerRecord.cost()
                                                                 );
        employee.printMenuComponent(component);
        String employeeOutput = this.employeeByteArrayOutputStream.toString();
        assertEquals("     Pão de alho - R$6.0\n", employeeOutput);
    }

    @Test
    public void shouldPrintDecoratedAppetizer() {
        Employee employee = new Employee(null, null, null, new PrintStream(this.employeeByteArrayOutputStream));

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
        String employeeOutput = this.employeeByteArrayOutputStream.toString();
        assertEquals("     Pão de alho - R$8.0 - Extras: Muçarela.\n", employeeOutput);
    }

    @Test
    public void shouldPrintOrder() {
        Employee employee = new Employee(null, null, null, new PrintStream(this.employeeByteArrayOutputStream));
        var order = this.createOrder();
        employee.printOrder(order);
        String employeeOutput = this.employeeByteArrayOutputStream.toString();
        assertEquals(
                "Recebido: Alice Andrade - R$67.0 via Cartão de Crédito\n" +
                "     Pão de alho - R$10.0 - Extras: Muçarela. Muçarela.\n" +
                "     Feijoada - R$35.0 - Extras: Farofa.\n" + "     Caipirinha - R$13.0 - Extras: Mel.\n" +
                "     Brigadeiro - R$9.0 - Extras: Doce de leite.\n", employeeOutput
                    );
    }

    private Order createOrder() {
        this.orderBuilder.setCustomerName(customerName);
        this.orderBuilder.setAppetizer(appetizerRecord);
        this.orderBuilder.decorateAppetizer(appetizerRecordDecorator);
        this.orderBuilder.decorateAppetizer(appetizerRecordDecorator);
        this.orderBuilder.setBeverage(beverageRecord);
        this.orderBuilder.decorateBeverage(beverageRecordDecorator);
        this.orderBuilder.setMainCourse(mainCourseRecord);
        this.orderBuilder.decorateMainCourse(mainCourseRecordDecorator);
        this.orderBuilder.setDessert(dessertRecord);
        this.orderBuilder.decorateDessert(dessertRecordDecorator);
        this.orderBuilder.setPaymentStrategy(new CreditCard("1234 5678 1234 5678"));
        return this.orderBuilder.build();
    }

    @Test
    public void shouldPrintOrders() {
        Employee employee = new Employee(null, null, null, new PrintStream(this.employeeByteArrayOutputStream));

        var firstOrder = this.createOrder();
        firstOrder.advance();
        var secondOrder = this.createOrder();

        ConcurrentHashMap<Integer, Order> orders = new ConcurrentHashMap<>();
        orders.put(1, firstOrder);
        orders.put(2, secondOrder);

        employee.printOrders(orders);
        String employeeOutput = this.employeeByteArrayOutputStream.toString();
        assertEquals(
                "1. Preparando: Alice Andrade - R$67.0 via Cartão de Crédito\n" +
                "     Pão de alho - R$10.0 - Extras: Muçarela. Muçarela.\n" +
                "     Feijoada - R$35.0 - Extras: Farofa.\n" + "     Caipirinha - R$13.0 - Extras: Mel.\n" +
                "     Brigadeiro - R$9.0 - Extras: Doce de leite.\n" +
                "2. Recebido: Alice Andrade - R$67.0 via Cartão de Crédito\n" +
                "     Pão de alho - R$10.0 - Extras: Muçarela. Muçarela.\n" +
                "     Feijoada - R$35.0 - Extras: Farofa.\n" + "     Caipirinha - R$13.0 - Extras: Mel.\n" +
                "     Brigadeiro - R$9.0 - Extras: Doce de leite.\n", employeeOutput
                    );
    }

    @BeforeEach
    void startServer() {
        this.serverExecutor = Executors.newSingleThreadExecutor();
        this.clientExecutor = Executors.newFixedThreadPool(2);

        this.serverExecutor.submit(() -> {
            this.server = new Server();
            this.server.run(EmployeeTest.findDefaultPort());
        });

        // Wait for server to start
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void stopServer() {
        this.clientExecutor.shutdownNow();
        this.serverExecutor.shutdownNow();
        this.clientExecutor = null;
        this.serverExecutor = null;
        this.server = null;
    }

}

/* Testing path
Bruno Barros
1

 */