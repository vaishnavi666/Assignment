package org.pancakelab.model.order;

import org.pancakelab.model.Address;
import org.pancakelab.model.pancakes.Pancake;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Order {
    private final UUID id;
    private final Address address;
    private final ConcurrentHashMap<Pancake, Integer> pancakes = new ConcurrentHashMap<>();

    public Order(Address address) {
        this.address = address;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public Map<Pancake, Integer> getPancakes() {
        return pancakes;
    }

    public Map<Pancake, Integer> addPancakes(Pancake pancake, int count) {
        if (pancake == null || count <= 0) return Collections.emptyMap();
        pancakes.merge(pancake, count, Integer::sum);
        return pancakes;
    }

    public Map<Pancake, Integer> removePancakes(Pancake pancake, int count) {
        if (pancake == null || count <= 0) return Collections.emptyMap();
        pancakes.computeIfPresent(pancake, (k, v) -> (v <= count) ? null : v - count);
        return pancakes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
