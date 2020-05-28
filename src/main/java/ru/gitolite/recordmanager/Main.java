package ru.gitolite.recordmanager;

import org.hibernate.SessionFactory;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import ru.gitolite.recordmanager.service.CommandResolver;
import ru.gitolite.recordmanager.service.DatabaseSessionFactory;
import ru.gitolite.recordmanager.service.StateManager;

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

        // Init instances
        Map<String, Object> state = StateManager.getState();
        SessionFactory sessionFactory = DatabaseSessionFactory.getSessionFactory();

        // Start resolver
        CommandResolver resolver = new CommandResolver(lineReader);
        resolver.start();
    }
}
