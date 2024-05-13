package project.voting.web.vote;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.voting.config.AuthorizedUser;
import project.voting.model.Vote;
import project.voting.repository.VoteRepository;
import project.voting.service.VoteService;
import project.voting.to.VoteTo;

import java.util.List;

import static project.voting.util.VoteUtil.asTo;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {

    public static final String REST_URL = "/api/profile/votes";

    VoteService service;
    VoteRepository repository;

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthorizedUser authorizedUser, @PathVariable int id, @Valid @RequestBody VoteTo voteTo) {
        log.info("update vote {} for user {}", id, authorizedUser.id());
        service.update(voteTo, id, authorizedUser.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authorizedUser, @PathVariable int id) {
        log.info("delete vote {} for user {}", id, authorizedUser.id());
        Vote vote = repository.getBelonged(id, authorizedUser.id());
        repository.delete(vote);
    }

    @GetMapping("/{id}")
    public VoteTo get(@AuthenticationPrincipal AuthorizedUser authorizedUser, @PathVariable int id) {
        log.info("get vote for user {}", authorizedUser.id());
        Vote vote = repository.getBelonged(id, authorizedUser.id());
        return asTo(vote);
    }

    @GetMapping
    public List<Vote> getAllByUser(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("get all vote for user {}", authorizedUser.id());
        return repository.getAllByUser(authorizedUser.id());
    }
}