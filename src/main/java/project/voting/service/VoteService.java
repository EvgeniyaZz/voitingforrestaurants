package project.voting.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.voting.model.Restaurant;
import project.voting.model.User;
import project.voting.model.Vote;
import project.voting.repository.VoteRepository;
import project.voting.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;

import static project.voting.util.VoteUtil.FINAL_TIME;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final EntityManager em;

    @Transactional
    public Vote create(VoteTo voteTo, int userId) {
        if (voteRepository.getByDate(userId, LocalDate.now()) != null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "vote already exist");
        }
        Vote newVote = new Vote(null, LocalDate.now());
        newVote.setRestaurant(em.find(Restaurant.class, voteTo.getRestaurantId()));
        newVote.setUser(em.find(User.class, userId));
        return voteRepository.saveValid(newVote);
    }

    @Transactional
    public void update(VoteTo voteTo, int id, int userId) {
        if (LocalTime.now().isAfter(FINAL_TIME)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "update vote can before " + FINAL_TIME);
        }
        Vote vote = voteRepository.getBelongedByDateWithId(id, userId, LocalDate.now());
        vote.setRestaurant(em.find(Restaurant.class, voteTo.getRestaurantId()));
        voteRepository.saveValid(vote);
    }
}
