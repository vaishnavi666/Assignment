package org.pancakelab.model.pancakes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pancake {
    private List<String> ingredients = new ArrayList<>();

    public Pancake(List<String> ingredients) {
        if(ingredients != null){
            this.ingredients = new ArrayList<>(ingredients);
        }

    }

    public List<String> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public String getDescription() {
        return "Delicious pancake with %s!".formatted(String.join(", ", this.ingredients));
    }

    public String getName(List<String> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            return "EmptyPancake";
        }

        return ingredients.stream()
                .sorted()
                .map(ingredient -> Arrays.stream(ingredient.split(" "))
                        .map(word -> word.replaceAll("[^a-zA-Z0-9]", "")) // clean each word
                        .map(this::capitalize) // capitalize each word
                        .collect(Collectors.joining()))
                .collect(Collectors.joining()) + "Pancake";
    }
    private String capitalize(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
