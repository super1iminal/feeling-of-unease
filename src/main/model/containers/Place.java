package model.containers;

import model.items.Item;

import java.util.*;


// It represents a place (location), which holds
// a description, a list of items, and can hold up to four references to other places, one for each cardinal direction.
// It extends container so can therefore hold items, be locked and have a required item.
public class Place extends Container {
    Map<String, Place> directions;
    List<Container> containers;

    // REQUIRES: jsonCode be a unique 5 digit integer
    // lockCode be a 4 digit integer
    // EFFECTS: creates a place with a description, an empty list of items, and a dictionary of a string of a cardinal
    // direction and the subsequent place it's connected to, which is initialized to be null
    public Place(String name, String description, String sd, String searchNoItems, String unlockMessage,
                 int lockCode, String jsonCode) {
        super(name, description, sd, searchNoItems, unlockMessage, lockCode, jsonCode);
        // creating a dict of places connected to the place
        directions = new HashMap<>();
        directions.put("north", null);
        directions.put("south", null);
        directions.put("east", null);
        directions.put("west", null);


        // creating a list of containers within the place
        containers = new ArrayList<>();
    }


    /*
    Setters
     */

/*
    public void setInitialItems(List<Item> items) {
        for (Item item : items) {
            addItem(item);

        }
    }
*/

    // REQUIRES: direction must be either "east", "west", "south", or "north"
    // MODIFIES: this
    // EFFECTS: sets the direction given to the place given, overwriting anything there
    public void setDirection(String direction, Place place) {
        directions.put(direction, place);
    }




    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: adds a container to the list of containers
    public void addContainer(Container container) {
        containers.add(container);
    }


    /*
    Getters
     */

    public Map<String, Place> getDirections() {
        return directions;
    }

    // REQUIRES: direction must be either "east", "west", "south", or "north"
    // MODIFIES: none
    // EFFECTS: returns the direction if there is one there, and null if not.
    public Place getDirection(String direction) {
        return directions.get(direction);
    }

    // REQUIRES: direction must be either "east", "west", "south", or "north"
    // MODIFIES: none
    // EFFECTS: returns true if there is a place in the given direction, and false otherwise
    public boolean isDirection(String direction) {
        if (directions.get(direction) != null) {
            return true;
        }
        return false;
    }



    public List<Container> getContainers() {
        return containers;
    }
}
