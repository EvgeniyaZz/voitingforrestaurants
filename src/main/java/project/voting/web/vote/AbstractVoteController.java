package project.voting.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.model.Vote;
import project.voting.service.VoteService;
import project.voting.to.VoteTo;
import project.voting.util.VoteUtil;
import project.voting.web.SecurityUtil;

import java.util.List;

import static project.voting.util.ValidationUtil.assureIdConsistent;
import static project.voting.util.ValidationUtil.checkNew;

public abstract class AbstractVoteController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    VoteService voteService;

    public Vote create(VoteTo voteTo, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("create {} for user {}", voteTo, userId);
        checkNew(voteTo);
        return voteService.create(VoteUtil.createNewFromTo(voteTo), userId, restaurantId);
    }

    public void update(VoteTo voteTo, int restaurantId, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update vote {} in restaurant {} for user {}", voteTo, restaurantId, userId);
        assureIdConsistent(voteTo, id);
        Vote vote = voteService.get(id, userId);
        voteService.update(VoteUtil.updateFromTo(vote, voteTo), userId, restaurantId);
    }

    public void delete(int restaurantId, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete vote {} for restaurant {}, user {}", id, restaurantId, userId);
        voteService.delete(id, userId);
    }

    public Vote get(int restaurantId, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get vote {} for restaurant {}, user {}", id, restaurantId, userId);
        return voteService.get(id, userId);
    }

    public Vote getWithRestaurant(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get vote {} with restaurant for user {}", id, userId);
        return voteService.getWithRestaurant(id, userId);
    }

    public List<Vote> getAllByUser() {
        int userId = SecurityUtil.authUserId();
        log.info("get all vote for user {}", userId);
        return voteService.getAllByUser(userId);
    }

    public List<Vote> getAllByRestaurant(int restaurantId) {
        log.info("get all vote for restaurant {}", restaurantId);
        return voteService.getAllByRestaurant(restaurantId);
    }
}
