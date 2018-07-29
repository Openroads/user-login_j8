package pl.daren.services.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.daren.api.UserService;
import pl.daren.api.model.LoginDTO;
import pl.daren.api.model.User;
import pl.daren.dao.UserDAO;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceLoginUserTest {
    @Mock
    UserDAO userDAO;
    UserService userService;

    private static final User testUser = User.of("Johny", "jbravo@gmail.com", "AAaa12390##", "555666333");
    private static final LoginDTO testLoginDTO = new LoginDTO();

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    public void testLoginUserFailure() {
        assertNull(userService.loginUser(null));

        assertNull(userService.loginUser(testLoginDTO));
    }

    @Test
    public void testLoginUserFailureIncorrectUserName() {
        testLoginDTO.setUserName("Joe");
        when(userDAO.getUser(testLoginDTO.getUserName())).thenReturn(Optional.empty());

        assertNull(userService.loginUser(testLoginDTO));
    }

    @Test
    public void testLoginUserFailureCorrectUserNameIncorrectPassword() {
        testLoginDTO.setUserName("Johny");
        testLoginDTO.setPassword("AAxxxxxxx");
        when(userDAO.getUser(testUser.getName())).thenReturn(Optional.of(testUser));

        assertNull(userService.loginUser(testLoginDTO));
    }

    @Test
    public void testLoginUserSuccess() {
        testLoginDTO.setUserName("Johny");
        testLoginDTO.setPassword("AAaa12390##");
        when(userDAO.getUser(testUser.getName())).thenReturn(Optional.of(testUser));

        assertEquals(userService.loginUser(testLoginDTO), testUser);
    }

}
