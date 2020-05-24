package ru.gitolite.recordmanager.service;

import ru.gitolite.recordmanager.commands.Action;
import ru.gitolite.recordmanager.commands.ExitAction;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.UserInterruptException;
import ru.gitolite.recordmanager.exception.InvalidArgumentException;

import java.util.Arrays;
import java.util.Map;

public class CommandResolver {
    private final LineReader reader;
    @SuppressWarnings("unchecked")
    public CommandResolver(LineReader reader, Map<String, Object> state) {
        this.reader = reader;
    }

    @SuppressWarnings("unchecked")
    public void start() {
        Object[] line;
        do {
            try {
                Map<String, Action> actionMap = (Map<String, Action>) StateManager.getState().get("actions");
                line = reader.readLine((String) StateManager.getState().get("prompt")).split("\\s");
                String command = (String) line[0];
                if (actionMap.containsKey(command)) {
                    if (line.length > 1) {
                        actionMap.get(command).apply(Arrays.copyOfRange(line, 1, line.length));
                    } else {
                        actionMap.get(command).apply();
                    }
                } else {
                    System.out.println("No such command, use \033[0;1mhelp\033[0;0m command to get a list of available commands.");
                }
            } catch (UserInterruptException e) {
                (new ExitAction()).apply();
            } catch (InvalidArgumentException e){
                System.out.println(e.getMessage());
            } catch (EndOfFileException e) {
                return;
            }
        }
        while (true);
    }
}
