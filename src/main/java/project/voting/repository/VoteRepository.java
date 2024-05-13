package project.voting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.Vote;
import project.voting.repository.BaseRepository;
import project.voting.util.exception.DataConflictException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.id= :id AND v.user.id= :userId")
    Optional<Vote> get(int id, int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id= :userId AND v.registered= :registered")
    Vote getByDate(int userId, LocalDate registered);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.id= ?1")
    Optional<Vote> getWithRestaurant(int id);

    @Query("SELECT v FROM Vote v WHERE v.user.id= :userId ORDER BY v.registered DESC")
    List<Vote> getAllByUser(int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id= :restaurantId ORDER BY v.registered DESC")
    List<Vote> getAllByRestaurant(int restaurantId);

    default Vote getBelonged(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + " is not exist or doesn't belong to User id=" + userId));
    }
}
