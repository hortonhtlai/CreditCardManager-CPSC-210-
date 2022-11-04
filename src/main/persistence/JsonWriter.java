package persistence;

import model.Wallet;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of wallet to file
// Code based on Workroom application presented in CPSC 210
// Workroom: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: creates a new JsonWriter object which writes to given destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer or throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of wallet to destination file
    public void write(Wallet wallet) {
        JSONObject walletJsonObject = wallet.toJson();
        saveToFile(walletJsonObject.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes walletJsonText to destination file
    private void saveToFile(String walletJsonText) {
        writer.print(walletJsonText);
    }
}
