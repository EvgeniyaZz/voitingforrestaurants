package project.voting.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.Meal;
import project.voting.repository.MealRepository;
import project.voting.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository;

    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
    @Transactional
    public Meal save(Meal meal, int restaurantId) {
        if(meal.isNew()) {
            meal.setRestaurant(restaurantRepository.getExisted(restaurantId));
        }
        return mealRepository.save(meal);
    }

    @Transactional
    public Meal getWithRestaurant(int id, int restaurantId) {
        Meal meal = mealRepository.getBelonged(id, restaurantId);
        meal.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return meal;
    }
}
