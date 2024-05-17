package project.voting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.Restaurant;
import project.voting.util.exception.NotFoundException;

import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id = ?1")
    Optional<Restaurant> getWithDishes(int id);

    default Restaurant getExistedWithDishes(int id) {
        return getWithDishes(id).orElseThrow(() -> new NotFoundException("Restaurant with id=" + id + " not found"));
    }
}
