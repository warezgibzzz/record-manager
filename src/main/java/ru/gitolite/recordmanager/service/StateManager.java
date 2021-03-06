package ru.gitolite.recordmanager.service;

import com.github.javafaker.Bool;
import ru.gitolite.recordmanager.commands.*;
import ru.gitolite.recordmanager.model.User;
import ru.gitolite.recordmanager.security.PBKDF2HashUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StateManager {
    private static Map<String, Object> state;

    private StateManager() {
    }

    public static Map<String, Object> getState() {
        if (state == null) {
            Map<String, Object> initialState = new HashMap<>();

            Map<String, Action> actions = publicActions();
            if (!Files.exists(Paths.get("seed.lock"))) {
                actions.put("seed", new SeedAction());
            }

            initialState.put("user", null);
            initialState.put("action", null);
            initialState.put("entity", null);
            initialState.put("actions", actions);
            initialState.put("auth", new UserAuthenticator(new PBKDF2HashUtil()));
            initialState.put("prompt", "record-manager $ ");
            initialState.put("seed", false);

            state = initialState;
        }
        return state;
    }

    private static Map<String, Action> compileActionsMap(Map<String, Action> a, Map<String, Action> b) {
        return Stream.of(a, b)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Entry::getKey,
                        Entry::getValue,
                        (v1, v2) -> (Action) v2)
                );
    }

    private static Map<String, Action> commonActions() {
        Map<String, Action> actions = new HashMap<>();
        actions.put("exit", new ExitAction());
        actions.put("help", new HelpAction());

        return actions;
    }

    public static Map<String, Action> publicActions() {
        Map<String, Action> actions = new HashMap<>();
        actions.put("login", new LoginAction());
        actions.put("sign-up", new SignUpAction());

        return compileActionsMap(actions, commonActions());
    }

    public static Map<String, Action> protectedActions() {
        Map<String, Action> actions = new HashMap<>();
        actions.put("menu", new MenuAction());

        return compileActionsMap(actions, commonActions());
    }

    public static Map<String, Action> listContextActions(Map<String, Action> actions) {
        actions.put("menu", new MenuAction());
        return compileActionsMap(commonActions(), actions);
    }

    public static Map<String, Action> viewContextActions(Map<String, Action> actions) {
        actions.put("menu", new MenuAction());

        return compileActionsMap(actions, commonActions());
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Action> getActions() {
        return (Map<String, Action>) state.get("actions");
    }

    public static void setActions(Map<String, Action> actions) {
        state.replace("actions", actions);
    }

    public static String buildPrompt() {
        String prompt = "record-manager" ;

        if (state.get("user") != null) {
            prompt = prompt + "@" + ((User) state.get("user")).getName() + " # " ;
        } else {
            prompt = prompt + " $ " ;
        }
        return prompt;
    }
}
