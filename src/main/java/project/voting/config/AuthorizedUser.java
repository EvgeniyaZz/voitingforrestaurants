package project.voting.config;

import lombok.Getter;
import org.springframework.lang.NonNull;
import project.voting.model.User;

@Getter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthorizedUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }

    @Override
    public String toString() {
        return "AuthorizedUser:" + id() + '[' + user.getEmail() + ']';
    }
}