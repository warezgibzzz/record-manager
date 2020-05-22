package command;

import exception.InvalidPasswordException;
import exception.NoSuchUserException;
import service.StateManager;
import service.UserAuthenticator;

import java.io.Console;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Map;

public class LoginAction implements Action {
    public void apply() {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        System.out.print("Enter your username: ");
        String username = console.readLine();

        System.out.print("Enter your password: ");
        char[] password = console.readPassword();

        System.out.println("Checking...");

        Map<String, Object> state = StateManager.getState();
        UserAuthenticator authenticator = (UserAuthenticator) state.get("auth");

        try {
            if (authenticator.checkUser(username, new String(password))) {
                state.replace("isLoggedIn", true);
                System.out.println("You are logged in!");
            }
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchUserException | InvalidPasswordException e){
            System.out.println(e.getMessage());
        }
    }
}
