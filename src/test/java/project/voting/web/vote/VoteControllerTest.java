package project.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.voting.VoteTestData;
import project.voting.model.Vote;
import project.voting.repository.VoteRepository;
import project.voting.to.VoteTo;
import project.voting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.voting.RestaurantTestData.RESTAURANT1_ID;
import static project.voting.UserTestData.USER2_MAIL;
import static project.voting.UserTestData.USER_MAIL;
import static project.voting.VoteTestData.VOTE_MATCHER;
import static project.voting.VoteTestData.votes1;
import static project.voting.util.VoteUtil.createNewFromTo;
import static project.voting.web.json.JsonUtil.writeValue;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/api/restaurants/" + RESTAURANT1_ID + "/votes";

    @Autowired
    VoteRepository repository;

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
        Vote newVote = createNewFromTo(newVoteTo);
        newVote.setId(newId);

        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getExisted(newId), newVote);
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