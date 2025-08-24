import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDateTime by;

    public Deadline(String description, String by) throws PeppyInvalidCommandException {
        super(description);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
            this.by = LocalDateTime.from(formatter.parse(by));
        } catch (DateTimeException e) {
            throw new PeppyInvalidCommandException("PeppyInvalidCommandException: Invalid date time format "
                    + "(dd-MM-yyyy HHmm)");
        }
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)",
                super.toString(),
                this.by.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mma")));
    }

    @Override
    public String toDataString() {
        return String.format("D|%s|%s",
                super.toDataString(),
                this.by.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm")));
    }
}
