package command;

import org.jline.reader.Widget;

public class SignUpAction implements Widget {
    public boolean apply() {
        System.out.println("SignUp");
        return true;
    }
}
