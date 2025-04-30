package org.pancakelab.builder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.pancakelab.model.Address;
import org.pancakelab.model.order.NullOrder;
import org.pancakelab.model.order.Order;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderBuilderTest {

    @Test
    @org.junit.jupiter.api.Order(1)
    void givenValidAddress_whenBuild_thenReturnsOrder() {
        // setup
        Address address = new Address(1, 101);

        // exercise
        Order order = new OrderBuilder(address).build();

        // verify
        assertNotNull(order);
        assertFalse(order instanceof NullOrder);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void givenNullAddress_whenBuild_thenReturnsNullOrder() {
        // exercise
        Order order = new OrderBuilder(null).build();

        // verify
        assertTrue(order instanceof NullOrder);
    }
}