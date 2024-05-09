package project.voting.web.vote;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import project.voting.model.Vote;

import java.util.List;

@RestController
@RequestMapping(value = VoteProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteProfileRestController extends AbstractVoteController {

    public static final String REST_URL = "/profile/votes";

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@RequestParam int restaurantId, @PathVariable int id) {
        super.delete(restaurantId, id);
    }

    @GetMapping("/{id}")
    @Override
    public Vote get(@RequestParam int restaurantId, @PathVariable int id) {
        return super.get(restaurantId, id);
    }

    @GetMapping
    @Override
    public List<Vote> getAllByUser() {
        return super.getAllByUser();
    }
}