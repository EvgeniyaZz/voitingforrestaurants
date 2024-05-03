package project.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.VoteTestData;
import project.voting.model.Vote;
import project.voting.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static project.voting.RestaurantTestData.*;
import static project.voting.UserTestData.USER1_ID;
import static project.voting.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    VoteService voteService;

    @Test
    void create() {
        Vote created = voteService.create(VoteTestData.getNew(), USER1_ID, RESTAURANT1_ID);
        int newId = created.id();
        Vote newVote = VoteTestData.getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteService.get(newId, USER1_ID, RESTAURANT1_ID), newVote);
    }

    @Test
    void update() {
        // only for time before 11:00
        Vote updated = VoteTestData.getUpdated();
        voteService.update(updated, USER1_ID, RESTAURANT3_ID);
        Vote getUpdated = voteService.getWithRestaurant(VOTE_ID, USER1_ID, RESTAURANT3_ID);
        VOTE_MATCHER.assertMatch(getUpdated, VoteTestData.getUpdated());
        RESTAURANT_MATCHER.assertMatch(getUpdated.getRestaurant(), restaurant3);
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
    void getWithRestaurant() {
        Vote vote = voteService.getWithRestaurant(VOTE_ID, USER1_ID, RESTAURANT1_ID);
        VOTE_MATCHER.assertMatch(vote, vote1);
        RESTAURANT_MATCHER.assertMatch(vote.getRestaurant(), restaurant1);
    }

    @Test
    void getAllByUser() {
        VOTE_MATCHER.assertMatch(voteService.getAllByUser(USER1_ID), List.of(vote1));
    }

    @Test
    void getAllByRestaurant() {
        VOTE_MATCHER.assertMatch(voteService.getAllByRestaurant(RESTAURANT1_ID), List.of(vote1, vote3));
    }
}