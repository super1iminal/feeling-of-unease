package model;

import model.containers.Container;
import model.containers.Place;
import model.items.Item;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

// simple class that holds the general properties of a world. used for json writing
public class World  implements Writable {
    private static final String PLAYER_INVENTORY_JSON_CODE = "99999";
    // places
    private List<Place> placesList = new ArrayList<>();
    // inventories
    private List<Inventory> inventoriesList = new ArrayList<>();
    // items
    private List<Item> itemsList = new ArrayList<>();
    // player
    private Player player;


    public World() {
        EventLog.getInstance().logEvent(new Event("World Generated."));
    }

    /*
    *
    * important methods
    *
    * */

    /*
    * player methods
    * player methods
    * player methods
    * player methods
    * player methods
    * player methods
    * player methods
    * player methods
    * */

    // GETTERS AND SETTERS

    public void setPlayer(Player player) {
        this.player = player;
        inventoriesList.add(player);
    }

    public boolean worldHasPlayer() {
        return (this.player != null);
    }

    public void setPlayerName(String name) {
        this.player.setName(name);
    }

    // MODIFIES: this
    // EFFECTS: sets the player's location based on a JSON code
    public void setPlayerLocationFromJsonCode(String jsonCode) {
        player.setLocation(getPlaceFromJsonCode(jsonCode));
    }

    public boolean playerInventoryIsEmpty() {
        return player.getItems().isEmpty();
    }

    public List<Item> getPlayerItems() {
        return player.getItems();
    }

    public Place getPlayerLocation() {
        return player.getLocation();
    }

    public void setPlayerDead(boolean bool) {
        player.setDead(bool);
    }

    public String getContentsPlayerInventory() {
        return player.returnItemsString();
    }

    public String getPlayerName() {
        return player.getName();
    }

    public boolean isLocationInCardinal(String direction) {
        return (!isNull(player.getLocation().getDirection(direction)));
    }

    public boolean isLockedInCardinal(String direction) {
        return (getPlaceFromJsonCode(getJsonCodeFromCardinal(direction)).isLocked());
    }

    public boolean isRequiredItemInCardinal(String direction) {
        return (!isNull(getPlaceFromJsonCode(getJsonCodeFromCardinal(direction)).getRequiredItem()));
    }

    public boolean isDeadPlayer() {
        return player.isDead();
    }

    // METHODS

    // REQUIRES: direction is one of: north, east, south, west
    // MODIFIES: this
    // EFFECTS: moves the player in a given cardinal direction
    public void movePlayerInCardinal(String direction) {
        String jsonCode = getJsonCodeFromCardinal(direction);
        EventLog.getInstance().logEvent(new Event("Moving player from " + getPlayerJsonLocation()
                + "(" + getPlaceName(getPlayerJsonLocation()) + ")" + " to " + jsonCode + "(" + getPlaceName(jsonCode)
                + ")"));
        setPlayerLocationFromJsonCode(jsonCode);

    }

    public boolean playerHasItem(int itemCode) {
        return getInventoryFromJsonCode(PLAYER_INVENTORY_JSON_CODE).contains(itemCode);
    }

    public String getPlayerJsonLocation() {
        return player.getJsonLocation();
    }

    // returns a JSON code for the place in the given cardinal direction from the player
    public String getJsonCodeFromCardinal(String direction) {
        if (isLocationInCardinal(direction)) {
            return player.getLocation().getDirection(direction).getJsonCode();
        }
        return null;
    }

/*
    public int itemNameToCode(String itemName) {
        for (Item item: itemsList) {
            if (item.getName().equals(itemName)) {
                return item.getCode();
            }
        }
        return -1;
    }
*/

