package project.voting.repository;

import jakarta.validation.ConstraintViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project.voting.model.Vote;
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

    @Query("SELECT v FROM Vote v WHERE v.id= :id AND v.user.id= :userId AND v.registered= :registered")
    Optional<Vote> getByDateWithId(int id, int userId, LocalDate registered);

    @Query("SELECT v FROM Vote v WHERE v.user.id= :userId ORDER BY v.registered DESC")
    List<Vote> getAllByUser(int userId);

    default Vote getBelonged(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + " is not exist or doesn't belong to User id=" + userId));
    }

    default Vote getBelongedByDateWithId(int id, int userId, LocalDate date) {
        return getByDateWithId(id, userId, date).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + " is not exist or doesn't belong to User id=" + userId + " on date=" + date));
    }

    @Transactional
    default Vote saveValid(Vote vote) {
        try {
            return save(vote);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "vote not valid");
        }
    }
}
