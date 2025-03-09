/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerTest {

    private static final Manager MANAGER = Manager.getInstance();

    @Test
    public void shouldReturnSameRestaurantInstance() {
        assertEquals(MANAGER, Manager.getInstance());
    }

    @Test
    public void shouldReturnSameRestaurantInstanceAfterMultipleCalls() {
        assertEquals(MANAGER, Manager.getInstance());
        assertEquals(MANAGER, Manager.getInstance());
        assertEquals(MANAGER, Manager.getInstance());
    }

}