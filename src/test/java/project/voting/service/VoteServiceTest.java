package project.voting.service;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import project.voting.VoteTestData;
import project.voting.model.Vote;
import project.voting.repository.VoteRepository;
import project.voting.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static project.voting.RestaurantTestData.restaurant3;
import static project.voting.UserTestData.*;
import static project.voting.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    VoteService service;

    @Autowired
    VoteRepository repository;

    private LocalTime testTime;

    @Test
    void create() {
        VoteTo newVoteTo = VoteTestData.getNew();
        Vote created = service.create(newVoteTo, USER2_ID);
        int newId = created.id();
        Vote newVote = new Vote(null, LocalDate.now());
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getBelongedByDateWithId(newId, USER2_ID, LocalDate.now()), newVote);
    }

    @Test
    void updateBefore() {
        testTime = checkTestTime(true);

        VoteTo updatedTo = VoteTestData.getUpdated();

        try (MockedStatic<LocalTime> mocked = mockStatic(LocalTime.class, Mockito.CALLS_REAL_METHODS)) {
            mocked.when(LocalTime::now).thenReturn(testTime);
            service.update(updatedTo, VOTE1_ID, USER1_ID);
        }

        Vote vote = new Vote(null, LocalDate.now());
        vote.setRestaurant(restaurant3);
        vote.setId(VOTE1_ID);
        vote.setUser(user1);

        Vote getUpdated = repository.getBelonged(VOTE1_ID, USER1_ID);

        VOTE_MATCHER.assertMatch(getUpdated, vote);
    }

    @Test
    void updateAfter() {
        testTime = checkTestTime(false);

        VoteTo updatedTo = VoteTestData.getUpdated();

        try (MockedStatic<LocalTime> mocked = mockStatic(LocalTime.class, Mockito.CALLS_REAL_METHODS)) {
            mocked.when(LocalTime::now).thenReturn(testTime);
            assertThrows(ResponseStatusException.class, () -> service.update(updatedTo, VOTE1_ID, USER1_ID));
        }
    }
}