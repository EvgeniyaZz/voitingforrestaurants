package project.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.model.Vote;
import project.voting.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static project.voting.RestaurantTestData.RESTAURANT1_ID;
import static project.voting.UserTestData.USER1_ID;
import static project.voting.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    VoteService voteService;

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
        voteService.delete(VOTE_ID, USER1_ID, RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_ID, USER1_ID, RESTAURANT1_ID));
    }

    @Test
    void get() {
        Vote vote = voteService.get(VOTE_ID, USER1_ID, RESTAURANT1_ID);
        VOTE_MATCHER.assertMatch(vote, vote1);
    }

    @Test
    void getAll() {
    }
}