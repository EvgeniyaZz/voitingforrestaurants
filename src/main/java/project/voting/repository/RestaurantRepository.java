package project.voting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.Restaurant;
import project.voting.repository.BaseRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r ORDER BY r.added DESC")
    List<Restaurant> getAll();

    @Query("SELECT r FROM Restaurant r WHERE r.added=:added ORDER BY r.name")
    List<Restaurant> getByDate(@Param("added") LocalDate added);

    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.added=:added ORDER BY r.name")
    List<Restaurant> getWithMealsByDate(@Param("added") LocalDate added);

    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id = ?1")
    Optional<Restaurant> getWithMeals(int id);
}
