package pl.daren.utils;

import org.apache.commons.lang3.RandomStringUtils;
import pl.daren.api.model.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.length;

public class UserUtils {

    public static char[] charactersUppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static char[] charactersLowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static char[] charactersNumeric = "0123456789".toCharArray();
    private static String specialCharacters = "#?!@$%^&*-";
    public static char[] charactersSpecial = specialCharacters.toCharArray();

    private static String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[" + specialCharacters + "]).{8,}$";

    public static boolean isValid(User user) {
        if (isNull(user)) return false;

        if (isNull(user.getName()) || length(user.getName()) < 3) {
            return false;
        }

        if (!checkPassword(user.getPassword())) return false;

        if (!checkEmail(user.getEmail())) return false;

        if (!checkPhone(user.getPhone())) return false;

        return true;
    }

    private static boolean checkPhone(String phone) {
        return isNumeric(phone) && phone.length() > 8;
    }

    public static boolean checkEmail(String email) {
        if (isNull(email)) return false;
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
        } catch (AddressException e) {
            return false;
        }

        return true;
    }

    private static boolean checkPassword(String password) {
        if (isNull(password)) return false;
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);

       return matcher.matches();
    }

    public static String generateRandomPassword() {
        StringBuilder randomPassword = new StringBuilder("R");
        randomPassword
                .append(RandomStringUtils.random(2, charactersUppercase))
                .append(RandomStringUtils.random(2, charactersLowercase))
                .append(RandomStringUtils.random(2, charactersNumeric))
                .append(RandomStringUtils.random(2, charactersSpecial));
        return randomPassword.toString();
    }
}
