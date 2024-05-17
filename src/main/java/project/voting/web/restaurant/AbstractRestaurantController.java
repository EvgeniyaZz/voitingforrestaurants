package project.voting.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.model.Restaurant;
import project.voting.repository.RestaurantRepository;
import project.voting.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

public class AbstractRestaurantController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RestaurantRepository repository;

    @Autowired
    private RestaurantService service;

    public Restaurant get(int id) {
        log.info("get restaurant {}", id);
        return repository.getExisted(id);
    }

    public Restaurant getWithDishes(int id) {
        log.info("get restaurant {} with dishes", id);
        return repository.getExistedWithDishes(id);
    }

    public Restaurant getWithDishesByDate(int id, LocalDate date) {
        log.info("get restaurant {} with dishes by date {}", id, date);
        return service.getWithDishesByDate(id, date);
    }

    public List<Restaurant> getAllWithDishesToday() {
        log.info("get all restaurants with dishes for today");
        return service.getAllWithDishesByDate(LocalDate.now());
    }
}
