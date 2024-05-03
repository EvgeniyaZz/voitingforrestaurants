package project.voting;

import project.voting.model.Vote;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static project.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");

    public static final int VOTE_ID = START_SEQ + 17;

    public static final Vote vote1 = new Vote(VOTE_ID, LocalDate.now());
    public static final Vote vote2 = new Vote(VOTE_ID + 1, LocalDate.now());
    public static final Vote vote3 = new Vote(VOTE_ID + 2, LocalDate.now());

    public static Vote getNew() {
        return new Vote(null, LocalDate.now().plus(1, ChronoUnit.DAYS));
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID, LocalDate.now());
    }
}
