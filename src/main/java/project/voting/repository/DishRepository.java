package project.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.Dish;
import project.voting.util.exception.DataConflictException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId")
    List<Dish> getAll(int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.id = :id and d.restaurant.id = :restaurantId")
    Optional<Dish> get(int id, int restaurantId);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.added= :added")
    List<Dish> getWithRestaurantByDate(LocalDate added);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurantId AND d.added= :added")
    List<Dish> getByDate(int restaurantId, LocalDate added);

    default Dish getBelonged(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("Dish id=" + id + " is not exist or doesn't belong to Restaurant id=" + restaurantId));
    }
}
