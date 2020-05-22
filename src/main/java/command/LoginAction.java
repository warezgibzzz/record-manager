package command;

import service.StateManager;
import service.UserAuthenticator;

import java.io.Console;
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

        if (authenticator.checkUser(username, new String(password))) {
            state.replace("isLoggedIn", true);
            System.out.println("You are logged in!");
        }
    }
}
