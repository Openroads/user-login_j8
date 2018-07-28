package pl.daren.api;

import pl.daren.api.model.LoginDTO;
import pl.daren.api.model.User;

public interface UserService {

    User registerUser(User user);

    boolean editUser(User user);

    User loginUser(LoginDTO loginDTO);
}
