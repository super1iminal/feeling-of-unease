package model;

import model.containers.Place;
import model.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    World world;
    Player player;
    Item item1;
    Item item2;
    Item item3;
    Place place1;
    Place place2;
    Place place3;

    @BeforeEach
    void setUp() {
        world = new World();
        player = new Player("John");
        place1 = new Place("1", "d1", "sd1", "sni1", "um1", 0001,
                "00001");
        place2 = new Place("2", "d2", "sd2", "sni2", "um2", 0002,
                "00002");
        place3 = new Place("3", "d3", "sd3", "sni3", "um3", 0003,
                "00003");
        item1 = new Item("1", "ini1", "b1", "p1", "inv1",
                0001, place1);
        item2 = new Item("2", "ini2", "b2", "p2", "inv2",
                0003, place2);
        item3 = new Item("3", "ini3", "b3", "p3", "inv3",
                0002, player);

        world.setDirection("north", place1, place2, true);
        world.setDirection("south", place1, place3, true);
        place3.setRequiredItem(item3);

        world.setPlayer(player);

        world.addPlace(place1);
        world.addPlace(place2);
        world.addPlace(place3);

        world.addItem(item1);
        world.addItem(item2);
        world.addItem(item3);

        world.setPlayerLocationFromJsonCode("00001");

        world.setContainerLocked(place1, false);
        world.setContainerLocked(place2, true);
        world.setContainerLocked(place3, false);

        world.setPlaceBeenVisited(place2, true);

    }


    public void testSetUp() {
        // player stuff
        assertEquals(player.getJsonLocation(), "00001");
        assertEquals(player.getJsonLocation(), world.getPlayerJsonLocation());

        assertEquals(player.getName(), world.getPlayerName());
        assertEquals(player.getJsonLocation(), world.getPlayerJsonLocation());
        assertEquals(player.getLocation(), world.getPlayerLocation());


        assertTrue(world.playerHasRequiredItemInCardinal("south"));


        // items and places
        assertTrue(place1.contains(1));
        assertTrue(world.placeContains("00001", "1"));
        assertFalse(world.placeContains("00001", "2"));
        assertFalse(place1.contains(2));
        assertTrue(place2.contains(2));
        assertFalse(place2.contains(1));


        // places
        assertTrue(place2.isLocked());
        assertFalse(place1.isLocked());
        assertTrue(place2.isBeenVisited());



        // note: next test is implicit from setting player to start here
        assertTrue(place1.isBeenVisited());
        assertEquals(place1.getDescription(), world.getPlaceDescription("00001"));
        assertEquals(place1.getShortDescription(), world.getPlaceShortDescription("00001"));
        assertEquals(place1.getUnlockMessage(), world.getPlaceUnlockMessage("00001"));
        assertEquals(world.getPlaceName("00001"), place1.getName());

        assertTrue(world.getItemsList().contains(item1));
        assertTrue(world.getItemsList().contains(item2));
        assertTrue(world.getItemsList().contains(item3));
        assertEquals(item1.getPickUpMessage(), world.getItemPickUpMessage("1"));
    }


    /*
    *
    * player
    *
    * */

