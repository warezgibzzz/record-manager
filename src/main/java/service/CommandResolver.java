package service;

import command.Action;
import command.ExitAction;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.UserInterruptException;

import java.util.Map;

public class CommandResolver {
    private final LineReader reader;
    private final Map<String, Action> actions;

    @SuppressWarnings("unchecked")
    public CommandResolver(LineReader reader, Map<String, Object> state) {
        this.reader = reader;
        this.actions = (Map<String, Action>) state.get("actions");
    }

    public void start(String prompt) {
        String line;
        do {
            try {
                line = reader.readLine(prompt);
                if (this.actions.containsKey(line)) {
                    this.actions.get(line).apply();
                } else {
                    System.out.println("No such command, use \033[0;1mhelp\033[0;0m command to get a list of available commands.");
                }
            } catch (UserInterruptException e) {
                (new ExitAction()).apply();

            } catch (EndOfFileException e) {
                return;
            }
        }
        while (true);
    }
}
