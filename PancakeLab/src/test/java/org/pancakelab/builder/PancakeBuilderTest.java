package org.pancakelab.builder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.pancakelab.model.pancakes.NullPancake;
import org.pancakelab.model.pancakes.Pancake;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PancakeBuilderTest {

    @Test
    @org.junit.jupiter.api.Order(1)
    void givenAllowedIngredients_whenBuild_thenReturnsValidPancake() {
        // setup
        List<String> ingredients = List.of("Milk Chocolate", "Dark Chocolate");
        PancakeBuilder builder = new PancakeBuilder(ingredients);

        // exercise
        Pancake pancake = builder.build();

        // verify
        assertNotNull(pancake);
        assertFalse(pancake instanceof NullPancake);

        List<String> actualIngredients = pancake.getIngredients();
        assertEquals(ingredients, actualIngredients);

        String name = pancake.getName(actualIngredients);
        assertEquals("DarkChocolateMilkChocolatePancake", name);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void givenInvalidIngredients_whenBuild_thenReturnsNullPancake() {
        // setup
        List<String> ingredients = List.of("Strawberry", "Banana");
        PancakeBuilder builder = new PancakeBuilder(ingredients);

        // exercise
        Pancake pancake = builder.build();

        // verify
        assertNotNull(pancake);
        assertTrue(pancake instanceof NullPancake);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void givenEmptyIngredients_whenBuild_thenReturnsNullPancake() {
        // setup
        List<String> ingredients = List.of();
        PancakeBuilder builder = new PancakeBuilder(ingredients);

        // exercise
        Pancake pancake = builder.build();

        // verify
        assertNotNull(pancake);
        assertTrue(pancake instanceof NullPancake);
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void allowedIngredients_shouldContainExpectedItems() {
        // exercise
        Set<String> allowed = PancakeBuilder.getAllowedIngredients();

        // verify
        assertEquals(Set.of("Dark Chocolate", "Milk Chocolate", "Whipped Cream", "Hazelnuts"), allowed);
    }
}
