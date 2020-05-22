package command;

public interface Action {
    public void apply();
    public void apply(String[] args);
}
