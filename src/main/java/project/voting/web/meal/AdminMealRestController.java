package project.voting.web.meal;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.voting.model.Meal;
import project.voting.service.MealService;
import project.voting.to.MealTo;
import project.voting.util.MealUtil;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

import static project.voting.util.ValidationUtil.assureIdConsistent;
import static project.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminMealRestController {

    public static final String REST_URL = "/api/admin/restaurants/{restaurantId}/meals";

    MealService mealService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody MealTo mealTo, @PathVariable int restaurantId) {
        log.info("create {}", mealTo);
        checkNew(mealTo);
        Meal created = mealService.create(MealUtil.createNewFromTo(mealTo), restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.id())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MealTo mealTo, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update meal {} in restaurant {}", mealTo, restaurantId);
        assureIdConsistent(mealTo, id);
        Meal meal = mealService.get(id, restaurantId);
        mealService.update(MealUtil.updateFromTo(meal, mealTo), restaurantId);
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

    @GetMapping
    public List<Meal> getAll(@PathVariable int restaurantId) {
        log.info("get all meal in restaurant {}", restaurantId);
        return mealService.getAll(restaurantId);
    }
}