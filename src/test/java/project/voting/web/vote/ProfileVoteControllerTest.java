package project.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.voting.VoteTestData;
import project.voting.model.Vote;
import project.voting.repository.VoteRepository;
import project.voting.service.VoteService;
import project.voting.to.VoteTo;
import project.voting.web.AbstractControllerTest;
import project.voting.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.voting.RestaurantTestData.RESTAURANT1_ID;
import static project.voting.RestaurantTestData.restaurant3;
import static project.voting.UserTestData.*;
import static project.voting.VoteTestData.*;
import static project.voting.util.VoteUtil.FINAL_TIME;
import static project.voting.web.json.JsonUtil.writeValue;
import static project.voting.web.vote.ProfileVoteController.REST_URL;

class ProfileVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + "/";

    @Autowired
    VoteService service;

    @Autowired
    VoteRepository repository;

    private LocalTime testTime;

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void createWithLocation() throws Exception {
        VoteTo newVoteTo = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);

        int newId = created.id();
        Vote newVote = new Vote(null, LocalDate.now());
        newVote.setId(newId);

        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getExisted(newId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateBefore() throws Exception {
        VoteTo updatedTo = VoteTestData.getUpdated();

        if (LocalTime.now().isAfter(FINAL_TIME)) {
            testTime = FINAL_TIME.minusMinutes(5);
            try (MockedStatic<LocalTime> mocked = mockStatic(LocalTime.class, Mockito.CALLS_REAL_METHODS)) {
                mocked.when(LocalTime::now).thenReturn(testTime);
                doUpdate(updatedTo).andExpect(status().isNoContent());
            }
        } else {
            doUpdate(updatedTo).andExpect(status().isNoContent());
        }

        Vote vote = new Vote(null, LocalDate.now());
        vote.setRestaurant(restaurant3);
        vote.setId(VOTE1_ID);
        vote.setUser(user1);

        VOTE_WITH_ID_RESTAURANT_USER_MATCHER.assertMatch(repository.getBelonged(VOTE1_ID, USER1_ID), vote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfter() throws Exception {
        VoteTo updatedTo = VoteTestData.getUpdated();

        if (LocalTime.now().isBefore(FINAL_TIME)) {
            testTime = FINAL_TIME.plusMinutes(5);
            try (MockedStatic<LocalTime> mocked = mockStatic(LocalTime.class, Mockito.CALLS_REAL_METHODS)) {
                mocked.when(LocalTime::now).thenReturn(testTime);
                doUpdate(updatedTo).andExpect(status().isUnprocessableEntity());
            }
        } else {
            doUpdate(updatedTo).andExpect(status().isUnprocessableEntity());
        }
    }

    private ResultActions doUpdate(VoteTo updatedTo) throws Exception {
        return perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print());
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