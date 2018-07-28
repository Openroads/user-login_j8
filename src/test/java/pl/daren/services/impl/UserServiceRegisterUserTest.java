package pl.daren.services.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.daren.api.UserService;
import pl.daren.api.model.User;
import pl.daren.dao.UserDAO;
import pl.daren.error.UserAlreadyExist;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceRegisterUserTest {

    @Mock
    UserDAO userDAO;


    UserService userService;

    private static final User testUser = User.of("Johny", "jbrawo@gmail.com", "AAaa12390]]", "555666333");

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    public void testRegisterUserFailureWithNull() throws UserAlreadyExist {
        assertNull(userService.registerUser(null));
    }

    @Test(expected = UserAlreadyExist.class)
    public void testRegisterUserFailureWithException() throws UserAlreadyExist {
        when(userDAO.getUser(testUser.getName())).thenReturn(Optional.of(testUser));

        userService.registerUser(testUser);
    }

    @Test
    public void testRegisterUserSuccess() throws UserAlreadyExist {

        when(userDAO.getUser(testUser.getName())).thenReturn(Optional.empty());

        User registeredUser = userService.registerUser(testUser);

        assertEquals(testUser, registeredUser);
    }

    @Test
    public void testRegisterUserAndGeneratePassword() throws UserAlreadyExist {

        when(userDAO.getUser(testUser.getName())).thenReturn(Optional.empty());

        testUser.setPassword(null);

        User registeredUser = userService.registerUser(testUser);



        assertNotNull(registeredUser.getPassword());
        System.out.println("Generated password: " + registeredUser.getPassword());
        assertTrue(registeredUser.getPassword().length() >= 8);
    }
}
