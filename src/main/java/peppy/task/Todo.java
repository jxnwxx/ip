package peppy.task;

import peppy.exception.PeppyInvalidCommandException;

/**
 * Represents a Todo task.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo object.
     *
     * @param description Description of the task.
     * @throws PeppyInvalidCommandException If description is blank.
     */
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
