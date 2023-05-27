package persistence;

import model.Event;
import model.EventLog;
import model.Player;
import model.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // REQUIRES: world must be fully initialized before any reading is done
    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public World read(World world) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorld(world, jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses a world, given a world, and returns it
    private World parseWorld(World world, JSONObject jsonObject) {
        EventLog.getInstance().logEvent(new Event("World reading begins." + " Number of places: "
                + world.getInventoriesList().size() + ". Number of items: " + world.getItemsList().size() + "."));
        parsePlaces(world, jsonObject);

        String playerName = jsonObject.getString("playerName");
        if (!world.worldHasPlayer()) {
            Player player = new Player(playerName);
            world.setPlayer(player);
        } else {
            world.setPlayerName(playerName);
        }

        String playerJsonLocation = jsonObject.getString("playerJsonLocation");
        world.setPlayerLocationFromJsonCode(playerJsonLocation);

        parseItems(world, jsonObject);

        EventLog.getInstance().logEvent(new Event("World reading ends."));

        return world;
    }

    // EFFECTS: parses items.
    private void parseItems(World world, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json: jsonArray) {
            JSONObject jsonItem = (JSONObject) json;
            parseItem(world, jsonItem);
        }
    }

    // EFFECTS: puts item in its correct locations
    private void parseItem(World world, JSONObject jsonItem) {
        int code = jsonItem.getInt("code");
        String jsonLocation = jsonItem.getString("jsonLocation");
        world.moveItem(world.findItemNameFromCode(code), jsonLocation);
    }

    // EFFECTS: parses places
    private void parsePlaces(World world, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("locations");
        for (Object json: jsonArray) {
            JSONObject jsonPlace = (JSONObject) json;
            parsePlace(world, jsonPlace);
        }
    }

    // EFFECTS: sets locked and beenVisited state for place
    private void parsePlace(World world, JSONObject jsonPlace) {
        String jsonCode = jsonPlace.getString("jsonCode");
        world.setPlaceLockedFromPlaceCode(jsonCode, jsonPlace.getBoolean("locked"));
        world.setPlaceBeenVisitedFromJson(jsonCode, jsonPlace.getBoolean("beenVisited"));
    }

}

// TODO: add citation