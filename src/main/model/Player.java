package model;

import model.items.Item;
import model.containers.Place;

import java.util.ArrayList;

import static java.util.Objects.isNull;

// Player represents a player, and it extends inventory due to it needing those operations. The player class holds
// the player's name and location. The location is a Place. The few operations that player implements mostly have to
// do with the location, as the player exists in a location, and can move from location to location through the
// cardinal directions.
public class Player extends Inventory {
    private Place location;
    private String name;
    private boolean dead;

    // Creates a player with a name, an empty inventory, and sets their location to null
    public Player(String name) {
        super("99999");
        location = null;
        this.name = name;
        this.dead = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES: direction is either north, south, east or west
    // MODIFIES: this
    // EFFECTS: changes a player's location.
    public void changeLocation(String direction) {
        location = location.getDirection(direction);
/*        if (location.isDirection(direction) && !location.getDirection(direction).isLocked()) {
            if (!isNull(location.getDirection(direction).getRequiredItem())) {
                for (Item item: inventory) {
                    if (item.equals(location.getDirection(direction).getRequiredItem())) {
                        location = location.getDirection(direction);
                        return true;
                    }
                    setDead(true);
                    return false;
                }

            } else {
                location = location.getDirection(direction);
                return true;
            }
        }
        return false;*/
    }

    // MODIFIES: this
    // sets the player location
    public void setLocation(Place place) {
        this.location = place;
        place.setBeenVisited(true);
    }

    // gets the player location
    public Place getLocation() {
        return location;
    }

    public String getJsonLocation() {
        return location.getJsonCode();
    }

    // gets the player name
    public String getName() {
        return name;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean bool) {
        this.dead = bool;
    }

    // returns all descriptions of the items in a player's inventory
    @Override
    public String returnItemsString() {
        String rep = "";
        int maxIndex = getSize();
        int counter = 0;
        for (Item item: inventory) {
            counter += 1;
            rep += item.getInventoryDescription();
            if (counter < maxIndex) {
                rep += "\n";
            }
        }
        return rep;
    }

}
