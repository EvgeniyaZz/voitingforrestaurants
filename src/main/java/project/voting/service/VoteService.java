package project.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import project.voting.model.Vote;
import project.voting.repository.vote.VoteRepository;

import java.util.List;

import static project.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    @Autowired
    VoteRepository voteRepository;

    public Vote create(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        return voteRepository.save(vote, userId, restaurantId);
    }

    public void update(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(voteRepository.save(vote, userId, restaurantId), vote.id());
    }

    public void delete(int id, int userId, int restaurantId) {
        checkNotFoundWithId(voteRepository.delete(id, userId, restaurantId), id);
    }

    public Vote get(int id, int userId, int restaurantId) {
        return checkNotFoundWithId(voteRepository.get(id, userId, restaurantId), id);
    }

    public List<Vote> getAll() {
        return voteRepository.getAll();
    }
}
