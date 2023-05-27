package model;

import model.containers.Place;
import model.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;
    Place place1;
    Place place2;
    Item item1;
    Item item2;
    World world;

    @BeforeEach
    void setUp() {
        world = new World();

        player = new Player("Player");
        world.setPlayer(player);
        place1 = new Place("1", "d1", "sd1", "sni1", "um1", 1234,
                "00001");
        place2 = new Place("2", "d2", "sd2", "sni2", "um2", 1234,
                "00002");

        world.addPlace(place1);
        world.addPlace(place2);

        //setting places to be locked
        place1.lock();
        place2.lock();

        world.setDirection("north", place1, place2, true);
        player.setLocation(place1);

        item1 = new Item("1", "ini1", "b1", "p1", "inv1",
                1234, place1);
        item2 = new Item("2", "ini2", "b2", "p2", "inv2",
                1235, place2);

        world.addItem(item1);
        world.addItem(item2);

    }

    @Test
    void testConstructor() {
        assertEquals(place1, player.getLocation());
        assertEquals("Player", player.getName());
        assertEquals("00001", player.getJsonLocation());
        assertFalse(player.isDead());
    }

    @Test
    void testChangeLocation() {
        place1.setDirection("north", place2);
        place2.unlock();
        player.changeLocation("north");
        assertEquals(place2, player.getLocation());
    }

    @Test
    void testSetDead() {
        player.setDead(true);
        assertTrue(player.isDead());
    }

    @Test
    void testReturnItemsString() {
        world.moveItem("1", "99999");
        world.moveItem("2", "99999");
        assertEquals("inv1\ninv2", player.returnItemsString());
    }

/*
    @Test
    void testChangeLocationFalseLocationLocked() {
        assertFalse(player.changeLocation("north"));
        assertEquals(place1, player.getLocation());
    }

    @Test
    void testChangeLocationFalseNullLocation() {
        assertFalse(player.changeLocation("south"));
        assertEquals(place1, player.getLocation());
    }

    @Test
    void testChangeLocationFalseRequiredItem() {
        place2.unlock();
        place2.setRequiredItem(requiredItem);
        assertFalse(player.changeLocation("north"));
    }

    @Test
    void testChangeLocationTrueRequiredItem() {
        place2.unlock();
        place2.setRequiredItem(requiredItem);
        player.addItem(requiredItem);
        assertTrue(player.changeLocation("north"));
    }

    @Test
    void testChangeLocationTrueNoRequiredItem() {
        place2.unlock();
        assertTrue(player.changeLocation("north"));
        assertEquals(place2, player.getLocation());
    }*/

    @Test
    void testSetLocation() {
        player.setLocation(place2);
        assertEquals(place2, player.getLocation());
    }

}