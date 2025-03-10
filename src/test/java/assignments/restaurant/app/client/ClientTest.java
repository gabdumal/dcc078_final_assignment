/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.app.client;

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
    public void shouldGetCustomerInterface() {
        var arguments = new String[]{
                customerInterface
        };
        var userInterfaceType = Client.processArguments(arguments);
        assertEquals(UserInterfaceType.Customer, userInterfaceType);
    }

    @Test
    public void shouldGetEmployeeInterface() {
        var arguments = new String[]{
                employeeInterface
        };
        var userInterfaceType = Client.processArguments(arguments);
        assertEquals(UserInterfaceType.Employee, userInterfaceType);
    }

    @Test
    public void shouldThrowExceptionWhenProcessingInvalidArgument() {
        var arguments = new String[]{
                "-i",
                };
        var exception = assertThrows(IllegalArgumentException.class, () -> Client.processArguments(arguments));
        assertEquals(invalidArgumentsMessage, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenProcessingNoArguments() {
        var arguments = new String[]{};
        var exception = assertThrows(IllegalArgumentException.class, () -> Client.processArguments(arguments));
        assertEquals(invalidArgumentsMessage, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenProcessingTwoArguments() {
        var arguments = new String[]{
                "-r",
                "-c"
        };
        var exception = assertThrows(IllegalArgumentException.class, () -> Client.processArguments(arguments));
        assertEquals(invalidArgumentsMessage, exception.getMessage());
    }

}
