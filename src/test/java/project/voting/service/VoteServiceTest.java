package project.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.voting.VoteTestData;
import project.voting.model.Vote;
import project.voting.repository.VoteRepository;
import project.voting.to.VoteTo;

import java.time.LocalTime;

import static project.voting.RestaurantTestData.restaurant3;
import static project.voting.UserTestData.USER1_ID;
import static project.voting.UserTestData.USER2_ID;
import static project.voting.VoteTestData.*;
import static project.voting.util.VoteUtil.FINAL_TIME;
import static project.voting.util.VoteUtil.createNewFromTo;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    VoteService voteService;

    @Autowired
    VoteRepository voteRepository;

    @Test
    void save() {
        VoteTo newVoteTo = VoteTestData.getNew();
        Vote created = voteService.create(newVoteTo, USER2_ID);
        int newId = created.id();
        Vote newVote = new Vote(null, newVoteTo.getRegistered());
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getExisted(newId), newVote);
    }

    @Test
    void updateBefore() {
        VoteTo updatedTo = VoteTestData.getUpdated();
        Vote vote = createNewFromTo(updatedTo);
        vote.setRestaurant(restaurant3);
        vote.setId(updatedTo.id());
        if (LocalTime.now().isBefore(FINAL_TIME)) {
            voteService.update(updatedTo, updatedTo.id(), USER1_ID);
            Vote getUpdated = voteRepository.getWithRestaurant(VOTE1_ID).orElseThrow();

            VOTE_WITH_RESTAURANT_MATCHER.assertMatch(getUpdated, vote);
        }
    }
}