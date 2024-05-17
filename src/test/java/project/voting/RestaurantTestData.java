package project.voting;

import project.voting.model.Restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static project.voting.DishTestData.*;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes", "votes", "added");

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_DISHES_MATCHER = MatcherFactory.usingAssertions(Restaurant.class,
            (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("added", "dishes.restaurant", "votes").isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields("added", "dishes.restaurant", "votes").isEqualTo(e));

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT3_ID = 3;
    public static final int NOT_FOUND = 10;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Restaurant Schengen");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Brisket Eat & Fun");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Smoke BBQ");

    static {
        restaurant1.setDishes(dishes1);
        restaurant2.setDishes(dishes2);
        restaurant3.setDishes(dishes3);
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "newRestaurant");
    }
}