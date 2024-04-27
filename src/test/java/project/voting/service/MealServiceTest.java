package project.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.model.Meal;
import project.voting.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static project.voting.MealTestData.*;
import static project.voting.RestaurantTestData.RESTAURANT1_ID;

class MealServiceTest extends AbstractServiceTest {

    @Autowired
    MealService mealService;

    @Test
    void create() {
        Meal created = mealService.create(getNew(), RESTAURANT1_ID);
        int newId = created.id();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealService.get(newId, RESTAURANT1_ID), newMeal);
    }

    @Test
    void update() {
        Meal updated = getUpdated();
        mealService.update(updated, RESTAURANT1_ID);
        MEAL_MATCHER.assertMatch(mealService.get(MEAL1_ID, RESTAURANT1_ID), getUpdated());
    }

    @Test
    void delete() {
        mealService.delete(MEAL1_ID, RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, RESTAURANT1_ID));
    }

    @Test
    void get() {
        Meal meal = mealService.get(MEAL1_ID, RESTAURANT1_ID);
        MEAL_MATCHER.assertMatch(meal, meal1);
    }

    @Test
    void getAll() {
        MEAL_MATCHER.assertMatch(mealService.getAll(), allMeals);
    }
}