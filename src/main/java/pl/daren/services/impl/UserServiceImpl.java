package pl.daren.services.impl;

import lombok.RequiredArgsConstructor;
import pl.daren.api.UserService;
import pl.daren.api.model.LoginDTO;
import pl.daren.api.model.User;
import pl.daren.dao.UserDAO;
import pl.daren.utils.UserUtils;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public User registerUser(User user) {
        if (nonNull(user)) {
            if (isNull(user.getPassword())) {
                user.setPassword(UserUtils.generateRandomPassword());
            }

            if (UserUtils.isValid(user)) {
                if (!userDAO.getUser(user.getName()).isPresent()) {
                    Optional<User> us = userDAO.createUser(user);
                    if(us.isPresent()){
                        System.out.println("User with this name exist..");
                        return null;
                    }
                    return user;
                }else{
                    System.out.println("User with this name exist..");
                }
            }
        }
        return null;
    }

    @Override
    public boolean editUser(User user) {
        if (nonNull(user)) {
            if (UserUtils.isValid(user)) {
                    return userDAO.changeUser(user);
            }
        }
        return false;
    }

    @Override
    public User loginUser(LoginDTO loginDTO) {
        if(nonNull(loginDTO) && nonNull(loginDTO.getUserName()) && nonNull(loginDTO.getPassword())){
            Optional<User> user = userDAO.getUser(loginDTO.getUserName());
            if(user.isPresent()){
                User user1 = user.get();
                if(user1.getPassword().equals(loginDTO.getPassword())){
                    return user1;
                }
            }
        }
        return null;
    }
}
