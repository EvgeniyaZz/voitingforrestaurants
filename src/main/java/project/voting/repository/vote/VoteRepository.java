package project.voting.repository.vote;

import org.springframework.stereotype.Repository;
import project.voting.model.Vote;
import project.voting.repository.restaurant.CrudRestaurantRepository;
import project.voting.repository.user.CrudUserRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class VoteRepository {

    public static final LocalTime FINAL_TIME = LocalTime.of(11, 0);

    private final CrudVoteRepository crudVoteRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;
    private final CrudUserRepository crudUserRepository;

    public VoteRepository(CrudVoteRepository crudVoteRepository, CrudRestaurantRepository crudRestaurantRepository, CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Transactional
    public Vote save(Vote vote, int userId, int restaurantId) {
        Vote getByDate = getByDate(userId, vote.getRegistered());
        if (getByDate == null) {
            vote.setRestaurant(crudRestaurantRepository.getReferenceById(restaurantId));
            vote.setUser(crudUserRepository.getReferenceById(userId));
            return crudVoteRepository.save(vote);
        }
        if (LocalTime.now().isBefore(FINAL_TIME)) {
            if (crudVoteRepository.update(restaurantId, getByDate.id(), userId) > 0) {
                return getWithRestaurant(getByDate.id(), userId);
            }
        }
        return null;
    }

    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }

    public Vote get(int id, int userId) {
        return crudVoteRepository.findById(id)
                .filter(vote -> vote.getUser().getId() == userId).orElse(null);
    }

    public Vote getByDate(int userId, LocalDate registered) {
        return crudVoteRepository.getByDate(userId, registered);
    }

    public Vote getWithRestaurant(int id, int userId) {
        return crudVoteRepository.getWithRestaurant(id, userId).orElse(null);
    }

    public List<Vote> getAllByUser(int userId) {
        return crudVoteRepository.getAllByUser(userId);
    }

    public List<Vote> getAllByRestaurant(int restaurantId) {
        return crudVoteRepository.getAllByRestaurant(restaurantId);
    }
}
