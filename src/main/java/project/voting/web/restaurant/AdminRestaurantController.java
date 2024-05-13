package project.voting.web.restaurant;

import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.voting.model.Restaurant;
import project.voting.to.RestaurantTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static project.voting.util.RestaurantUtil.createNewFromTo;
import static project.voting.util.RestaurantUtil.updateFromTo;
import static project.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/api/admin/restaurants";

    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("create restaurant {}", restaurantTo);
        checkNew(restaurantTo);
        Restaurant created = repository.save(createNewFromTo(restaurantTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("update restaurant {}", id);
        repository.save(updateFromTo(repository.getExisted(id), restaurantTo));
    }

    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        repository.deleteExisted(id);
    }

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/{id}/with-meals")
    public ResponseEntity<Restaurant> getWithMeals(@PathVariable int id) {
        return super.getWithMeals(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @Override
    @GetMapping("/date")
    public List<Restaurant> getByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate added) {
        return super.getByDate(added);
    }

    @Override
    @GetMapping("/with-meals/date")
    public List<Restaurant> getWithMealsByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate added) {
        return super.getWithMealsByDate(added);
    }
}