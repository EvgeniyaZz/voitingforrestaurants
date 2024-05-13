package project.voting.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import project.voting.model.Restaurant;
import project.voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

public class AbstractRestaurantController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RestaurantRepository repository;

    public Restaurant get(int id) {
        log.info("get restaurant {}", id);
        return repository.getExisted(id);
    }

    public ResponseEntity<Restaurant> getWithMeals(int id) {
        log.info("get restaurant {} with meals", id);
        return ResponseEntity.of(repository.getWithMeals(id));
    }

    public List<Restaurant> getByDate(LocalDate added) {
        log.info("get restaurants by date {}", added);
        return repository.getByDate(added);
    }

    @Cacheable(value = "restaurantsWithMenu", key = "#added")
    public List<Restaurant> getWithMealsByDate(LocalDate added) {
        log.info("get restaurants with meals by date {}", added);
        return repository.getWithMealsByDate(added);
    }
}
