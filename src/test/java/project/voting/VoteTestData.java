package project.voting;

import project.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static project.voting.RestaurantTestData.*;
import static project.voting.UserTestData.user1;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");

    public static final MatcherFactory.Matcher<Vote> VOTE_WITH_RESTAURANT_MATCHER = MatcherFactory.usingAssertions(Vote .class,
            (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("restaurant.votes", "restaurant.menu", "user").isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields("restaurant.votes", "restaurant.menu", "user").isEqualTo(e));

    public static final int VOTE1_ID = 1;
    public static final int VOTE2_ID = 2;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.now());

    public static final List<Vote> votes1 = List.of(vote1);

    static {
        vote1.setRestaurant(restaurant1);
        vote1.setUser(user1);
    }

    public static Vote getNew() {
        return new Vote(null, LocalDate.now());
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(vote1);
        updated.setRestaurant(restaurant3);
        return updated;
    }
}
