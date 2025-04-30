package org.pancakelab.model;


import java.util.Objects;

public class Address {
    private final int building;
    private final int room;

    public Address(int building, int room) {
        if (building <= 0 || room <= 0) {
            throw new IllegalArgumentException("Building and room must be positive. Cannot proceed with ordering");
        }
        this.building = building;
        this.room = room;
    }

}
