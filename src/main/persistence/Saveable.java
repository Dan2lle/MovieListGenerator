package persistence;

import java.io.PrintWriter;

// used the TellerApp as a source of code
// Represents data that can be saved to file
public interface Saveable {
    // MODIFIES: printWriter
    // EFFECTS: writes the saveable to printWriter
    void save(PrintWriter printWriter);
}
