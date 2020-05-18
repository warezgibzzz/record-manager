package service;

import command.ExitAction;
import command.LoginAction;
import command.SignUpAction;
import org.jline.reader.Widget;
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
            state = initialState;
        }
        return state;
    }

    private static Map<String, Widget> compileActionsMap(Map<String, Widget> a, Map<String, Widget> b) {
        return Stream.of(a, b)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Entry::getKey,
                        Entry::getValue,
                        (v1, v2) -> (Widget) v2)
                );
    }

    private static Map<String, Widget> commonActions() {
        Map<String, Widget> actions = new HashMap<>();
        actions.put("exit", new ExitAction());

        return actions;
    }

    private static Map<String, Widget> publicActions() {
        Map<String, Widget> actions = new HashMap<>();
        actions.put("login", new LoginAction());
        actions.put("sign-up", new SignUpAction());
        actions.put("exit", new ExitAction());

        return compileActionsMap(actions, commonActions());
    }

    private static Map<String, Widget> listContextActions() {
        Map<String, Widget> actions = new HashMap<>();
        actions.put("exit", new ExitAction());

        return compileActionsMap(actions, commonActions());
    }

    private static Map<String, Widget> viewContextActions() {
        Map<String, Widget> actions = new HashMap<>();
        actions.put("exit", new ExitAction());

        return compileActionsMap(actions, commonActions());
    }
}
