package project.voting;

import project.voting.model.Role;
import project.voting.model.User;
import project.voting.web.json.JsonUtil;

import java.util.Collections;

public class UserTestData {

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password", "votes");

    public static final int USER1_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int USER2_ID = 3;
    public static final int NOT_FOUND = 10;

    public static final String USER_MAIL = "user@yandex.ru";
    public static final String USER2_MAIL = "user2@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";


    public static final User user1 = new User(USER1_ID, "User1", "user@yandex.ru", "password", Role.USER);
    public static final User user2 = new User(USER2_ID, "User2", "user2@gmail.com", "user2", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New user", "new@mail.ru", "newpassword", Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(user1);
        updated.setName("Updated user");
        updated.setEmail("updated@gmail.com");
        updated.setPassword("11111");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
