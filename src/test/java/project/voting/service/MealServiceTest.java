package project.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.model.Meal;
import project.voting.repository.MealRepository;

import static project.voting.MealTestData.MEAL_MATCHER;
import static project.voting.MealTestData.getNew;
import static project.voting.RestaurantTestData.RESTAURANT1_ID;

class MealServiceTest extends AbstractServiceTest {

    @Autowired
    MealService service;

    @Autowired
    MealRepository repository;

    @Test
    void save() {
        Meal created = service.save(getNew(), RESTAURANT1_ID);
        int newId = created.id();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(repository.getBelonged(newId, RESTAURANT1_ID), newMeal);
    }
}