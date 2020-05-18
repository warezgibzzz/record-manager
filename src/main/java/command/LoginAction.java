package command;

import org.jline.reader.Widget;

public class LoginAction implements Widget {
    public boolean apply() {
        System.out.println("Login");
        System.out.println("");
        return true;
    }
}
