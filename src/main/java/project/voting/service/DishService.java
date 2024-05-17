package project.voting.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.Dish;
import project.voting.repository.DishRepository;
import project.voting.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @CacheEvict(value = "restaurantsWithDishesByDate", allEntries = true)
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        if (dish.isNew()) {
            dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        }
        return dishRepository.save(dish);
    }
}
