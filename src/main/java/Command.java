public class Command {
    private final Action action;
    private final String[] args;

    public Command(Action action, String... args) {
        this.action = action;
        this.args = args;
    }

    public Action getAction() {
        return action;
    }

    public String[] getArgs() {
        return args;
    }
}
