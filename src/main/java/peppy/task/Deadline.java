package peppy.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import peppy.exception.PeppyInvalidCommandException;

/**
 * Represents a Deadline with a due date.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Constructs a Deadline object with a specified due date.
     *
     * @param description Description of the task.
     * @param by          String form of due date of the task.
     * @throws PeppyInvalidCommandException If description is blank or if by is not in the proper datetime format.
     */
    public Deadline(String description, String by) throws PeppyInvalidCommandException {
        super(description);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
            this.by = LocalDateTime.from(formatter.parse(by));
        } catch (DateTimeException e) {
            throw new PeppyInvalidCommandException("Invalid date time format, use: "
                    + INPUT_DATE_FORMAT);
        }
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)",
                super.toString(),
                this.by.format(DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT)));
    }

    @Override
    public String toDataString() {
        return String.format("D|%s|%s",
                super.toDataString(),
                this.by.format(DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT)));
    }
}
