/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

import assignments.restaurant.Manager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientTest {

    private static final String customerInterface       = "-c";
    private static final String employeeInterface       = "-e";
    private static final String invalidArgumentsMessage = "Você deve fornecer uma interface de usuário válida: " +
                                                          employeeInterface + " (funcionário) ou " + customerInterface +
                                                          " (cliente)!";

    @Test
    public void shouldGetCustomerUserInterface() {
        String[] arguments = getDefaultArguments();
        arguments[2] = customerInterface;
        var clientArguments = Client.processArguments(arguments);
        assertEquals(UserInterfaceType.Customer, clientArguments.getUserInterface());
    }

    private static String[] getDefaultArguments() {
        return new String[]{
                ClientTest.findDefaultHost(),
                String.valueOf(ClientTest.findDefaultPort()),
                null
        };
    }

    private static String findDefaultHost() {
        return Manager.getInstance().getHost();
    }

    private static int findDefaultPort() {
        return Manager.getInstance().getDefaultSocketPort() + 200;
    }

    @Test
    public void shouldGetEmployeeUserInterface() {
        String[] arguments = getDefaultArguments();
        arguments[2] = employeeInterface;
        var clientArguments = Client.processArguments(arguments);
        assertEquals(UserInterfaceType.Employee, clientArguments.getUserInterface());
    }

    @Test
    public void shouldThrowExceptionWhenProcessingInvalidUserInterface() {
        String[] arguments = getDefaultArguments();
        arguments[2] = "-i";
        var exception = assertThrows(IllegalArgumentException.class, () -> Client.processArguments(arguments));
        assertEquals(invalidArgumentsMessage, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenProcessingLessThanRequiredArguments() {
        String[] arguments = {
                ClientTest.findDefaultHost(),
                String.valueOf(ClientTest.findDefaultPort())
        };

        var exception = assertThrows(IllegalArgumentException.class, () -> Client.processArguments(arguments));
        assertEquals("Você deve fornecer um host, uma porta e uma interface de usuário!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenProcessingNoUserInterface() {
        String[] arguments = getDefaultArguments();
        arguments[2] = "";
        var exception = assertThrows(IllegalArgumentException.class, () -> Client.processArguments(arguments));
        assertEquals(invalidArgumentsMessage, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenProcessingTooMuchArguments() {
        String[] defaultArguments = getDefaultArguments();
        String[] arguments = new String[defaultArguments.length + 1];
        System.arraycopy(defaultArguments, 0, arguments, 0, defaultArguments.length);
        arguments[defaultArguments.length] = "-x";

        var exception = assertThrows(IllegalArgumentException.class, () -> Client.processArguments(arguments));
        assertEquals("Você deve fornecer um host, uma porta e uma interface de usuário!", exception.getMessage());
    }

}
