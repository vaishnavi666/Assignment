package org.pancakelab.builder;

import org.pancakelab.model.Address;
import org.pancakelab.model.order.NullOrder;
import org.pancakelab.model.order.Order;

public class OrderBuilder {
    private final Address address;

    public OrderBuilder(Address address) {
        this.address = address;
    }

    public Order build() {
        if (address == null) {
            System.out.println("Invalid address. Cannot create order.");
            return new NullOrder();
        }
        return new Order(address);
    }
}
