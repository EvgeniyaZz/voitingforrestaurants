package project.voting.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.voting.config.AuthorizedUser;
import project.voting.model.User;
import project.voting.to.UserTo;

import jakarta.validation.Valid;
import project.voting.util.UserUtil;

import java.net.URI;

import static project.voting.util.ValidationUtil.assureIdConsistent;
import static project.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = ProfileUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileUserController extends AbstractUserController {
    static final String REST_URL = "/api/profile";

    @GetMapping
    public User get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("get {}", authorizedUser);
        return authorizedUser.getUser();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        super.delete(authorizedUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = repository.prepareAndSave(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestBody @Valid UserTo userTo, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("update {} with id={}", userTo, authorizedUser.id());
        assureIdConsistent(userTo, authorizedUser.id());
        User user = authorizedUser.getUser();
        repository.prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    @GetMapping("/with-votes")
    public ResponseEntity<User> getWithVotes(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return super.getWithVotes(authorizedUser.id());
    }
}
