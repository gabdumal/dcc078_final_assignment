/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.order.payment;

import java.io.Serial;

public class CreditCard
        implements PaymentStrategy {

    @Serial
    private static final long   serialVersionUID = 1L;
    private final        String cardNumber;

    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String pay(double amount) {
        return "Pagamento de R$" + amount + " realizado com sucesso via " + this.getPaymentType() + " de número " +
               this.cardNumber + ".";
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.CreditCard;
    }

}
