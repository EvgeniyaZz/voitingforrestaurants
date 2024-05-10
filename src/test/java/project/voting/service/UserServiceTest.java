package project.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import project.voting.model.User;
import project.voting.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static project.voting.UserTestData.*;

class UserServiceTest extends AbstractServiceTest {

    @Autowired
    UserService userService;

    @Test
    void save() {
        User created = userService.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);

        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void update() {
        User updated = getUpdated();
        userService.update(updated);
        USER_MATCHER.assertMatch(userService.get(USER1_ID), getUpdated());
    }

    @Test
    void delete() {
        userService.delete(USER1_ID);
        assertThrows(NotFoundException.class, () -> userService.get(USER1_ID));
    }

    @Test
    void get() {
        User user = userService.get(USER1_ID);
        USER_MATCHER.assertMatch(user, user1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> userService.get(NOT_FOUND));
    }

    @Test
    void getByEmail() {
        User user = userService.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, admin);
    }

    @Test
    void getAll() {
        List<User> all = userService.getAll();
        USER_MATCHER.assertMatch(all, admin, user1, user2);
    }

    @Test
    void enable() {
        userService.enable(USER1_ID, false);
        assertFalse(userService.get(USER1_ID).isEnabled());
        userService.enable(USER1_ID, true);
        assertTrue(userService.get(USER1_ID).isEnabled());
    }

    @Test
    void createDuplicate() {
        User created = userService.create(getNew());
        created.setEmail("admin@gmail.com");
        assertThrows(DataIntegrityViolationException.class, () -> userService.create(created));
    }

    @Test
    void updateDuplicate() {
        User updated = new User(user1);
        updated.setEmail("admin@gmail.com");
        assertThrows(DataIntegrityViolationException.class, () -> userService.update(updated));
    }
}