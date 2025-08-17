public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public boolean markDone() {
        if (this.isDone)
            return false;
        this.isDone = true;
        return true;
    }

    public boolean markUndone() {
        if (!this.isDone)
            return false;
        this.isDone = false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%c] %s", this.isDone ? 'X' : ' ',this.description);
    }
}
