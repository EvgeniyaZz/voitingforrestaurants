package project.voting.repository.restaurant;

import project.voting.model.Restaurant;

import java.util.Date;
import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant getWithMeals(int id);

    public List<Restaurant> getByDate(Date added);
}
