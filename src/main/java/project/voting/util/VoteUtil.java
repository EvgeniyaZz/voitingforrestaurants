package project.voting.util;

import project.voting.model.Vote;
import project.voting.to.VoteTo;

import java.time.LocalTime;

public class VoteUtil {

    public static final LocalTime FINAL_TIME = LocalTime.of(11, 0);

    public static Vote createNewFromTo(VoteTo voteTo) {
        return new Vote(null, voteTo.getRegistered());
    }

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getRestaurant().id(), vote.getRegistered());
    }
}
