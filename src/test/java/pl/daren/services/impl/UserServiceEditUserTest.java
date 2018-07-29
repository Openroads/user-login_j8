package pl.daren.services.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.daren.api.UserService;
import pl.daren.api.model.User;
import pl.daren.dao.UserDAO;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceEditUserTest {

    @Mock
    UserDAO userDAO;
    UserService userService;

    private static final User testUser = User.of("Johny", "jbravo@gmail.com", "AAaa12390##", "555666333");

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    public void testEditUserFailure() {
        assertFalse(userService.editUser(null));


        when(userDAO.changeUser(testUser)).thenReturn(false);
        assertFalse(userService.editUser(testUser));
    }

    @Test
    public void testEditUserSuccess() {
        when(userDAO.changeUser(testUser)).thenReturn(true);

        assertTrue(userService.editUser(testUser));
    }
}
