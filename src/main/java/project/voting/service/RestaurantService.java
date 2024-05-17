package project.voting.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.Dish;
import project.voting.model.Restaurant;
import project.voting.repository.DishRepository;
import project.voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Restaurant getWithDishesByDate(int id, LocalDate date) {
        Restaurant restaurant = restaurantRepository.getExisted(id);
        restaurant.setDishes(dishRepository.getByDate(id, date));
        return restaurant;
    }

    @Cacheable(value = "restaurantsWithDishesByDate")
    public List<Restaurant> getAllWithDishesByDate(LocalDate date) {
        Map<Restaurant, List<Dish>> DishesByRestaurant = dishRepository.getWithRestaurantByDate(date).stream()
                .collect(Collectors.groupingBy(Dish::getRestaurant));

        DishesByRestaurant.forEach(Restaurant::setDishes);
        return new ArrayList<>(DishesByRestaurant.keySet());
    }
}
