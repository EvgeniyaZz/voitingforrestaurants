package project.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.model.Restaurant;
import project.voting.util.exception.NotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static project.voting.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @Test
    void create() {
        Restaurant created = restaurantService.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        restaurantService.update(updated);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(RESTAURANT1_ID), getUpdated());
    }

    @Test
    void delete() {
        restaurantService.delete(RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT1_ID));
    }

    @Test
    void get() {
        Restaurant restaurant = restaurantService.get(RESTAURANT1_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, restaurant1);
    }

    @Test
    void getAll() {
        RESTAURANT_MATCHER.assertMatch(restaurantService.getAll(), restaurants);
    }

    @Test
    void getWithMeals() {
        Restaurant restaurantWithMeal = restaurantService.getWithMeals(RESTAURANT1_ID);
        RESTAURANT_WITH_MENU_MATCHER.assertMatch(restaurantWithMeal, restaurant1);
    }

    @Test
    void getByDate() {
        RESTAURANT_MATCHER.assertMatch(restaurantService.getByDate(LocalDate.now()), restaurantsByDate);
    }
}