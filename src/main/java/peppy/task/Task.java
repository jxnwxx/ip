package peppy.task;

import peppy.exception.PeppyInvalidCommandException;

/**
 * Represents a Task to store.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Constructs a Task object
     *
     * @param description Description of the task.
     * @throws PeppyInvalidCommandException If description is blank.
     */
    public Task(String description) throws PeppyInvalidCommandException {
        if (description.isBlank()) {
            throw new PeppyInvalidCommandException("description is empty");
        }
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     *
     * @return True if it marks successfully, False otherwise.
     */
    public boolean markDone() {
        if (this.isDone) {
            return false;
        }
        this.isDone = true;
        return true;
    }

    /**
     * Marks the task as undone.
     *
     * @return True if it marks successfully, False otherwise.
     */
    public boolean markUndone() {
        if (!this.isDone) {
            return false;
        }
        this.isDone = false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%c] %s", this.isDone ? 'X' : ' ', this.description);
    }

    /**
     * Returns a string representation of the Task in the format of the data file.
     *
     * @return String representation of the Task in the format of the data file.
     */
    public String toDataString() {
        return String.format("%d|%s", this.isDone ? 1 : 0, this.description);
    }
}
