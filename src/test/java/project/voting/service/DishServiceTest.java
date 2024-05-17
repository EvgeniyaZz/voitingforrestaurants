package project.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.model.Dish;
import project.voting.repository.DishRepository;

import static project.voting.DishTestData.DISH_MATCHER;
import static project.voting.DishTestData.getNew;
import static project.voting.RestaurantTestData.RESTAURANT1_ID;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    DishService service;

    @Autowired
    DishRepository repository;

    @Test
    void save() {
        Dish created = service.save(getNew(), RESTAURANT1_ID);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getBelonged(newId, RESTAURANT1_ID), newDish);
    }
}