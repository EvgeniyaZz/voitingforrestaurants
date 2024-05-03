package project.voting.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.model.Restaurant;
import project.voting.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

import static project.voting.util.ValidationUtil.assureIdConsistent;
import static project.voting.util.ValidationUtil.checkNew;

public class AbstractRestaurantController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return restaurantService.create(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
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
