package ru.gitolite.recordmanager.service;

import ru.gitolite.recordmanager.commands.*;
import ru.gitolite.recordmanager.security.PBKDF2HashUtil;

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
            initialState.put("user", null);
            initialState.put("action", null);
            initialState.put("entity", null);
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
        return compileActionsMap(actions, commonActions());
    }

    public static Map<String, Action> viewContextActions() {
        Map<String, Action> actions = new HashMap<>();

        return compileActionsMap(actions, commonActions());
    }
}
