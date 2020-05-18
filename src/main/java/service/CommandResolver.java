package service;

import command.ExitAction;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.UserInterruptException;
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

        actions.forEach((k, v) -> addWidget(k, v));
    }

    public void start(String prompt)  {
        while (true) {
            String line = reader.readLine(prompt);
            try {
                this.executeWidget(line);
                reader.getTerminal().writer().flush();
            } catch (UserInterruptException e) {
                (new ExitAction()).apply();
            } catch (EndOfFileException e) {
                return;
            }
        }
    }
}
