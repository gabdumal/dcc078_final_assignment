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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    private static final String                customerName = "Alice Andrade";
    private              ExecutorService       clientExecutor;
    private              Server                server;
    private              ByteArrayOutputStream serverByteArrayOutputStream;
    private              ExecutorService       serverExecutor;

    @AfterEach
    void resetOutputStreams() {
        Server.setServerPrintStream(System.out);
        this.serverByteArrayOutputStream = null;
    }

    @BeforeEach
    void setOutputStreams() {
        this.serverByteArrayOutputStream = new ByteArrayOutputStream();
        Server.setServerPrintStream(new PrintStream(this.serverByteArrayOutputStream));
    }

    @Test
    void shouldCreateOrderOnServer()
            throws Exception {
        var orders = this.server.getOrders();
        var ordersSize = orders.size();

        var customerOutput = CustomerTest.simulateCustomerActions(
                this.clientExecutor,
                CustomerTest.findDefaultHost(),
                CustomerTest.findDefaultPort(),
                "Alice Andrade\n" + "1\n" + "1\n" + "1\n" + "S\n" + "1\n" + "1\n" + "S\n" + "1\n" + "1\n" + "S\n" +
                "1\n" + "1\n" + "S\n" + "1\n" + "1\n" + "1234 5678 1234 5678\n" + "\n"
                                                                 );

        // Check if order was processed
        assertEquals(ordersSize + 1, orders.size());

        var order = orders.get(1);
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

        assertEquals(
                "Boas-vindas à interface de pedidos do Restaurante!\n" + "\n" + "Qual é seu nome?\n" + "\n" +
                "Tipos de pedido:\n" + "1. Entrega\n" + "2. Retirada\n" + "3. Mesa\n" +
                "Digite o número do tipo de pedido que deseja:\n" + "\n" + "Culinárias:\n" +
                "1. Culinária brasileira\n" + "2. Culinária italiana\n" + "Escolha a culinária que deseja pedir:\n" +
                "\n" + "Entrada:\n" + "1. Pão de alho\n" + "     R$6.0\n" +
                "     Pão francês assado ao molho de alho, azeite e ervas.\n" + "2. Coxinha\n" + "     R$5.0\n" +
                "     Massa de batata recheada com frango desfiado.\n" + "3. Pastel\n" + "     R$4.0\n" +
                "     Massa de pastel recheada com carne moída.\n" + "4. Caldo de feijão\n" + "     R$12.0\n" +
                "     Caldo de feijão temperado com bacon e linguiça.\n" + "Digite o número do item que deseja:\n" +
                "\n" + "Deseja adicionar algum acompanhamento? (S/N)\n" + "\n" + "Acompanhamentos:\n" +
                "1. Muçarela\n" + "     R$2.0\n" + "     Fatias finas de queijo muçarela.\n" + "2. Maionese\n" +
                "     R$1.0\n" + "     Maionese caseira com ervas.\n" + "Digite o número do item que deseja:\n" + "\n" +
                "Prato principal:\n" + "1. Feijoada\n" + "     R$30.0\n" +
                "     Feijoada completa com arroz, couve, farofa, laranja e torresmo.\n" +
                "Digite o número do item que deseja:\n" + "\n" + "Deseja adicionar algum acompanhamento? (S/N)\n" +
                "\n" + "Acompanhamentos:\n" + "1. Farofa\n" + "     R$5.0\n" +
                "     Farinha de mandioca torrada com bacon e ovos.\n" + "Digite o número do item que deseja:\n" +
                "\n" + "Bebida:\n" + "1. Caipirinha\n" + "     R$10.0\n" + "     Cachaça, limão, açúcar e gelo.\n" +
                "Digite o número do item que deseja:\n" + "\n" + "Deseja adicionar algum acompanhamento? (S/N)\n" +
                "\n" + "Acompanhamentos:\n" + "1. Mel\n" + "     R$3.0\n" + "     Mel puro.\n" +
                "Digite o número do item que deseja:\n" + "\n" + "Sobremesa:\n" + "1. Brigadeiro\n" + "     R$5.0\n" +
                "     Doce de chocolate com leite condensado e chocolate granulado.\n" +
                "Digite o número do item que deseja:\n" + "\n" + "Deseja adicionar algum acompanhamento? (S/N)\n" +
                "\n" + "Acompanhamentos:\n" + "1. Doce de leite\n" + "     R$4.0\n" + "     Doce de leite pastoso.\n" +
                "Digite o número do item que deseja:\n" + "\n" + "Métodos de pagamento:\n" + "1. Cartão de Crédito\n" +
                "2. Pix\n" + "3. Dinheiro\n" + "Escolha um método de pagamento:\n" +
                "Digite o número do cartão de crédito:\n" + "\n" + "Seu pedido foi recebido com sucesso!\n" + "\n" +
                "Foi um prazer atendê-lo! Até a próxima.\n" + "\n", customerOutput
                    );

        String serverOutput = this.serverByteArrayOutputStream.toString();
        assertEquals(
                "Um novo cliente se conectou.\n" +
                "Pedido recebido: {Status: \"Recebido\", Tipo: \"Entrega\", Cliente: \"Alice Andrade\", Custo: 65.0, Pagamento: \"Cartão de Crédito\", Entrada: {Nome: \"Pão de alho\", Descrição: \"Pão francês assado ao molho de alho, azeite e ervas.\", Custo: R$6.0, Categoria: \"Entrada\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Muçarela\", Descrição: \"Fatias finas de queijo muçarela.\", Custo: R$2.0, Categoria: \"Entrada\", Cozinha: \"Culinária brasileira\"}}, Prato principal: {Nome: \"Feijoada\", Descrição: \"Feijoada completa com arroz, couve, farofa, laranja e torresmo.\", Custo: R$30.0, Categoria: \"Prato principal\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Farofa\", Descrição: \"Farinha de mandioca torrada com bacon e ovos.\", Custo: R$5.0, Categoria: \"Prato principal\", Cozinha: \"Culinária brasileira\"}}, Bebida: {Nome: \"Caipirinha\", Descrição: \"Cachaça, limão, açúcar e gelo.\", Custo: R$10.0, Categoria: \"Bebida\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Mel\", Descrição: \"Mel puro.\", Custo: R$3.0, Categoria: \"Bebida\", Cozinha: \"Culinária brasileira\"}}, Sobremesa: {Nome: \"Brigadeiro\", Descrição: \"Doce de chocolate com leite condensado e chocolate granulado.\", Custo: R$5.0, Categoria: \"Sobremesa\", Cozinha: \"Culinária brasileira\", Extra: {Nome: \"Doce de leite\", Descrição: \"Doce de leite pastoso.\", Custo: R$4.0, Categoria: \"Sobremesa\", Cozinha: \"Culinária brasileira\"}}}\n",
                serverOutput
                    );
    }

    public static String simulateCustomerActions(
            ExecutorService clientExecutor,
            String host,
            int port,
            String simulatedInput
                                                )
            throws ExecutionException, InterruptedException, TimeoutException {
        ByteArrayOutputStream customerByteArrayOutputStream = new ByteArrayOutputStream();

        Future<String> customerFuture = clientExecutor.submit(() -> {
            try {
                Client.setClientPrintStream(new PrintStream(customerByteArrayOutputStream));
                System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

                Client.main(new String[]{
                        host,
                        String.valueOf(port),
                        "-c"
                });

                Client.setClientPrintStream(System.out);
                System.setIn(System.in);

                return "A interface de usuário do cliente foi executada com sucesso.";
            }
            catch (Exception e) {
                return "A execução da interface de usuário do cliente falhou: " + e.getMessage();
            }
        });

        // Wait for client execution
        String result = customerFuture.get(5, TimeUnit.MINUTES);
        assertEquals("A interface de usuário do cliente foi executada com sucesso.", result);
        Thread.sleep(2000);

        return customerByteArrayOutputStream.toString();
    }

    private static String findDefaultHost() {
        return Manager.getInstance().getHost();
    }

    private static int findDefaultPort() {
        return Manager.getInstance().getDefaultSocketPort() + 200;
    }

    @BeforeEach
    void startServer() {
        this.clientExecutor = Executors.newFixedThreadPool(2);
        this.serverExecutor = Executors.newSingleThreadExecutor();

        this.serverExecutor.submit(() -> {
            this.server = new Server();
            this.server.run(CustomerTest.findDefaultPort());
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
Alice Andrade
1
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