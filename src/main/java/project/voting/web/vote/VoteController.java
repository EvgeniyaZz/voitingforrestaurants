package project.voting.web.vote;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    public static final String REST_URL = "/api/restaurants/{restaurantId}/votes";

    VoteService service;
    VoteRepository repository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthorizedUser authorizedUser, @PathVariable int restaurantId, @Valid @RequestBody VoteTo voteTo) {
        log.info("create new vote for user {}", authorizedUser.id());
        Vote created = service.create(voteTo, authorizedUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.id())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public List<Vote> getAllByRestaurant(@PathVariable int restaurantId) {
        return repository.getAllByRestaurant(restaurantId);
    }
}