package model.containers;

import model.Inventory;
import model.items.Item;
import model.items.Key;
import org.json.JSONObject;
import persistence.Writable;

import static java.util.Objects.isNull;

// Container is basically a lockable inventory with the possibility of having
// a "required item", which, in of itself does nothing, but is utilized when a
// player attempts to access the container. This is different from the locking mechanism
// since the container itself can be locked or unlocked, but a player must hold the required
// item to enter the container at all times.
public class Container extends Inventory implements Writable {
    String description;
    String shortDescription;
    String name;
    String searchNoItems;
    String unlockMessage;
    int lockCode;
    boolean locked;
    boolean beenVisited;
    Item requiredItem;

    // REQUIRES: jsonCode be a unique 5 digit integer. Stored as a string due to being able to hold leading zeroes.
    // cannot be 00000 or 99999, as these are reserved for temporary item storage and the inventory, repspectively
    // lockCode be a 4 digit integer
    // effects: creates a container with a description, a name, a lock code, a locked state (false), a required item,
    // (null), and an empty inventory.
    public Container(String name, String description, String sd, String searchNoItems, String unlockMessage,
                     int lockCode, String jsonCode) {
        super(jsonCode);
        this.beenVisited = false;
        this.description = description;
        this.searchNoItems = searchNoItems;
        this.unlockMessage = unlockMessage;
        this.shortDescription = sd;
        this.name = name;
        this.lockCode = lockCode;
        this.locked = false;
        this.requiredItem = null;
    }

    // effects: creates an empty placeholder container to be used in tests, and tests only
    public Container() {
        super("12345");
        this.beenVisited = false;
        this.description = "description";
        this.searchNoItems = "searchNoItems";
        this.unlockMessage = "unlockMessage";
        this.shortDescription = "sd";
        this.name = "name";
        this.lockCode = 1234;
        this.locked = false;
        this.requiredItem = null;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getSearchNoItems() {
        return searchNoItems;
    }

    public String getUnlockMessage() {
        return unlockMessage;
    }

    // Returns the description of the place
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setRequiredItem(Item item) {
        this.requiredItem = item;
    }

    public void setBeenVisited(boolean bool) {
        this.beenVisited = bool;
    }

    public boolean isBeenVisited() {
        return beenVisited;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: unlocks the lock
    public void unlock() {
        this.locked = false;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: given a key, if the code of the key matches that of the lock, the lock is unlocked and returns true
    // Otherwise, it stays locked and returns false
    public boolean unlock(Key key) {
        if (key.getCode() == getLockCode()) {
            this.locked = false;
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: sets a container to be locked based on the bool given
    public void setLocked(Boolean bool) {
        locked = bool;
    }

    public boolean hasRequiredItem() {
        return (!isNull(this.requiredItem));
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: given an inventory, if one of the items in the inventory matches the required item, the lock is
    // unlocked and returns true. Otherwise, it stays locked and returns false
    // To be used in places that are locked but by items
    public Item getRequiredItem() {
        return requiredItem;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: locks the lock
    public void lock() {
        this.locked = true;
    }

    // returns the code of the lock
    public int getLockCode() {
        return lockCode;
    }

    // returns the state of lockness of the lock
    public boolean isLocked() {
        return locked;
    }


    // saves a container's locked state, visited state, and jsonCode to JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("jsonCode", getJsonCode());
        json.put("locked", isLocked());
        json.put("beenVisited", isBeenVisited());
        return json;
    }

    // returns a string of all items in the container
    @Override
    public String returnItemsString() {
        String rep = "";
        int maxIndex = getSize();
        int counter = 0;
        for (Item item: inventory) {
            counter += 1;
            if (!item.isAtInitialLocation()) {
                rep = rep.concat(item.getBasicDescription());
            } else {
                rep = rep.concat(item.getInitialDescription());
            }
            if (counter < maxIndex) {
                rep += "\n";
            }
        }
        return rep;
    }
}

// TODO: add citation
