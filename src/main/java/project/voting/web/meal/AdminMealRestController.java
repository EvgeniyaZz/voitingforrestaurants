package project.voting.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.voting.model.Meal;
import project.voting.service.MealService;

import javax.validation.Valid;
import java.net.URI;

import static project.voting.util.ValidationUtil.assureIdConsistent;
import static project.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMealRestController {

    public static final String REST_URL = "/admin/restaurants/{restaurantId}/meals";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MealService mealService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> create(@Valid @RequestBody Meal meal, @PathVariable int restaurantId) {
        log.info("create {}", meal);
        checkNew(meal);
        Meal created = mealService.create(meal, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.id())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Meal meal, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update meal {} in restaurant {}", meal, restaurantId);
        assureIdConsistent(meal, id);
        mealService.update(meal, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete meal {} in restaurant {}", id, restaurantId);
        mealService.delete(id, restaurantId);
    }

    @GetMapping("/{id}")
    public Meal get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get meal {} in restaurant {}", id, restaurantId);
        return mealService.get(id, restaurantId);
    }
}