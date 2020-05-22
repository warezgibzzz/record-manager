package command;

public class ExitAction implements Action {
    public void apply() {
        System.out.println("Exiting");
        System.exit(0);
    }
}
