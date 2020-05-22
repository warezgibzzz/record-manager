package command;

import model.User;
import org.jline.reader.UserInterruptException;
import security.HashUtilInterface;
import security.PBKDF2HashUtil;
import service.UserService;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class SignUpAction implements Action {
    private char[] password;
    private final UserService userService;
    private String username;

    private boolean checkPassword(Console console) {
        System.out.print("Enter your password: ");
        char[] password = console.readPassword();
        System.out.print("Confirm your password: ");
        char[] confirmPassword = console.readPassword();

        if (Arrays.equals(password, confirmPassword)) {
            this.setPassword(password);
            return true;
        }

        return false;
    }

    private boolean checkUser(Console console) {
        System.out.print("Enter your username: ");
        String username = console.readLine();

        if (!userService.findUserByName(username).isPresent()) {
            this.setUsername(username);
            return true;
        }
        return false;
    }

    public SignUpAction() {
        userService = new UserService();
    }

    public void apply() {
        System.out.println("SignUp");

        Console console = System.console();

        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }


        while (!this.checkUser(console)) {
            System.out.println("This name is already taken!");
        }

        while (!this.checkPassword(console)) {
            System.out.println("Provided passwords are not identical!");
        }

        PBKDF2HashUtil hashUtil = new PBKDF2HashUtil();

        String salt = Base64.getEncoder().encodeToString(hashUtil.generateSalt());

        User user = new User();
        user.setName(this.getUsername());
        user.setSalt(salt);
        user.setPassword(Base64.getEncoder().encodeToString(hashUtil.hashPassword(salt, new String(this.getPassword()))));

        userService.saveUser(user);
        System.out.println("User " + username + " created! Type \"login\" to get started!");
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
