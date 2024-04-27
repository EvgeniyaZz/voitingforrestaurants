package project.voting;

import project.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static project.voting.RestaurantTestData.restaurant1;
import static project.voting.UserTestData.user1;
import static project.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");

    public static final int VOTE_ID = START_SEQ + 17;

    public static final Vote vote1 = new Vote(VOTE_ID, LocalDate.now());
    public static final Vote vote2 = new Vote(VOTE_ID + 1, LocalDate.now());
    public static final Vote vote3 = new Vote(VOTE_ID + 2, LocalDate.now());

    public static final List<Vote> votes = List.of(vote1, vote2, vote3);

    static {
        vote1.setUser(user1);
        vote1.setRestaurant(restaurant1);
    }

    public static Vote getNew() {
        return new Vote(null, LocalDate.now());
    }
}
