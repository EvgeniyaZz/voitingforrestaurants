package project.voting.util;

import project.voting.model.Meal;
import project.voting.to.MealTo;

public class MealUtil {

    public static Meal createNewFromTo(MealTo mealTo) {
        return new Meal(null, mealTo.getName(), mealTo.getPrice());
    }

    public static Meal updateFromTo(Meal meal, MealTo mealTo) {
        meal.setName(mealTo.getName());
        meal.setPrice(mealTo.getPrice());
        return meal;
    }
}
