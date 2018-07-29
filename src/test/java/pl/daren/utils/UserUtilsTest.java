package pl.daren.utils;

import org.junit.Before;
import org.junit.Test;
import pl.daren.api.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserUtilsTest {

    private static User correctUser;
    @Before
    public void setUp(){
        correctUser = User.of("Johny", "jbravo@gmail.com", "AAaa12390#", "555666333");
    }

    @Test
    public void isValidTestSuccess() {
        assertTrue(UserUtils.isValid(correctUser));
    }

    @Test
    public void isValidTestFailureName() {
        correctUser.setName(null);
        assertFalse(UserUtils.isValid(correctUser));
        correctUser.setName("");
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setName("D");
        assertFalse(UserUtils.isValid(correctUser));
    }

    @Test
    public void isValidTestFailureEmail() {
        correctUser.setEmail(null);
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setEmail("");
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setEmail("em.pl");
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setEmail("em@");
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setEmail("em@.pl");
        assertFalse(UserUtils.isValid(correctUser));
    }

    @Test
    public void isValidTestFailurePassword() {
        correctUser.setPassword(null);
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setPassword("");
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setPassword("aa12");
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setPassword("12345678");
        assertFalse(UserUtils.isValid(correctUser));
    }

    @Test
    public void isValidTestFailurePhone() {
        correctUser.setPhone(null);
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setPhone("");
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setPhone("12345678");
        assertFalse(UserUtils.isValid(correctUser));

        correctUser.setPhone("+12345678");
        assertFalse(UserUtils.isValid(correctUser));

    }
}