package project.voting.repository.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.Vote;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId AND v.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("userId") int userId, @Param("restaurantId") int restaurantId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.registered=:registered")
    Vote getByDate(@Param("userId") int userId, @Param("registered") LocalDate registered);

    @Query("UPDATE Vote v SET v.restaurant.id=:restaurantId WHERE v.registered=:registered")
    Vote updateByDate(@Param("restaurantId") int restaurantId, @Param("registered") LocalDate registered);
}