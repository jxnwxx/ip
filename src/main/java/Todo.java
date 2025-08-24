public class Todo extends Task {
    public Todo(String description) throws PeppyInvalidCommandException {
        super(description);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }

    @Override
    public String toDataString() {
        return String.format("T|%s", super.toDataString());
    }
}
