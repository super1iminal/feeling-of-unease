package model.items;


import model.Inventory;
import model.containers.Place;

// This is a very basic class describing a key. It does not add any extra methods or fields on top of Item, the class
// which it extends
public class Key extends Item {

    // REQUIRES: keyCode must be a 4 digit integer
    // MODIFIES: this
    // EFFECTS: Constructs a key with a name and a code associated to it
    public Key(String name, String initialDescription, String basicDescription, String pickUpMessage,
               String inventoryDescription, int keyCode, Inventory initialLocation) {
        super(name, initialDescription, basicDescription, pickUpMessage, inventoryDescription,
                keyCode, initialLocation);
    }

}
