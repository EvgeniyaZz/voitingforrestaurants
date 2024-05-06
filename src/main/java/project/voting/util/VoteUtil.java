package project.voting.util;

import project.voting.model.Vote;
import project.voting.to.VoteTo;

public class VoteUtil {

    public static Vote createNewFromTo(VoteTo voteTo) {
        return new Vote(null, voteTo.getRegistered());
    }

    public static Vote updateFromTo(Vote vote, VoteTo voteTo) {
        vote.setRegistered(voteTo.getRegistered());
        return vote;
    }
}
