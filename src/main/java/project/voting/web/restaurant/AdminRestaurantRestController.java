package project.voting.web.restaurant;

import org.springframework.lang.Nullable;
import project.voting.model.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.voting.service.RestaurantService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static project.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController {

    static final String REST_URL = "/admin/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant) {
        log.info("update {}", restaurant);
        restaurantService.update(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        restaurantService.delete(id);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return restaurantService.get(id);
    }

    @GetMapping("/{id}/with-meals")
    public Restaurant getWithMeals(@PathVariable int id) {
        log.info("get restaurant {} with meals", id);
        return restaurantService.getWithMeals(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return restaurantService.getAll();
    }

    @GetMapping("/date")
    public List<Restaurant> getByDate(@RequestParam @Nullable LocalDate added) {
        log.info("get restaurants by date");
        return restaurantService.getByDate(added);
    }
}