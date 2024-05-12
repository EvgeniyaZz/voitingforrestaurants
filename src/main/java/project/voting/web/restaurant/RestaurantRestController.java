package project.voting.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import project.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController extends AbstractRestaurantController {

    public static final String REST_URL = "/api/restaurants";

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping("/{id}/with-meals")
    public Restaurant getWithMeals(@PathVariable int id) {
        return super.getWithMeals(id);
    }

    @GetMapping("/date")
    public List<Restaurant> getByDate(@RequestParam @Nullable LocalDate added) {
        return super.getByDate(added);
    }

    @GetMapping("/with-meals/date")
    public List<Restaurant> getWithMealsByDate(@RequestParam @Nullable LocalDate added) {
        return super.getWithMealsByDate(added);
    }
}