package command;

import org.jline.reader.Widget;

public class ExitAction implements Widget {
    public boolean apply() {
        System.out.println("Exiting");
        System.exit(0);
        return true;
    }
}
