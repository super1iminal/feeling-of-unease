package model.containers;

import model.items.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlaceTest {
    Place place1;
    Place place2;
    Place place3;
    Container container1;
    Key key1;
    Key key2;

    @BeforeEach
    void setUp() {
        place1 = new Place("1", "d1", "sd1", "sni1", "um1", 1234,
                "00001");
        place2 = new Place("2", "d2", "sd2", "sni2", "um2", 0002,
                "00002");
        place3 = new Place("3", "d3", "sd3", "sni3", "um3", 0003,
                "00003");
        key1 = new Key("1", "ini1", "b1", "p1", "inv1",
                1234, place1);
        key2 = new Key("2", "ini2", "b2", "p2", "inv2",
                0000, place2);

        container1 = new Container("1", "d1", "sd1", "sni1", "um1",
                0001, "00004");

        // setting all places to be locked
        place1.lock();
        place2.lock();
        place3.lock();
    }

    @Test
    void testConstructor() {
        assertEquals("d1" , place1.getDescription());
        assertEquals("1", place1.getName());
        assertEquals(1234, place1.getLockCode());
        assertEquals(1, place1.getItems().size());

        assertNull(place1.getDirection("north"));
        assertNull(place1.getDirection("south"));
        assertNull(place1.getDirection("east"));
        assertNull(place1.getDirection("west"));

        assertTrue(place1.isLocked());
        assertNull(place1.getRequiredItem());
    }

    @Test
    void testIsDirectionWhenNull() {
        assertFalse(place1.isDirection("north"));
        assertFalse(place1.isDirection("south"));
    }

    @Test
    void testIsDirectionWhenPlace(){
        // setup
        place2.setDirection("north", place3);

        // test
        assertTrue(place2.isDirection("north"));
        assertFalse(place2.isDirection("south"));
        assertFalse(place2.isDirection("east"));
        assertFalse(place2.isDirection("west"));
    }

    @Test
    void testGetDirectionWhenNull() {
        assertEquals(null, place1.getDirection("north"));
    }

    @Test
    void testSetAndGetDirection() {
        // setup
        place2.setDirection("north", place3);

        // test
        assertEquals(place3, place2.getDirection("north"));
        assertEquals(null, place2.getDirection("south"));
        assertEquals(null, place2.getDirection("east"));
        assertEquals(null, place2.getDirection("west"));
    }

    @Test
    void testSetDirectionOverwrite() {
        // setup
        place1.setDirection("north", place2);
        place1.setDirection("north", place3);

        // test
        assertEquals(place3, place1.getDirection("north"));
        assertEquals(null, place1.getDirection("south"));
        assertEquals(null, place1.getDirection("east"));
        assertEquals(null, place1.getDirection("west"));
    }

    @Test
    void testGetDirectionsOneDirection() {
        // setup
        place1.setDirection("north", place2);

        //test
        Map<String, Place> directions = place1.getDirections();
        assertEquals(place2, directions.get("north"));
        assertEquals(null, directions.get("south"));
        assertEquals(null, directions.get("east"));
        assertEquals(null, directions.get("west"));
    }


    @Test
    void testContainer() {
        // setup
        place1.addContainer(container1);

        // test
        assertEquals(1, place1.getContainers().size());
        assertEquals(container1, place1.getContainers().get(0));
    }


}