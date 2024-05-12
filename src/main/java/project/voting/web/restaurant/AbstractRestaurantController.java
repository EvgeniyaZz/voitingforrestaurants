package project.voting.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.model.Restaurant;
import project.voting.service.RestaurantService;
import project.voting.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

import static project.voting.util.RestaurantUtil.createNewFromTo;
import static project.voting.util.RestaurantUtil.updateFromTo;
import static project.voting.util.ValidationUtil.assureIdConsistent;
import static project.voting.util.ValidationUtil.checkNew;

public class AbstractRestaurantController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    public Restaurant create(RestaurantTo restaurantTo) {
        log.info("create {}", restaurantTo);
        checkNew(restaurantTo);
        return restaurantService.create(createNewFromTo(restaurantTo));
    }

    public void update(RestaurantTo restaurantTo, int id) {
        log.info("update {} with id={}", restaurantTo, id);
        assureIdConsistent(restaurantTo, id);
        restaurantService.update(updateFromTo(restaurantService.get(id), restaurantTo));
    }

    public void delete(int id) {
        log.info("delete restaurant {}", id);
        restaurantService.delete(id);
    }

    public Restaurant get(int id) {
        log.info("get restaurant {}", id);
        return restaurantService.get(id);
    }

    public Restaurant getWithMeals(int id) {
        log.info("get restaurant {} with meals", id);
        return restaurantService.getWithMeals(id);
    }

    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return restaurantService.getAll();
    }

    public List<Restaurant> getByDate(LocalDate added) {
        log.info("get restaurants by date");
        return restaurantService.getByDate(added);
    }

    public List<Restaurant> getWithMealsByDate(LocalDate added) {
        log.info("get restaurants with meals by date");
        return restaurantService.getWithMealsByDate(added);
    }
}