    // EFFECTS: returns true if the player has the required item to go in the cardinal direction
    public boolean playerHasRequiredItemInCardinal(String direction) {
        String jsonCode = getJsonCodeFromCardinal(direction);
        if (getPlaceFromJsonCode(jsonCode).hasRequiredItem()) {
            int requiredItemCode = getPlaceFromJsonCode(jsonCode).getRequiredItem().getCode();
            for (Item item: player.getItems()) {
                if (item.getCode() == requiredItemCode) {
                    return true;
                }
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: checks if a player can unlock a place (has the correct key), and, if so, unlocks it and returns true
    public boolean playerUnlockPlace(String placeName) {
        String jsonCode = getPlaceJsonFromName(placeName);
        if (getPlaceFromJsonCode(jsonCode) == null) {
            return false;
        }
        if (getPlaceFromJsonCode(jsonCode).isLocked()) {
            for (Item item: player.getItems()) {
                if (item.getCode() == getPlaceFromJsonCode(jsonCode).getLockCode()) {
                    getPlaceFromJsonCode(jsonCode).unlock();
                    EventLog.getInstance().logEvent(new Event("Player unlocked "
                            + getPlaceJsonFromName(placeName) + "(" + getPlaceName(getPlaceJsonFromName(placeName))
                            + ")"
                            + " with item " + item.getCode() + "(" + item.getName() + ")."));
                    return true;
                }
            }
        }
        return false;
    }


    /*
    * places methods
    * places methods
    * places methods
    * places methods
    * places methods
    * places methods
    * places methods
    * */

    // GETTERS AND SETTERS

    public void addPlace(Place place) {
        placesList.add(place);
        inventoriesList.add(place);
    }

    public boolean placeIsEmpty(String jsonCode) {
        return getInventoryFromJsonCode(jsonCode).isEmpty();
    }

    public List<Item> getPlaceItems(String jsonCode) {
        return getInventoryFromJsonCode(jsonCode).getItems();
    }

    // EFFECTS: returns a place's json code from the place's name
    public String getPlaceJsonFromName(String placeName) {
        for (Place place: placesList) {
            if (place.getName().equals(placeName)) {
                return place.getJsonCode();
            }
        }
        return null;
    }

    public void setContainerLocked(Container container, boolean locked) {
        container.setLocked(locked);
    }


    // EFFECTS: returns an inventory from its JSON code
    private Inventory getInventoryFromJsonCode(String jsonCode) {
        for (Inventory inventory: inventoriesList) {
            if (inventory.getJsonCode().equals(jsonCode)) {
                return inventory;
            }
        }
        return null;
    }


    // EFFECTS: returns a place from its JSON code
    private Place getPlaceFromJsonCode(String jsonCode) {
        for (Place place: placesList) {
            if (place.getJsonCode().equals(jsonCode)) {
                return place;
            }
        }
        return null;
    }

    public List<Place> getPlacesList() {
        return new ArrayList<>(placesList);
    }

    public List<Inventory> getInventoriesList() {
        return new ArrayList<>(inventoriesList);
    }

    public void setPlaceBeenVisited(Place place, boolean bool) {
        place.setBeenVisited(bool);
    }

    public boolean hasPlaceBeenVisited(String jsonCode) {
        return getPlaceFromJsonCode(jsonCode).isBeenVisited();
    }

    public String getPlaceName(String jsonCode) {
        return getPlaceFromJsonCode(jsonCode).getName();
    }

    public String getPlaceDescription(String jsonCode) {
        return getPlaceFromJsonCode(jsonCode).getDescription();
    }

    public String getPlaceShortDescription(String jsonCode) {
        return getPlaceFromJsonCode(jsonCode).getShortDescription();
    }

    public String getPlaceUnlockMessage(String jsonCode) {
        return getPlaceFromJsonCode(jsonCode).getUnlockMessage();
    }

    // returns a string of a place's items' descriptions
    public String getPlaceItemsDescription(String jsonCode) {
        if (getPlaceFromJsonCode(jsonCode).getSize() > 0) {
            return getPlaceFromJsonCode(jsonCode).returnItemsString();
        } else {
            return getPlaceFromJsonCode(jsonCode).getSearchNoItems();
        }
    }

    // REQUIRES: direction must be either "east", "west", "south", or "north"
    // MODIFIES: this, place
    // EFFECTS: sets the direction given to the place given, overwriting anything there and
    // sets place's inverse direction to be this place, overwriting anything there if the desire to attach the
    // locations both ways is specified
    public void setDirection(String direction, Place place1, Place place2, boolean bothWays) {
        place1.setDirection(direction, place2);
        if (bothWays) {
            place2.setDirection(directionInverter(direction), place1);
        }

    }

    // REQUIRES: direction must be either "east", "west", "south", or "north"
    // EFFECTS: returns the opposite cardinal direction of the one given
    public static String directionInverter(String direction) {
        for (List<String> pathPair: Arrays.asList(Arrays.asList("north", "south"), Arrays.asList("east, west"))) {
            if (pathPair.contains(direction)) {
                List<String> mutablePathPair = new ArrayList<>();
                mutablePathPair.addAll(pathPair);
                mutablePathPair.remove(direction);
                return mutablePathPair.get(0);
            }
        }
        // useless and will be tested due to code coverage but is rendered useless due to REQURIES statement
        return null;
    }

    // METHODS

    // returns true if a place contains an item based on a place json code and an item name
    public boolean placeContains(String jsonCode, String itemName) {
        return getInventoryFromJsonCode(jsonCode).contains(itemName);
    }

    /*
    * items methods
    * items methods
    * items methods
    * items methods
    * items methods
    * items methods
    * items methods
    * items methods
    * */

    // GETTERS AND SETTERS

    public void addItem(Item item) {
        itemsList.add(item);
    }

    public List<Item> getItemsList() {
        return new ArrayList<>(itemsList);
    }

    // EFFECTS: finds and returns an item based on its name
    private Item findItemFromName(String itemName) {
        for (Item item: itemsList) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public String getItemPickUpMessage(String itemName) {
        return findItemFromName(itemName).getPickUpMessage();
    }




    // METHODS


    /*
    *
    * stuff related to interacting with the UI
    *
    *
    * */

    // ITEMS
    // EFFECTR: moves an item from one inventory to another based on the item's name and the JSON code for
    // the desired location.
    public void moveItem(String itemName, String jsonCodeTo) {
        EventLog.getInstance().logEvent(new Event("Item with code " + findItemFromName(itemName).getCode()
                + " and name " + itemName
                + " moved from inventory "
                + findItemFromName(itemName).getJsonLocation() + " to " + jsonCodeTo));
        getInventoryFromJsonCode(jsonCodeTo).addItem(findItemFromName(itemName));

    }

/*
    //
    private Inventory findInventoryFromItem(String itemName) {
        for (Inventory inventory: inventoriesList) {
            if (inventory.contains(itemName)) {
                return inventory;
            }
        }
        return null;
    }

*/



    /*
    *
    * Saving stuff
    *
    * */

    // converts a world to JSON format
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerName", player.getName());
        json.put("playerJsonLocation", player.getJsonLocation());
        json.put("locations", locationsToJson());
        json.put("items", itemsToJson());
        return json;
    }

    // EFFECTS: transfers locations to JSON format by adding all locations' JSON conversion to a list
    public JSONArray locationsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Place l: placesList) {
            jsonArray.put(l.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: transfers items to JSON format by adding all items' JSON conversion to a list
    public JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i: itemsList) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

    /*
    *
    *
    * loading stuff
    *
    *
    * */

    // EFFECTS: finds an item in the list of items from the item code
    private Item findItemFromCode(int itemCode) {
        for (Item item: itemsList) {
            if (item.getCode() == itemCode) {
                return item;
            }
        }
        return null;
    }

    public String findItemNameFromCode(int itemCode) {
        return findItemFromCode(itemCode).getName();
    }

    // MODIFIES: this
    // EFFECTS: sets a place's locked state from a place code and a boolean
    public void setPlaceLockedFromPlaceCode(String jsonCode, boolean locked) {
        getPlaceFromJsonCode(jsonCode).setLocked(locked);
    }

    // MODIFIES: this
    // EFFECTS: sets a place's beenVisited state from a place code and a boolean
    public void setPlaceBeenVisitedFromJson(String jsonCode, boolean bool) {
        getPlaceFromJsonCode(jsonCode).setBeenVisited(bool);
    }


}

