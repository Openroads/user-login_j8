package pl.daren.api;

import pl.daren.api.model.LoginDTO;
import pl.daren.api.model.User;
import pl.daren.error.UserAlreadyExist;

public interface UserService {
    /**
     *
     * @param user user to create
     * @return  registered user or null if registering failure
     */
    User registerUser(User user) throws UserAlreadyExist;

    boolean editUser(User user);

    User loginUser(LoginDTO loginDTO);
}
