package peppy.task;

import peppy.exception.PeppyInvalidCommandException;

public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) throws PeppyInvalidCommandException {
        if (description.isBlank()) {
            throw new PeppyInvalidCommandException("description is empty");
        }
        this.description = description;
        this.isDone = false;
    }

    public boolean markDone() {
        if (this.isDone) {
            return false;
        }
        this.isDone = true;
        return true;
    }

    public boolean markUndone() {
        if (!this.isDone) {
            return false;
        }
        this.isDone = false;
        return true;
    }

    public boolean contains(String input) {
        return description.toLowerCase().contains(input.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("[%c] %s", this.isDone ? 'X' : ' ', this.description);
    }

    public String toDataString() {
        return String.format("%d|%s", this.isDone ? 1 : 0, this.description);
    }
}
