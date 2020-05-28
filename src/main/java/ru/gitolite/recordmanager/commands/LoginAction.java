package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.exception.InvalidPasswordException;
import ru.gitolite.recordmanager.exception.NoSuchUserException;
import ru.gitolite.recordmanager.model.User;
import ru.gitolite.recordmanager.service.StateManager;
import ru.gitolite.recordmanager.service.UserAuthenticator;
import ru.gitolite.recordmanager.service.UserService;

import java.io.Console;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Optional;

public class LoginAction implements Action {
    public void apply() throws InvalidArgumentException {
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
                Optional<User> user = (new UserService()).findUserByName(username);

                if (!user.isPresent()) {
                    throw new NoSuchUserException();
                }

                Map<String, Action> actionMap = StateManager.protectedActions();
                state.replace("user", user.get());
                state.replace("prompt", "record-manager@" + user.get().getName() + " # ");
                state.replace("actions", actionMap);
                state.replace("action", actionMap.get("menu"));

                System.out.println("You are logged in!");
                actionMap.get("menu").apply();
            }
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchUserException | InvalidPasswordException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }

    @Override
    public String toString() {
        return "login";
    }

    @Override
    public String getDescription() {
        return "Initiate an app login";
    }
}
