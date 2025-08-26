package peppy.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import peppy.exception.PeppyInvalidCommandException;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String description, String from, String to) throws PeppyInvalidCommandException {
        super(description);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
            this.from = LocalDateTime.from(formatter.parse(from));
            this.to = LocalDateTime.from(formatter.parse(to));
            if (this.from.isAfter(this.to)) {
                throw new PeppyInvalidCommandException("start_date is after end_date");
            }
        } catch (DateTimeException e) {
            throw new PeppyInvalidCommandException("Invalid date time format "
                    + "(dd-MM-yyyy HHmm)");
        }
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(),
                this.from.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mma")),
                this.to.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mma")));
    }

    @Override
    public String toDataString() {
        return String.format("E|%s|%s|%s",
                super.toDataString(),
                this.from.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm")),
                this.to.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm")));
    }
}
