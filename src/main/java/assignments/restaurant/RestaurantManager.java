/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

/*
 * Design Pattern: Singleton
 *
 * This class implements the Singleton design pattern to manage restaurant operations.
 * Only one instance of this class can exist at any time.
 */

package assignments.restaurant;

/**
 * Singleton class to manage restaurant operations.
 */
public class RestaurantManager {

    private static RestaurantManager instance;

    private RestaurantManager() {
    }

    public static synchronized RestaurantManager getInstance() {
        if (instance == null) {
            instance = new RestaurantManager();
        }
        return instance;
    }

}
