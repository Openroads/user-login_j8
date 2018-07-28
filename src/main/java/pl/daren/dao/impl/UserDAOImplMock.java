package pl.daren.dao.impl;

import pl.daren.api.model.User;
import pl.daren.dao.UserDAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDAOImplMock implements UserDAO {

    private static Map<String, User> users = new HashMap<>();

    static {
        users.put("John", User.of("John", "jdoe@gmail.com", "Jdoe123#", "555666777"));
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public Optional<User> getUser(String userName) {
        return Optional.ofNullable(userName).map(users::get);
    }

    public Optional<User> createUser(User user) {
        return Optional.ofNullable(user)
                .map(u -> users.put(u.getName(), u));
    }

    public boolean changeUser(User user) {
        Optional<User> user1 = Optional.ofNullable(user)
                .filter(u -> users.containsKey(u.getName()));
        if (user1.isPresent()) {
            users.put(user.getName(), user);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUser(String userName) {
        return Optional.ofNullable(userName).map(u -> users.remove(u))
                .isPresent();

    }
}
