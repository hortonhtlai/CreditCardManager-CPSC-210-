package persistence;

import org.json.JSONObject;

// Represents an object which may be written in JSON representation
public interface Writable {
    // EFFECTS: returns this as JSON Object
    JSONObject toJson();
}
