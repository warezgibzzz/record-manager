package service;

import command.ExitAction;
import org.jline.reader.LineReader;
import org.jline.reader.Widget;

import java.util.Map;

public class CommandResolver extends org.jline.builtins.Widgets {
    private LineReader reader;
    private Map<String, Object> state;

    public CommandResolver(LineReader reader, Map<String, Object> state) {
        super(reader);
        this.reader = reader;
        this.state = state;

        Map<String, Widget> actions = (Map<String, Widget>) state.get("actions");

        for (Map.Entry<String, Widget> action : actions.entrySet()) {
            System.out.println(action.getKey());
            addWidget(action.getKey(), action.getValue());
        }

    }
}
