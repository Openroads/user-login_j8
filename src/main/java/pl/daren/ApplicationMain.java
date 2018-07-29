package pl.daren;

import pl.daren.api.UserService;
import pl.daren.api.model.LoginDTO;
import pl.daren.api.model.User;
import pl.daren.dao.UserDAO;
import pl.daren.dao.impl.UserDAOImplMock;
import pl.daren.error.UserAlreadyExist;
import pl.daren.services.impl.UserServiceImpl;
import pl.daren.utils.UserUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ApplicationMain {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAOImplMock();
        UserService userService = new UserServiceImpl(userDAO);

        System.out.println("User registration/login application");

        int inputNum = -1;
        while (inputNum != 0) {
            System.out.println("Type number for action and press enter...");
            System.out.println("1. Registration");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            try {
                inputNum = readAction();
            } catch (Exception ex) {
                System.out.println("Please provide correct input.");
                inputNum = -1;
            }
            try {
                if (inputNum == 1) {
                    User user = new User();
                    System.out.println("User registration....");
                    System.out.println(".....................");
                    System.out.println("Enter your data..");
                    System.out.println("Name(minimum 3 characters): ");
                    user.setName(readNotEmptyLine());

                    System.out.println("Password[if empty - system will generate automatically]" +
                            "(minimum 8 characters, at least one: uppercase, lowercase and special character): ");
                    String password = br.readLine();
                    if (password.trim().length() > 1) {
                        user.setPassword(password);
                    }

                    readEmailFromConsole(user);
                    readPhoneFromConsole(user);
                    User registerUser = null;
                    try {
                        registerUser = userService.registerUser(user);
                        if (isNull(registerUser)) {
                            System.out.println("Unfortunately provided data are incorrect.\n Try again..");
                        } else {
                            System.out.println("Congratulation, successful registration. Now you can log in with " +
                                    "Login: " + registerUser.getName() + " and password: " + registerUser.getPassword());
                        }
                    } catch (UserAlreadyExist userAlreadyExist) {
                        System.out.println("Unfortunately user name is already used.\n Try again..");
                    }
                } else if (inputNum == 2) {
                    LoginDTO loginDTO = new LoginDTO();
                    System.out.println("User login....");
                    System.out.println("Enter login data..");

                    System.out.println("Name: ");
                    loginDTO.setUserName(readNotEmptyLine());

                    System.out.println("Password: ");
                    loginDTO.setPassword(readNotEmptyLine());

                    User user = userService.loginUser(loginDTO);
                    if (nonNull(user)) {
                        System.out.println("Congrats you successfully log in.");
                        inputNum = -1;
                        while (inputNum != 3) {
                            System.out.println("1. Edit account");
                            System.out.println("2. Show my account data");
                            System.out.println("3. Log out");
                            inputNum = readAction();

                            if (inputNum == 1) {
                                System.out.println("1. Change email address");
                                System.out.println("2. Change phone number");
                                System.out.println("3. Change both of them");
                                inputNum = readAction();
                                if (inputNum == 1) {
                                    readEmailFromConsole(user);
                                } else if (inputNum == 2) {
                                    readPhoneFromConsole(user);
                                } else if (inputNum == 3) {
                                    readEmailFromConsole(user);
                                    readPhoneFromConsole(user);
                                    inputNum = -1;
                                }

                                String originEmail = user.getEmail();
                                String originPhone = user.getPhone();

                                if (userService.editUser(user)) {
                                    System.out.println("Successfully edited.");
                                } else {
                                    System.out.println("Editing failure. Try again.");
                                    user.setEmail(originEmail);
                                    user.setPhone(originPhone);
                                }
                            } else if (inputNum == 2) {
                                System.out.println(user);
                            }
                        }
                    } else {
                        System.out.println("Incorrect user name or password.");
                    }

                }
            } catch (IOException ex) {
                System.out.println("Error while parsing input..");
                System.exit(1);
            }
        }
    }

    private static int readAction() throws IOException {
        System.out.print(">:");
        return Integer.parseInt(br.readLine());

    }

    private static String readNotEmptyLine() throws IOException {
        String s = br.readLine();
        while (isNull(s) || s.trim().length() < 1) {
            System.out.println("Input cannot be empty. Enter data again..");
            s = br.readLine();
        }
        return s;
    }

    private static void readEmailFromConsole(User user) throws IOException {
        System.out.println("Email(correct email address): ");
        String email = readNotEmptyLine();
        while (!UserUtils.checkEmail(email)) {
            System.out.println("Please provide correct address:");
            email = readNotEmptyLine();
        }
        user.setEmail(email);
    }


    private static void readPhoneFromConsole(User user) throws IOException {
        System.out.println("Phone(only numeric, minimum 9 numbers): ");
        user.setPhone(readNotEmptyLine());

    }
}
