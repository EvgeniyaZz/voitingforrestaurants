package project.voting.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.model.Vote;
import project.voting.service.VoteService;
import project.voting.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

public abstract class AbstractVoteController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    VoteService voteService;

    public Vote create(int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("create new vote for user {}", userId);
        return voteService.create(new Vote(null, LocalDate.now()), userId, restaurantId);
    }

    public void update(int restaurantId, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update vote {} in restaurant {} for user {}", id, restaurantId, userId);
        Vote vote = voteService.get(id, userId);
        voteService.update(vote, userId, restaurantId);
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