//    @Test
//    void setPlayer() {
//
//    }

    @Test
    void testGetPlayerLocation() {
        assertEquals(player.getLocation(), world.getPlayerLocation());
    }

    @Test
    void testSetPlayerDead() {
        world.setPlayerDead(true);
        assertTrue(world.isDeadPlayer());
    }

    @Test
    void testGetContentsPlayerInventory() {
        assertEquals("inv3", world.getContentsPlayerInventory());
    }

    @Test
    void testGetPlayerName() {
        assertEquals("John", world.getPlayerName());
    }

    @Test
    void testIsLocationInCardinal() {
        assertTrue(world.isLocationInCardinal("north"));
        assertTrue(world.isLocationInCardinal("south"));
        assertFalse(world.isLocationInCardinal("east"));
    }

    @Test
    void testIsLockedInCardinal() {
        assertTrue(world.isLockedInCardinal("north"));
        assertFalse(world.isLockedInCardinal("south"));
    }

    @Test
    void testIsRequiredItemInCardinal() {
        assertFalse(world.isRequiredItemInCardinal("north"));
        assertTrue(world.isRequiredItemInCardinal("south"));
    }

    @Test
    void testIsDeadPlayer() {
        assertFalse(world.isDeadPlayer());
    }

    @Test
    void testMovePlayerInCardinal() {
        world.movePlayerInCardinal("north");
        assertEquals("00002", world.getPlayerJsonLocation());
        assertEquals(place2, world.getPlayerLocation());
    }

    @Test
    void testPlayerHasItem() {
        assertTrue(world.playerHasItem(2));
        assertFalse(world.playerHasItem(3));
    }

    @Test
    void testGetPlayerJsonLocation() {
        assertEquals("00001", world.getPlayerJsonLocation());
    }

    @Test
    void testGetJsonCodeFromCardinal() {
        assertEquals("00002", world.getJsonCodeFromCardinal("north"));
    }

    @Test
    void testGetJsonCodeFromCardinalNull() {
        assertNull(world.getJsonCodeFromCardinal("east"));
    }

    @Test
    void testPlayerHasRequiredItemInCardinalTrue() {
        assertTrue(world.playerHasRequiredItemInCardinal("south"));
    }

    @Test
    void testPlayerHasRequiredItemInCardinalFalseNoRequiredItem() {
        assertFalse(world.playerHasRequiredItemInCardinal("north"));
    }

    @Test
    void testPlayerHasRequiredItemInCardinalFalseDoesNotHaveItem() {
        Place place4 = new Place("4", "d4", "sd4", "sni4", "um4", 0004,
                "00004");
        world.addPlace(place4);

        Item item4 = new Item("4", "ini4", "b4", "p4", "inv4",
                0004, place4);
        world.addItem(item4);

        world.setContainerLocked(place4, true);

        place4.setRequiredItem(item4);

        world.setDirection("east", place1, place4, true);

        assertFalse(world.playerHasRequiredItemInCardinal("east"));
    }

    @Test
    void testPlayerUnlockPlace() {
        assertTrue(world.playerUnlockPlace("2"));
    }

    @Test
    void testPlayerUnlockPlaceFalseNotLocked() {
        assertFalse(world.playerUnlockPlace("3"));
    }

    @Test
    void testPlayerUnlockPlaceNoKey() {
        Place place4 = new Place("4", "d4", "sd4", "sni4", "um4", 0004,
                "00004");
        world.addPlace(place4);

        world.setDirection("east", place1, place4, true);

        assertFalse(world.playerUnlockPlace("4"));
    }


    /*
    *
    * places
    *
    * */

    @Test
    void testGetPlacesList() {
        List<Place> placesList = world.getPlacesList();
        assertEquals(3, placesList.size());
        assertTrue(placesList.contains(place1));
        assertTrue(placesList.contains(place2));
        assertTrue(placesList.contains(place3));
    }

    @Test
    void testGetInventoriesList() {
        List<Inventory> inventoriesList = world.getInventoriesList();
        assertEquals(4, inventoriesList.size());
        assertTrue(inventoriesList.contains(place1));
        assertTrue(inventoriesList.contains(place2));
        assertTrue(inventoriesList.contains(place3));
        assertTrue(inventoriesList.contains(player));
    }

    @Test
    void testSetPlaceBeenVisited() {
        assertFalse(place3.isBeenVisited());
        world.setPlaceBeenVisited(place3, true);
        assertTrue(place3.isBeenVisited());
    }

    @Test
    void testHasPlaceBeenVisited() {
        assertTrue(world.hasPlaceBeenVisited("00002"));
        assertFalse(world.hasPlaceBeenVisited("00003"));
    }

    @Test
    void testGetPlaceName() {
        assertEquals("1", world.getPlaceName("00001"));
    }

    @Test
    void testGetPlaceDescription() {
        assertEquals("d1", world.getPlaceDescription("00001"));
    }

    @Test
    void testGetPlaceShortDescription() {
        assertEquals("sd1", world.getPlaceShortDescription("00001"));
    }

    @Test
    void testGetPlaceUnlockMessage() {
        assertEquals("um1", world.getPlaceUnlockMessage("00001"));
    }

    @Test
    void testGetPlaceItemsDescription() {
        assertEquals("ini1", world.getPlaceItemsDescription("00001"));
    }

    @Test
    void testGetPlaceItemsDescriptionNoItems() {
        Place place4 = new Place("4", "d4", "sd4", "sni4", "um4", 0004,
                "00004");
        world.addPlace(place4);

        assertEquals("sni4", world.getPlaceItemsDescription("00004"));
    }

    @Test
    void testPlaceContains() {
        assertTrue(world.placeContains("00001", "1"));
        assertFalse(world.placeContains("00001", "2"));
    }

    @Test
    void getPlaceJsonFromNameStringReturned() {
        assertEquals("00001", world.getPlaceJsonFromName("1"));
    }

    @Test
    void getPlaceJsonFromNameNullReturned() {
        assertNull(world.getPlaceJsonFromName("-1"));
    }


    /*
    *
    * items
    *
    * */

    @Test
    void getItemsList() {
        List<Item> itemsList = world.getItemsList();
        assertEquals(3, itemsList.size());
        assertTrue(itemsList.contains(item1));
        assertTrue(itemsList.contains(item2));
        assertTrue(itemsList.contains(item3));
    }
/*
    @Test
    void moveItem() {
    }

    @Test
    void testMoveItem() {
    }*/

    @Test
    void testGetItemPickUpMessage() {
        assertEquals("p1", world.getItemPickUpMessage("1"));
    }

    /*
    *
    * saving
    *
    * */

/*    @Test
    void toJson() {
        JSONObject json = world.toJson();
        assertEquals("John", json.get("playerName"));
        assertEquals("")
    }*/

    @Test
    void locationsToJson() {
    }

    @Test
    void itemsToJson() {
    }
}