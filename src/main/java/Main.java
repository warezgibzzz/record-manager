import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import service.CommandResolver;
import service.StateManager;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build();
        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

        Map<String, Object> state = StateManager.getState();
        CommandResolver resolver = new CommandResolver(lineReader, state);
        resolver.start((String) state.get("prompt"));
    }
}
