package org.pancakelab.builder;

import org.pancakelab.model.pancakes.NullPancake;
import org.pancakelab.model.pancakes.Pancake;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PancakeBuilder {
    private static final Set<String> allowedIngredients = Set.of(
            "Dark Chocolate",
            "Milk Chocolate",
            "Whipped Cream",
            "Hazelnuts"
    );

    private List<String> ingredients = new ArrayList<>();

    public PancakeBuilder(List<String> ingredients) {
        if (!ingredients.isEmpty() && allowedIngredients.containsAll(ingredients)) {
            this.ingredients = ingredients;
        }
    }
    public Pancake build() {
        if (ingredients != null && !ingredients.isEmpty()) {
            return new Pancake(ingredients);
        }
        return new NullPancake();
    }
    public static Set<String> getAllowedIngredients() {
        return allowedIngredients;
    }
}