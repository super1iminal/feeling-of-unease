package model.items;

import model.containers.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    Item item;
    Place place1 = new Place("Meep", "Meep", "Meep", "Meep", "Meep",
            1234, "00001");
    Place place2 = new Place("Meep", "Meep", "Meep", "Meep", "Meep",
            1234, "00002");

    @BeforeEach
    void setUp() {
        item = new Item("1", "ini1", "b1", "p1", "inv1",
                1234, place1);

    }

    @Test
    void testConstructor() {

        assertEquals(1234, item.getCode());
        assertEquals("1", item.getName());
        assertEquals("ini1", item.getInitialDescription());
        assertEquals("b1", item.getBasicDescription());
        assertEquals("p1", item.getPickUpMessage());
        assertEquals("inv1", item.getInventoryDescription());
        assertEquals(place1, item.getLocation());
    }

    @Test
    void testSetJsonLocation() {
        item.setLocation(place2);
        assertEquals(place2, item.getLocation());
        assertEquals("00002", item.getJsonLocation());
    }
}