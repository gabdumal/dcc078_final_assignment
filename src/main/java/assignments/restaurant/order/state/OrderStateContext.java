/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order.state;

import java.io.Serial;
import java.io.Serializable;

public class OrderStateContext
        implements Serializable {

    @Serial
    private static final long  serialVersionUID = 1L;
    private              State state;

    public OrderStateContext() {
        this.state = new New();
    }

    public void advance() {
        this.state.advance(this);
    }

    public StateType getStateType() {
        return this.state.getType();
    }

    public void setState(State state) {
        this.state = state;
    }

}
