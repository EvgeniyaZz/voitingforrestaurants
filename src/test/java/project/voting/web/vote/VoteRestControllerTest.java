package project.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.voting.VoteTestData;
import project.voting.model.Vote;
import project.voting.service.VoteService;
import project.voting.util.exception.NotFoundException;
import project.voting.web.AbstractControllerTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.voting.RestaurantTestData.*;
import static project.voting.UserTestData.*;
import static project.voting.VoteTestData.*;
import static project.voting.repository.vote.DataJpaVoteRepository.FINAL_TIME;
import static project.voting.web.json.JsonUtil.writeValue;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/api/restaurants/" + RESTAURANT1_ID + "/votes";
    private static final String REST_URL_SLASH = REST_URL + "/";

    @Autowired
    VoteService voteService;

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void createWithLocation() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newVote)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);

        int newId = created.id();
        newVote.setId(newId);

        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteService.get(newId, USER2_ID), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateBeforeFinalTime() throws Exception {
        if (LocalTime.now().isBefore(FINAL_TIME)) {
            Vote updated = voteService.getWithRestaurant(VOTE1_ID, USER1_ID);
            updated.setRestaurant(restaurant3);
            perform(MockMvcRequestBuilders.put("/api/restaurants/" + RESTAURANT3_ID + "/votes/" + VOTE1_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(writeValue(updated)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            VOTE_WITH_RESTAURANT_MATCHER.assertMatch(voteService.getWithRestaurant(VOTE1_ID, USER1_ID), updated);
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterFinalTime() throws Exception {
        if (LocalTime.now().isAfter(FINAL_TIME)) {
            Vote updated = voteService.getWithRestaurant(VOTE1_ID, USER1_ID);
            updated.setRestaurant(restaurant3);
            perform(MockMvcRequestBuilders.put("/api/restaurants/" + RESTAURANT3_ID + "/votes/" + VOTE1_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(writeValue(updated)))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE1_ID, USER1_ID));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE2_ID))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE2_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(votes1));
    }
}