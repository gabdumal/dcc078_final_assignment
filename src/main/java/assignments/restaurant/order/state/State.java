/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order.state;

import java.io.Serializable;

/*
 * Design Pattern: State
 *
 * This class is part of the State design pattern.
 * It represents the state of an order.
 */

public interface State
        extends Serializable {

    void advance(OrderStateContext context);

    StateType getType();

}
