package project.voting.repository.vote;

import project.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int userId, int restaurantId);

    boolean delete(int id, int userId);

    Vote get(int id, int userId);

    Vote getByDate(int userId, LocalDate registered);

    List<Vote> getAllByUser(int userId);

    List<Vote> getAllByRestaurant(int restaurantId);

    Vote getWithRestaurant(int id, int userId);
}
