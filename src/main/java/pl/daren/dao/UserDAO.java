package pl.daren.dao;

import pl.daren.api.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDAO {

    Collection<User> getUsers();

    Optional<User> getUser(String userName);

    /**
     * Create new user and return empty optional or return User object of previous user associated with the same user
     * name.
     * @param user the previous user associated with provided user.userName
     * @return empty optional or  User object of previous user associated with the same user name
     */
    Optional<User> createUser(User user);

    boolean changeUser(User user);

    boolean deleteUser(String userName);
}
