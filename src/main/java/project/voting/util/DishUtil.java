package project.voting.util;

import lombok.experimental.UtilityClass;
import project.voting.model.Dish;
import project.voting.to.DishTo;

@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getPrice(), dishTo.getAdded());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        dish.setAdded(dishTo.getAdded());
        return dish;
    }
}
