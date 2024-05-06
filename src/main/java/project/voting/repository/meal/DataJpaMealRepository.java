package project.voting.repository.meal;

import org.springframework.stereotype.Repository;
import project.voting.model.Meal;
import project.voting.repository.restaurant.CrudRestaurantRepository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudMealRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;


    public DataJpaMealRepository(CrudMealRepository mealRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMealRepository = mealRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    @Override
    public Meal save(Meal meal, int restaurantId) {
        if (!meal.isNew() && get(meal.id(), restaurantId) == null) {
            return null;
        }
        meal.setRestaurant(crudRestaurantRepository.getReferenceById(restaurantId));
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudMealRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public Meal get(int id, int restaurantId) {
        return crudMealRepository.findById(id)
                .filter(meal -> meal.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    public List<Meal> getAll(int restaurantId) {
        return crudMealRepository.getAll(restaurantId);
    }
}
