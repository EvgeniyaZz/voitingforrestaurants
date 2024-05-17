package project.voting;

import project.voting.model.Vote;
import project.voting.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static project.voting.RestaurantTestData.*;
import static project.voting.UserTestData.user1;
import static project.voting.util.VoteUtil.FINAL_TIME;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");

    public static final MatcherFactory.Matcher<Vote> VOTE_WITH_ID_RESTAURANT_USER_MATCHER = MatcherFactory.usingAssertions(Vote.class,
            (a, e) -> assertThat(a).usingRecursiveComparison().comparingOnlyFields("id", "registered", "restaurant.id", "user.id").isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "registered", "restaurant.id", "user.id").isEqualTo(e));

    public static final int VOTE1_ID = 1;
    public static final int VOTE2_ID = 2;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.now());

    static {
        vote1.setRestaurant(restaurant1);
        vote1.setUser(user1);
    }

    public static VoteTo getNew() {
        return new VoteTo(null, RESTAURANT1_ID, null);
    }

    public static VoteTo getUpdated() {
        return new VoteTo(null, RESTAURANT3_ID, null);
    }

    public static LocalTime checkTestTime(boolean isBefore) {
        if (LocalTime.now().isAfter(FINAL_TIME) && isBefore) {
            return FINAL_TIME.minusMinutes(5);
        } else if(LocalTime.now().isBefore(FINAL_TIME) && !isBefore) {
            return FINAL_TIME.plusMinutes(5);
        }
        return LocalTime.now();
    }
}
