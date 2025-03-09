/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant;

import assignments.restaurant.app.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientTest {

    //    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    //    private final PrintStream           standardOut        = System.out;
    //    private       Scanner               scanner;
    //
    //    @AfterEach
    //    public void afterEach() {
    //        this.scanner = null;
    //        //        UserInterface.setScanner(new Scanner(System.in));
    //        System.setOut(standardOut);
    //    }
    //
    //    @BeforeEach
    //    public void beforeEach() {
    //        System.setOut(new PrintStream(outputStreamCaptor));
    //    }

    @Test
    public void shouldRunMainMethodOnRestaurantInterface() {
        var arguments = new String[]{
                "-r",
                };
        Server.main(arguments);
    }

    @Test
    public void shouldThrowExceptionWhenRunningMainMethodWithInvalidArgument() {
        var arguments = new String[]{
                "-i",
                };
        var exception = assertThrows(IllegalArgumentException.class, () -> Server.main(arguments));
        assertEquals("Invalid argument provided.", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenRunningMainMethodWithTwoArguments() {
        var arguments = new String[]{
                "-r",
                "-c"
        };
        var exception = assertThrows(IllegalArgumentException.class, () -> Server.main(arguments));
        assertEquals("Too many arguments provided.", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenRunningMainMethodWithoutArguments() {
        var arguments = new String[]{};
        var exception = assertThrows(IllegalArgumentException.class, () -> Server.main(arguments));
        assertEquals("No arguments provided.", exception.getMessage());
    }

}
