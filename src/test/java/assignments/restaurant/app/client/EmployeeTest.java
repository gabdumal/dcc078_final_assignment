/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.Manager;
import assignments.restaurant.app.server.Request;
import assignments.restaurant.app.server.Server;
import assignments.restaurant.data.MenuComponentRecord;
import assignments.restaurant.data.Query;
import assignments.restaurant.order.Order;
import assignments.restaurant.order.OrderBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
    private final        ByteArrayOutputStream employeeOutputStream      = new ByteArrayOutputStream();
    private final        ByteArrayOutputStream serverOutputStream        = new ByteArrayOutputStream();

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
        serverExecutor.shutdownNow();
        clientExecutor.shutdownNow();
    }

    @BeforeEach
    void setUp() {
        clientExecutor = Executors.newFixedThreadPool(2);
        System.setOut(new PrintStream(serverOutputStream));
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
                System.setOut(new PrintStream(employeeOutputStream));
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

        String serverOutput = serverOutputStream.toString();
        String employeeOutput = employeeOutputStream.toString();
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