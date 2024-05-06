package project.voting;

import project.voting.model.Vote;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static project.voting.RestaurantTestData.*;
import static project.voting.UserTestData.user1;
import static project.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");

    public static final MatcherFactory.Matcher<Vote> VOTE_WITH_RESTAURANT_AND_USER_MATCHER = MatcherFactory.usingAssertions(Vote .class,
            (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "restaurant.vote", "user.vote").isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "restaurant.vote", "user.vote").isEqualTo(e));

    public static final int VOTE_ID = START_SEQ + 17;

    public static final Vote vote1 = new Vote(VOTE_ID, LocalDate.now());
    public static final Vote vote2 = new Vote(VOTE_ID + 1, LocalDate.now());
    public static final Vote vote3 = new Vote(VOTE_ID + 2, LocalDate.now());

    public static final List<Vote> votes1 = List.of(vote1, vote3);

    static {
        vote1.setRestaurant(restaurant1);
        vote1.setUser(user1);
    }

    public static Vote getNew() {
        return new Vote(null, LocalDate.now().plus(1, ChronoUnit.DAYS));
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(vote1);
        updated.setRestaurant(restaurant3);
        return updated;
    }
}
