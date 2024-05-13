package project.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.voting.VoteTestData;
import project.voting.model.Vote;
import project.voting.repository.VoteRepository;
import project.voting.service.VoteService;
import project.voting.to.VoteTo;
import project.voting.util.exception.NotFoundException;
import project.voting.web.AbstractControllerTest;
import project.voting.web.json.JsonUtil;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.voting.RestaurantTestData.RESTAURANT1_ID;
import static project.voting.RestaurantTestData.restaurant3;
import static project.voting.UserTestData.USER_MAIL;
import static project.voting.VoteTestData.*;
import static project.voting.util.VoteUtil.FINAL_TIME;
import static project.voting.util.VoteUtil.createNewFromTo;
import static project.voting.web.vote.ProfileVoteController.REST_URL;

class ProfileVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + "/";

    @Autowired
    VoteService service;

    @Autowired
    VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateBefore() throws Exception {
        if (LocalTime.now().isBefore(FINAL_TIME)) {
            VoteTo updatedTo = VoteTestData.getUpdated();
            Vote vote = createNewFromTo(updatedTo);
            vote.setRestaurant(restaurant3);
            vote.setId(updatedTo.id());
            perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID).contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updatedTo)))
                    .andDo(print())
                    .andExpect(status().isNoContent());

            VOTE_WITH_RESTAURANT_MATCHER.assertMatch(voteRepository.getWithRestaurant(VOTE1_ID).orElseThrow(), vote);
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE1_ID)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteRepository.getExisted(VOTE1_ID));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE2_ID)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE1_ID)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE2_ID)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE1_ID)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(vote1)));
    }
}