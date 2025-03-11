/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order.payment;

import java.io.Serial;
import java.io.Serializable;

public class OrderPaymentContext
        implements Serializable {

    @Serial
    private static final long            serialVersionUID = 1L;
    private              PaymentStrategy paymentStrategy;

    public OrderPaymentContext() {
    }

    public PaymentType getPaymentType() {
        return this.paymentStrategy.getPaymentType();
    }

    public String pay(double amount) {
        if (null == this.paymentStrategy) {
            throw new IllegalStateException("Nenhum método de pagamento foi selecionado!");
        }
        return this.paymentStrategy.pay(amount);
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

}
