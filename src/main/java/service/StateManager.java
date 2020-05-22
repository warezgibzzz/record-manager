package service;

import command.*;
import security.PBKDF2HashUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StateManager {
    private static Map<String, Object> state;

    private StateManager() {}

    public static Map<String, Object> getState() {
        if (state == null) {
            Map<String, Object> initialState = new HashMap<>();
            initialState.put("isLoggedIn", false);
            initialState.put("currentScreen", "login");
            initialState.put("actions", publicActions());
            initialState.put("auth", new UserAuthenticator(new PBKDF2HashUtil()));
            initialState.put("prompt", "record-manager $ ");
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
        actions.put("debug", new DebugAction());

        return actions;
    }

    private static Map<String, Action> publicActions() {
        Map<String, Action> actions = new HashMap<>();
        actions.put("login", new LoginAction());
        actions.put("sign-up", new SignUpAction());

        return compileActionsMap(actions, commonActions());
    }

    private static Map<String, Action> listContextActions() {
        Map<String, Action> actions = new HashMap<>();

        return compileActionsMap(actions, commonActions());
    }

    private static Map<String, Action> viewContextActions() {
        Map<String, Action> actions = new HashMap<>();

        return compileActionsMap(actions, commonActions());
    }
}
