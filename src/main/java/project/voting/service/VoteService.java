package project.voting.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.voting.model.Vote;
import project.voting.repository.RestaurantRepository;
import project.voting.repository.UserRepository;
import project.voting.repository.VoteRepository;
import project.voting.to.VoteTo;

import java.time.LocalTime;

import static project.voting.util.ValidationUtil.assureIdConsistent;
import static project.voting.util.VoteUtil.FINAL_TIME;
import static project.voting.util.VoteUtil.createNewFromTo;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Vote create(VoteTo voteTo, int userId) {
        Vote newVote = createNewFromTo(voteTo);
        newVote.setRestaurant(restaurantRepository.getExisted(voteTo.getRestaurantId()));
        newVote.setUser(userRepository.getExisted(userId));
        return voteRepository.save(newVote);
    }

    @Transactional
    public void update(VoteTo voteTo, int id, int userId) {
        Vote vote = voteRepository.getByDate(userId, voteTo.getRegistered());
        if (LocalTime.now().isBefore(FINAL_TIME)) {
            assureIdConsistent(vote, id);
            vote.setRestaurant(restaurantRepository.getExisted(voteTo.getRestaurantId()));
            voteRepository.save(vote);
        }
    }
}
