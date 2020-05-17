package service;

import command.*;
import java.util.HashMap;
import java.util.Map;

public class StateFabric {
    private static Map<String, Object> state;

    private StateFabric() {}

    public static Map<String, Object> getState() {
        if (state == null) {
            Map<String, Object> initialState = new HashMap<>();
            initialState.put("isLoggedIn", false);
            initialState.put("currentScreen", "login");
            initialState.put("actions", publicActions());
            state = initialState;
        }
        return state;
    }

    private static Map<String, Object> publicActions() {
        Map<String, Object> actions = new HashMap<>();
        actions.put("login", new LoginAction());
        actions.put("sign-up", new LoginAction());
        actions.put("exit", new ExitAction());

        return actions;
    }
}
