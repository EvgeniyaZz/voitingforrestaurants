package project.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import project.voting.model.Restaurant;
import project.voting.repository.restaurant.DataJpaRestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static project.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    @Autowired
    DataJpaRestaurantRepository restaurantRepository;

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        restaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    public Restaurant getWithMeals(int id) {
        return checkNotFoundWithId(restaurantRepository.getWithMeals(id), id);
    }

    public List<Restaurant> getByDate(LocalDate date) {
        return restaurantRepository.getByDate(date);
    }
}
