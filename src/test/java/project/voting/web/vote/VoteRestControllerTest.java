package project.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.voting.model.Vote;
import project.voting.service.VoteService;
import project.voting.to.VoteTo;
import project.voting.util.VoteUtil;
import project.voting.util.exception.NotFoundException;
import project.voting.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.voting.RestaurantTestData.RESTAURANT1_ID;
import static project.voting.UserTestData.USER1_ID;
import static project.voting.VoteTestData.*;
import static project.voting.repository.vote.DataJpaVoteRepository.FINAL_TIME;
import static project.voting.web.json.JsonUtil.writeValue;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/restaurants/" + RESTAURANT1_ID + "/votes/";

    @Autowired
    VoteService voteService;

    @Test
    void createWithLocation() throws Exception {
        VoteTo newVoteTo = new VoteTo(null, LocalDate.now().plus(1, ChronoUnit.DAYS));
        Vote newVote = new Vote(null, newVoteTo.getRegistered());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newVote)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);

        int newId = created.id();
        newVote.setId(newId);

        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteService.get(newId, USER1_ID), newVote);
    }

    @Test
    void update() throws Exception {
        if (LocalTime.now().isBefore(FINAL_TIME)) {
            VoteTo updatedTo = new VoteTo(null, LocalDate.now());
            perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(writeValue(updatedTo)))
                    .andDo(print())
                    .andExpect(status().isNoContent());

            VOTE_WITH_RESTAURANT_MATCHER.assertMatch(voteService.get(VOTE_ID, USER1_ID), VoteUtil.updateFromTo(new Vote(vote1), updatedTo));
        }

        //TODO make test for update vote after 11:00
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_ID, USER1_ID));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote1));
    }

    @Test
    void getAllByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(votes1));
    }
}