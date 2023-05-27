package model;

import model.containers.Container;
import model.items.Item;
import model.items.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Inventory inventory;
    Item item1;
    Item item2;


    @BeforeEach
    void setUp() {
        inventory = new Inventory("12345");
        item1 = new Item("1", "ini1", "b1", "p1", "inv1",
                0000, new Container());
        item2 = new Item("2", "ini2", "b2", "p2", "inv2",
                0001, new Container());
        assertTrue(inventory.isEmpty());
        assertEquals(0, inventory.getSize());
        assertEquals(12345, inventory.hashCode());
    }

    @Test
    void testAddItem() {
        inventory.addItem(item1);
        assertEquals(1, inventory.getSize());
        assertTrue(inventory.contains("1"));
    }

    @Test
    void testAddTwoDifferentItems() {
        // setup
        inventory.addItem(item1);
        inventory.addItem(item2);

        // test
        assertEquals(2, inventory.getSize());
        assertTrue(inventory.contains("1"));
        assertTrue(inventory.contains("2"));
    }


    @Test
    void testRemoveItemInInventoryItemName() {
        // setup
        inventory.addItem(item1);

        // test
        assertEquals(item1, inventory.removeItem("1"));
        assertTrue(inventory.isEmpty());
    }

    @Test
    void testRemoveTwoItemsInInventoryItemName() {
        // setup
        inventory.addItem(item1);
        inventory.addItem(item2);

        // test
        assertEquals(item2, inventory.removeItem("2"));
        assertEquals(1, inventory.getSize());
        assertEquals(item1, inventory.removeItem("1"));
        assertTrue(inventory.isEmpty());
    }

    @Test
    void testRemoveItemInInventoryItemCode() {
        // setup
        inventory.addItem(item1);

        // test
        assertEquals(item1, inventory.removeItem(0000));
        assertTrue(inventory.isEmpty());
    }

    @Test
    void testRemoveTwoItemsInInventoryItemCode() {
        // setup
        inventory.addItem(item1);
        inventory.addItem(item2);

        // test
        assertEquals(item2, inventory.removeItem(0001));
        assertEquals(1, inventory.getSize());
        assertEquals(item1, inventory.removeItem(0000));
        assertTrue(inventory.isEmpty());
    }

    @Test
    void testHasItemNameTwoItems() {
        // setup
        inventory.addItem(item1);
        inventory.addItem(item2);

        // test
        assertTrue(inventory.contains("1"));
        assertTrue(inventory.contains("2"));
    }

    @Test
    void testHasItemNameFalse() {
        // test
        assertFalse(inventory.contains("1"));
    }

    @Test
    void testFindItemIndexNameTwoItems() {
        // setup
        inventory.addItem(item1);
        inventory.addItem(item2);

        // test
        assertEquals(0, inventory.findItemIndex("1"));
        assertEquals(1, inventory.findItemIndex("2"));
    }

    @Test
    void testFindItemIndexNameFail() {
        // test
        assertEquals(-1, inventory.findItemIndex("1"));
    }

    @Test
    void testIsEmptyTrue() {
        assertTrue(inventory.isEmpty());
    }

    @Test
    void testIsEmptyFalse() {
        // setup
        inventory.addItem(item1);

        // test
        assertFalse(inventory.isEmpty());
    }

    @Test
    void testReturnItemsStringZeroItems() {
        assertEquals("", inventory.returnItemsString());
    }

    @Test
    void testReturnItemsStringOneItem() {
        // setup
        inventory.addItem(item1);

        // test
        assertEquals("b1", inventory.returnItemsString());
    }

    @Test
    void testReturnItemsStringTwoItems() {
        // setup
        inventory.addItem(item1);
        inventory.addItem(item2);

        // test
        assertEquals("b1\nb2", inventory.returnItemsString());
    }

    @Test
    void testFindItemItemCode() {
        inventory.addItem(item1);

        //test
        assertEquals(item1, inventory.findItem(0000));
    }

    @Test
    void testFindItemItemCodeNullNoItems() {
        assertNull(inventory.findItem(0000));
    }

    @Test
    void testFindItemItemCodeNull() {
        inventory.addItem(item1);

        //test
        assertNull(inventory.findItem(0001));
    }

    @Test
    void testFindItemItemName() {
        inventory.addItem(item1);

        // test
        assertEquals(item1, inventory.findItem("1"));
    }

    @Test
    void testFindItemItemNameNullNoItems() {
        assertNull(inventory.findItem("1"));
    }

    @Test
    void testFindItemItemNameNull() {
        inventory.addItem(item1);

        //test
        assertNull(inventory.findItem("2"));
    }


    @Test
    void testHasItemCodeTwoTimes() {
        // setup
        inventory.addItem(item1);
        inventory.addItem(item2);

        // test
        assertTrue(inventory.contains(0000));
        assertTrue(inventory.contains(0001));
    }

    @Test
    void testHasItemCodeFalse() {
        // test
        assertFalse(inventory.contains(0000));
    }

    @Test
    void testFindItemIndexCodeTwoTimes() {
        // setup
        inventory.addItem(item1);
        inventory.addItem(item2);

        // test
        assertEquals(0, inventory.findItemIndex(0000));
        assertEquals(1, inventory.findItemIndex(0001));
    }

    @Test
    void testFindItemIndexCodeFail() {
        // test
        assertEquals(-1, inventory.findItemIndex(0000));
    }
}