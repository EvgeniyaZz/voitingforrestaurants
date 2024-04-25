package project.voting;

import project.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static project.voting.MealTestData.meals;
import static project.voting.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu", "added");

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER = MatcherFactory.usingAssertions(Restaurant.class,
            (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("added", "menu.restaurant").isEqualTo(e),
            (a, e) -> {
                throw new UnsupportedOperationException();
            });

    public static final int RESTAURANT1_ID = START_SEQ + 3;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Restaurant Schengen");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Brisket Eat & Fun");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "Smoke BBQ");

    public static final List<Restaurant> restaurants = List.of(restaurant2, restaurant1, restaurant3);

    static {
        restaurant1.setMenu(meals);
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