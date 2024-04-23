package project.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import project.voting.model.Restaurant;
import project.voting.repository.restaurant.DataJpaRestaurantRepository;

import java.util.Date;
import java.util.List;

import static project.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    @Autowired
    DataJpaRestaurantRepository dataJpaRestaurantRepository;

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return dataJpaRestaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(dataJpaRestaurantRepository.delete(id), id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(dataJpaRestaurantRepository.get(id), id);
    }

    public List<Restaurant> getAll() {
        return dataJpaRestaurantRepository.getAll();
    }

    public Restaurant getWithMeals(int id) {
        return checkNotFoundWithId(dataJpaRestaurantRepository.getWithMeals(id), id);
    }

    public List<Restaurant> getByDate(Date date) {
        return dataJpaRestaurantRepository.getByDate(date);
    }
}
