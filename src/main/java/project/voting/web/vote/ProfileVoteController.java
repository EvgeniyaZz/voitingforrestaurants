package project.voting.web.vote;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.voting.config.AuthorizedUser;
import project.voting.model.Vote;
import project.voting.repository.VoteRepository;
import project.voting.service.VoteService;
import project.voting.to.VoteTo;

import java.net.URI;
import java.util.List;

import static project.voting.util.VoteUtil.asTo;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {

    public static final String REST_URL = "/api/profile/votes";

    private final VoteService service;
    private final VoteRepository repository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> createWithLocation(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                                     @Valid @RequestBody VoteTo voteTo) {
        log.info("create new vote for user {}", authorizedUser.id());
        Vote created = service.create(voteTo, authorizedUser.id());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(asTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthorizedUser authorizedUser, @PathVariable int id, @Valid @RequestBody VoteTo voteTo) {
        log.info("update vote {} for user {}", id, authorizedUser.id());
        service.update(voteTo, id, authorizedUser.id());
    }

    @GetMapping("/{id}")
    public VoteTo get(@AuthenticationPrincipal AuthorizedUser authorizedUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authorizedUser.id());
        Vote vote = repository.getBelonged(id, authorizedUser.id());
        return asTo(vote);
    }

    @GetMapping
    public List<Vote> getAllByUser(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("get all vote for user {}", authorizedUser.id());
        return repository.getAllByUser(authorizedUser.id());
    }
}