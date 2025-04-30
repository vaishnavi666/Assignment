package org.pancakelab.model.pancake;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.pancakelab.model.pancakes.Pancake;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PancakeTest {

    private Pancake pancake;
    private final List<String> ingredients = List.of("Milk Chocolate", "Dark Chocolate");

    @BeforeAll
    void setUp() {
        pancake = new Pancake(ingredients);
    }

    @Test
    @Order(1)
    void GivenValidIngredients_WhenGetIngredients_ThenReturnsCopyOfList() {
        // exercise
        List<String> result = pancake.getIngredients();

        // verify
        assertEquals(ingredients, result);
        assertNotSame(ingredients, result); // Ensures a copy is returned
    }

    @Test
    @Order(2)
    void GivenValidIngredients_WhenGetDescription_ThenReturnsExpectedString() {
        // exercise
        String description = pancake.getDescription();

        // verify
        assertEquals("Delicious pancake with Milk Chocolate, Dark Chocolate!", description);
    }

    @Test
    @Order(3)
    void GivenValidIngredients_WhenGetName_ThenReturnsFormattedName() {
        // exercise
        String name = pancake.getName(ingredients);

        // verify
        assertEquals("DarkChocolateMilkChocolatePancake", name);
    }

    @Test
    @Order(4)
    void GivenEmptyIngredientList_WhenGetName_ThenReturnsEmptyPancake() {
        // setup
        Pancake emptyPancake = new Pancake(List.of());

        // exercise
        String name = emptyPancake.getName(List.of());

        // verify
        assertEquals("EmptyPancake", name);
    }

    @Test
    @Order(5)
    void GivenNullIngredientList_WhenGetName_ThenReturnsEmptyPancake() {
        // exercise
        String name = pancake.getName(null);

        // verify
        assertEquals("EmptyPancake", name);
    }
}
