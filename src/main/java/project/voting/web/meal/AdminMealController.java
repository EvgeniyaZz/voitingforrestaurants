package project.voting.web.meal;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.voting.model.Meal;
import project.voting.repository.MealRepository;
import project.voting.service.MealService;
import project.voting.to.MealTo;
import project.voting.util.MealUtil;

import java.net.URI;
import java.util.List;

import static project.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMealController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminMealController {

    public static final String REST_URL = "/api/admin/restaurants/{restaurantId}/meals";

    private final MealRepository repository;
    private final MealService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody MealTo mealTo, @PathVariable int restaurantId) {
        log.info("create {}", mealTo);
        checkNew(mealTo);
        Meal created = service.save(MealUtil.createNewFromTo(mealTo), restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.id())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MealTo mealTo, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update meal {} for restaurant {}", mealTo, restaurantId);
        Meal meal = repository.getBelonged(id, restaurantId);
        service.save(MealUtil.updateFromTo(meal, mealTo), restaurantId);
    }

    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete meal {} for restaurant {}", id, restaurantId);
        Meal meal = repository.getBelonged(id, restaurantId);
        repository.delete(meal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get meal {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(repository.get(id, restaurantId));

    }

    @GetMapping
    public List<Meal> getAll(@PathVariable int restaurantId) {
        log.info("get all meal for restaurant {}", restaurantId);
        return repository.getAll(restaurantId);
    }
}