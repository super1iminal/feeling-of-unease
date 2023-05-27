package model.items;

import model.Inventory;
import org.json.JSONObject;
import persistence.Writable;

// This class will not be used frequently, as it acts more as a parent class. However, it could be used on its own.
// It represents an item that has name and a code associated with it. The code is a 4 digit integer, and it exists
// to identify and separate items from each other that may have the same name
public class Item implements Writable {
    protected String name;
    protected int code;
    protected Inventory location;
    protected Inventory initialLocation;
    protected String initialDescription;
    protected String basicDescription;
    protected String pickUpMessage;
    protected String inventoryDescription;


    // REQUIRES: code is a 4 digit integer
    // MODIFIES: this
    // EFFECTS: sets the name and code of the item
    // initial json location is used to decide what part of an item's description one should print
    public Item(String name, String initialDescription, String basicDescription, String pickUpMessage,
                String inventoryDescription, int code, Inventory initialLocation) {
        this.name = name;
        this.initialDescription = initialDescription;
        this.basicDescription = basicDescription;
        this.pickUpMessage = pickUpMessage;
        this.inventoryDescription = inventoryDescription;

        this.location = initialLocation;
        this.initialLocation = initialLocation;
        this.location.addItem(this);
        this.code = code;
    }

    public String getInitialDescription() {
        return initialDescription;
    }

    public String getBasicDescription() {
        return basicDescription;
    }

    public String getPickUpMessage() {
        return pickUpMessage;
    }

    public String getInventoryDescription() {
        return inventoryDescription;
    }

    // returns the item's name
    public String getName() {
        return name;
    }

    // returns the item's code
    public int getCode() {
        return code;
    }

    public Inventory getLocation() {
        return location;
    }

    public boolean isAtInitialLocation() {
        return (location.equals(initialLocation));
    }

    // modifies: this, location
    // effects: removes this from the current location, changes the location, and adds it to that location
    public void setLocation(Inventory location) {
        this.location.removeItem(this);
        this.location = location;
        if (!location.getItems().contains(this)) {
            location.addItem(this);
        }
    }

    public String getJsonLocation() {
        return location.getJsonCode();
    }

    // saves the item code and location to JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("code", getCode());
        json.put("jsonLocation", getJsonLocation());
        return json;
    }
}

// TODO: add citation