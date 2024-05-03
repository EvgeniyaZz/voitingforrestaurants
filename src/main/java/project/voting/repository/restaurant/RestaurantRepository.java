package project.voting.repository.restaurant;

import project.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant getWithMeals(int id);

    List<Restaurant> getByDate(LocalDate added);

    List<Restaurant> getWithMealsByDate(LocalDate added);
}
