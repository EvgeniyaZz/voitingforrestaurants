package project.voting.repository.user;

import project.voting.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    boolean delete(int id);

    User get(int id);

    Optional<User> findByEmailIgnoreCase(String email);

    List<User> getAll();
}
