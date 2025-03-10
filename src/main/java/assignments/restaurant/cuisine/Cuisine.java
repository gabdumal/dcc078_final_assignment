/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.cuisine;

/**
 * Enum representing different types of cuisine.
 */
public enum Cuisine {
    Brazilian,
    Italian;

    /**
     * Retrieves the name of the cuisine in the local language.
     *
     * @return The name of the cuisine in the local language.
     */
    private String getCuisineName() {
        return switch (this) {
            case Brazilian -> "Culinária brasileira";
            case Italian -> "Culinária italiana";
        };
    }
}