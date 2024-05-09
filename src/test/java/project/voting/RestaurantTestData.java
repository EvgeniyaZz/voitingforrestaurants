package project.voting;

import project.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static project.voting.MealTestData.*;
import static project.voting.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu", "votes", "added");

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER = MatcherFactory.usingAssertions(Restaurant.class,
            (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("added", "menu.restaurant", "votes").isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields("added", "menu.restaurant", "votes").isEqualTo(e));

    public static final int RESTAURANT1_ID = START_SEQ + 3;
    public static final int RESTAURANT3_ID = RESTAURANT1_ID + 2;
    public static final int NOT_FOUND = 10;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Restaurant Schengen");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Brisket Eat & Fun");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Smoke BBQ");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);
    public static final List<Restaurant> restaurantsByDate = List.of(restaurant2, restaurant1, restaurant3);

    static {
        restaurant1.setMenu(meals1);
        restaurant2.setMenu(meals2);
        restaurant3.setMenu(meals3);
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "newRestaurant", LocalDate.now());
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant1);
        updated.setName("New Name");
        return updated;
    }
}