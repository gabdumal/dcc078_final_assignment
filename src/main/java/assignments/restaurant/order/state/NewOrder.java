/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order.state;

import java.io.Serial;

public class NewOrder
        implements OrderState {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void advance(OrderContext context) {
        context.setState(new PreparingOrder());
    }

    @Override
    public OrderStateType getType() {
        return OrderStateType.New;
    }

}
