package project.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.Meal;
import project.voting.repository.BaseRepository;
import project.voting.util.exception.DataConflictException;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restaurantId")
    List<Meal> getAll(int restaurantId);

    @Query("SELECT m FROM Meal m WHERE m.id = :id and m.restaurant.id = :restaurantId")
    Optional<Meal> get(int id, int restaurantId);

    default Meal getBelonged(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("Meal id=" + id + " is not exist or doesn't belong to Restaurant id=" + restaurantId));
    }
}
