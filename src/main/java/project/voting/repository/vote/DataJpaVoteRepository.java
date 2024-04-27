package project.voting.repository.vote;

import org.springframework.stereotype.Repository;
import project.voting.model.Vote;
import project.voting.repository.restaurant.CrudRestaurantRepository;
import project.voting.repository.user.CrudUserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private final CrudVoteRepository crudVoteRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository, CrudRestaurantRepository crudRestaurantRepository, CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Transactional
    @Override
    public Vote save(Vote vote, int userId, int restaurantId) {
        if (getByDate(userId, vote.getRegistered()) == null) {
            vote.setRestaurant(crudRestaurantRepository.getReferenceById(restaurantId));
            vote.setUser(crudUserRepository.getReferenceById(userId));
            return crudVoteRepository.save(vote);
        }
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            return crudVoteRepository.updateByDate(restaurantId, vote.getRegistered());
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId, int restaurantId) {
        return crudVoteRepository.delete(id, userId, restaurantId) != 0;
    }

    @Override
    public Vote get(int id, int userId, int restaurantId) {
        return crudVoteRepository.findById(id)
                .filter(vote -> vote.getUser().getId() == userId && vote.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    public Vote getByDate(int userId, LocalDate registered) {
        return crudVoteRepository.getByDate(userId, registered);
    }

    @Override
    public List<Vote> getAll() {
        return crudVoteRepository.findAll();
    }
}
