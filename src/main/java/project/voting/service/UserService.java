package project.voting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import project.voting.model.User;
import project.voting.repository.user.UserRepository;
import project.voting.to.UserTo;
import project.voting.util.UserUtil;
import project.voting.util.exception.NotFoundException;

import java.util.List;

import static project.voting.util.UserUtil.prepareToSave;
import static project.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return prepareAndSave(user);
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        prepareAndSave(user);
    }

    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.id());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    public void delete(int id) {
        checkNotFoundWithId(userRepository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(userRepository.get(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    private User prepareAndSave(User user) {
        return userRepository.save(prepareToSave(user));
    }
}