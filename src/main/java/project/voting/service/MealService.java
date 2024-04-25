package project.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import project.voting.model.Meal;
import project.voting.repository.meal.MealRepository;

import java.util.List;

import static project.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    @Autowired
    MealRepository mealRepository;

    public Meal create(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        return mealRepository.save(meal, restaurantId);
    }

    public void update(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        checkNotFoundWithId(mealRepository.save(meal, restaurantId), meal.id());
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(mealRepository.delete(id, restaurantId), id);
    }

    public Meal get(int id, int restaurantId) {
        return checkNotFoundWithId(mealRepository.get(id, restaurantId), id);
    }

    public List<Meal> getAll() {
        return mealRepository.getAll();
    }
}
