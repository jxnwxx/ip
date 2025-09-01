package peppy.storage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageTest {
    @TempDir
    private static Path tempDir;
    private Storage storage;

    @BeforeEach
    public void setup() {
        this.storage = new Storage(tempDir.toString() + "/data/peppy.txt");
    }

    @Test
    public void testFileCreation() throws Exception {
        storage.loadData();
        assertTrue(Files.exists(tempDir.resolve("data/peppy.txt")));
    }
}
