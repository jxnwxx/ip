public class Event extends Task {
    private final String from;
    private final String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(), this.from, this.to);
    }

    @Override
    public String toDataString() {
        return String.format("E|%s|%s|%s", super.toDataString(), this.from, this.to);
    }
}
