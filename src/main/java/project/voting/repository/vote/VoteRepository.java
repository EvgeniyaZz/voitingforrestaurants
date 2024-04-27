package project.voting.repository.vote;

import project.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int userId, int restaurantId);

    boolean delete(int id, int userId, int restaurantId);

    Vote get(int id, int userId, int restaurantId);

    Vote getByDate(int userId, LocalDate registered);

    List<Vote> getAll();
}
