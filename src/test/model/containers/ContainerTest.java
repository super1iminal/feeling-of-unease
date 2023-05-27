package model.containers;

import model.items.Item;
import model.items.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContainerTest {
    Container container1;
    Container container2;
    Container container3;
    Key key1;
    Key key2;

    @BeforeEach
    void setUp() {
        container1 = new Container("1", "d1", "sd1", "sni1", "um1", 1234,
                "00001");
        container2 = new Container("2", "d2", "sd2", "sni2", "um2", 0002,
                "00002");
        container3 = new Container("3", "d3", "sd3", "sni3", "um3", 0003,
                "00003");
        key1 = new Key("1", "ini1", "b1", "p1", "inv1",
                1234, container1);
        key2 = new Key("2", "ini2", "b2", "p2", "inv2",
                0000, container2);

        container1.addItem(key2);
        container2.setBeenVisited(true);

        // setting all places to be locked
        container1.lock();
        container2.lock();
        container3.lock();
    }

    @Test
    void testConstructor() {
        assertEquals("d1" , container1.getDescription());
        assertEquals("1", container1.getName());
        assertEquals(1234, container1.getLockCode());
        assertEquals(2, container1.getItems().size());
        assertEquals("sd1", container1.getShortDescription());
        assertEquals("sni1", container1.getSearchNoItems());
        assertEquals("um1", container1.getUnlockMessage());
        assertFalse(container1.isBeenVisited());
        assertTrue(container2.isBeenVisited());
        assertTrue(container1.isLocked());
        assertFalse(container1.hasRequiredItem());
    }

    @Test
    void testLock() {
        // setup
        container1.unlock();
        assertFalse(container1.isLocked());

        // test
        container1.lock();
        assertTrue(container1.isLocked());
    }

    @Test
    void testReturnItemsString() {
        assertEquals("ini1\nb2", container1.returnItemsString());
    }

    @Test
    void testUnlockWithKeyTrue() {
        // test
        assertTrue(container1.unlock(key1));
        assertFalse(container1.isLocked());

    }

    @Test
    void testUnlockWithKeyFalse() {
        // test
        assertFalse(container1.unlock(key2));
        assertTrue(container1.isLocked());
    }

}
