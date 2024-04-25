package project.voting.repository.meal;

import project.voting.model.Meal;

import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, int restaurantId);

    boolean delete(int id, int restaurantId);

    Meal get(int id, int restaurantId);

    List<Meal> getAll();
}
