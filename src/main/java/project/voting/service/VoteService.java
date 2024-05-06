package project.voting.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import project.voting.model.Vote;
import project.voting.repository.vote.VoteRepository;

import java.util.List;

import static project.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote create(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        return voteRepository.save(vote, userId, restaurantId);
    }

    public void update(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(voteRepository.save(vote, userId, restaurantId), vote.id());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepository.delete(id, userId), id);
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(voteRepository.get(id, userId), id);
    }

    public Vote getWithRestaurant(int id, int userId) {
        return voteRepository.getWithRestaurant(id, userId);
    }

    public List<Vote> getAllByUser(int userId) {
        return voteRepository.getAllByUser(userId);
    }

    public List<Vote> getAllByRestaurant(int restaurantId) {
        return voteRepository.getAllByRestaurant(restaurantId);
    }
}
