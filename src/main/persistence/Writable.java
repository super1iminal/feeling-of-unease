package persistence;

import org.json.JSONObject;

// NOTE: This is the same interface as used in the JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as a json object
    JSONObject toJson();
}
// TODO: add citation