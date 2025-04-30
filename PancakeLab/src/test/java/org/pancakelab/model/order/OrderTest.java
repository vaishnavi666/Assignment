package org.pancakelab.model.order;

import org.junit.jupiter.api.Test;
import org.pancakelab.model.Address;
import org.pancakelab.model.pancakes.Pancake;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @org.junit.jupiter.api.Order(1)
    void givenValidAddress_whenOrderCreated_thenHasIdAndAddress() {
        // setup
        Address address = new Address(1, 101);

        // exercise
        Order order = new Order(address);

        // verify
        assertNotNull(order.getId());
        assertEquals(address, order.getAddress());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void givenPancake_whenAdded_thenStoredCorrectly() {
        // setup
        Order order = new Order(new Address(1, 101));
        Pancake pancake = new Pancake(List.of("Milk Chocolate"));

        // exercise
        Map<Pancake, Integer> pancakes = order.addPancakes(pancake, 2);

        // verify
        assertEquals(2, pancakes.get(pancake));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void givenPancake_whenRemoved_thenQuantityUpdated() {
        // setup
        Order order = new Order(new Address(1, 101));
        Pancake pancake = new Pancake(List.of("Milk Chocolate"));
        order.addPancakes(pancake, 3);

        // exercise
        order.removePancakes(pancake, 1);

        // verify
        assertEquals(2, order.getPancakes().get(pancake));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void givenPancakeRemovedCompletely_whenCountEquals_thenEntryIsRemoved() {
        // setup
        Order order = new Order(new Address(1, 101));
        Pancake pancake = new Pancake(List.of("Milk Chocolate"));
        order.addPancakes(pancake, 2);

        // exercise
        order.removePancakes(pancake, 2);

        // verify
        assertFalse(order.getPancakes().containsKey(pancake));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void testOrderEqualityBasedOnId() {
        // setup
        Address address = new Address(1, 101);
        Order order1 = new Order(address);
        Order order2 = new Order(address);

        // exercise & verify
        assertNotEquals(order1, order2);
        assertEquals(order1, order1);
    }
}
