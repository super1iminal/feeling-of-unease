package model;

import model.items.Item;

import java.util.ArrayList;
import java.util.List;

// This class is, at its base, a list of Items. That is its only field. The operations defined on it are simply
// Adding and taking out items based on their names or codes. There is also a varible used to determine the location of
// an item
public class Inventory {
    protected ArrayList<Item> inventory;
    protected String jsonCode;

    // EFFECTS: creates a new empty inventory with a jsonCode of 99999
    public Inventory(String jsonCode) {
        inventory = new ArrayList<>();
        this.jsonCode = jsonCode;
    }

    // EFFECTS: returns jsonCode
    public String getJsonCode() {
        return jsonCode;
    }


    // MODIFIES: this, item
    // EFFECTS: Adds an item to the player's inventory and sets that item's location to this.
    public void addItem(Item item) {
        if (!inventory.contains(item)) { // was missing this and was causing bug when loading and
            // items should be where they already are, adds them again to their current location
            inventory.add(item);
        }
        if (item.getLocation() != this) {
            item.setLocation(this);
        }
    }

    // REQUIRES: inventory.hasItem(itemName)
    // MODIFIES: this
    // EFFECTS: Removes an item from the player's inventory based on the item's name and returns it.
    // Removes from most recently added to first added
    // note that this method does not need to change the jsonLocation of the item
    public Item removeItem(String itemName) {
        int itemPos = findItemIndex(itemName);
        Item removedItem = inventory.get(itemPos);
        inventory.remove(itemPos);
        return removedItem;
    }

    // REQUIRES: inventory.hasItem(itemName)
    // MODIFIES: this
    // EFFECTS: Removes an item from the player's inventory based on the item's code and returns it.
    // Removes from most recently added to first added
    // note that this method does not need to change the jsonLocation of the item
    public Item removeItem(int itemCode) {
        int itemPos = findItemIndex(itemCode);
        Item removedItem = inventory.get(itemPos);
        inventory.remove(itemPos);
        return removedItem;
    }

    // REQUIRES: inventory.hasItem(itemName)
    // MODIFIES: this
    // EFFECTS: Removes an item from the player's inventory based on the item's name and returns it.
    // Removes from most recently added to first added
    // note that this method does not need to change the jsonLocation of the item
    public Item removeItem(Item item) {
        inventory.remove(item);
        return item;
    }

    // REQUIRES: None
    // MODIFIES: None
    // EFFECTS: Given the item's name, returns true if an item is contained within the inventory, and false if not
    public boolean contains(String itemName) {
        if (findItemIndex(itemName) != -1) {
            return true;
        }
        return false;
    }

    // REQUIRES: None
    // MODIFIES: None
    // EFFECTS: Given the item's code, returns true if an item is contained within the inventory and false if not
    public boolean contains(int itemCode) {
        if (findItemIndex(itemCode) != -1) {
            return true;
        }
        return false;
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns the index of an item given its name, and -1 if it cannot be found
    protected int findItemIndex(String itemName) {
        for (int counter = 0; counter < inventory.size(); counter++) {
            if ((inventory.get(counter).getName()).equals(itemName)) {
                return counter;
            }
        }
        return -1;
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns the index of an item given its code, and -1 if it cannot be found
    protected int findItemIndex(int itemCode) {
        for (int counter = 0; counter < inventory.size(); counter++) {
            if ((inventory.get(counter).getCode()) == (itemCode)) {
                return counter;
            }
        }
        return -1;
    }

    public Item findItem(int itemCode) {
        for (Item item: inventory) {
            if (item.getCode() == itemCode) {
                return item;
            }
        }
        return null;
    }

    // EFFECTS: finds an item based on an item name
    public Item findItem(String itemName) {
        for (Item item: inventory) {
            if (item.getName() == itemName) {
                return item;
            }
        }
        return null;
    }


    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns true if the inventory is empty and false if not.
    public boolean isEmpty() {
        if (inventory.size() == 0) {
            return true;
        }
        return false;
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns the number of items in the inventory
    public int getSize() {
        return inventory.size();
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns a string of all the items in the inventory, separated with linebreaks. There is no final
    //                                                                                          linebreak.
    // If nothing is in the inventory, return an empty string
    public String returnItemsString() {
        String rep = "";
        int maxIndex = getSize();
        int counter = 0;
        for (Item item: inventory) {
            counter += 1;
            rep += item.getBasicDescription();
            if (counter < maxIndex) {
                rep += "\n";
            }
        }
        return rep;
    }

    // EFFECTS: returns an IMMUTABLE copy of all the items in the inventory
    public ArrayList<Item> getItems() {
        return new ArrayList<>(inventory);
    }

    // two inventories are equal if their JSON codes equal each other
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        Inventory other = (Inventory)obj;

        return (this.getJsonCode().equals(other.getJsonCode()));
    }

    // hash code is simply the jsonCode
    @Override
    public int hashCode() {
        return Integer.parseInt(getJsonCode());
    }
}
