package project.voting.repository.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.registered=:registered")
    Vote getByDate(@Param("userId") int userId, @Param("registered") LocalDate registered);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.id= ?1 AND v.user.id= ?2")
    Optional<Vote> getWithRestaurant(int id, int userId);

    @Transactional
    @Modifying
    @Query("UPDATE Vote v SET v.restaurant.id=:restaurantId WHERE v.id=:id AND v.user.id=:userId")
    int update(@Param("restaurantId") int restaurantId, @Param("id") int id, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.registered DESC")
    List<Vote> getAllByUser(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId ORDER BY v.registered DESC")
    List<Vote> getAllByRestaurant(@Param("restaurantId") int restaurantId);
}