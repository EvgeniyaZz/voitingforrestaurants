package project.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import project.voting.model.User;
import project.voting.repository.BaseRepository;
import project.voting.util.exception.NotFoundException;

import java.util.Optional;

import static project.voting.config.SecurityConfig.PASSWORD_ENCODER;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    //     https://stackoverflow.com/a/46013654/548473
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.votes WHERE u.id=?1")
    Optional<User> getWithVotes(int id);

    @Transactional
    default User prepareAndSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return save(user);
    }

    default User getExistedByEmail(String email) {
        return findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }
}
