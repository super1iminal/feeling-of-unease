package model.items;

import model.containers.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {
    Key key;

    @Test
    void testConstructor() {
        key = new Key("1", "ini1", "b1", "p1", "inv1",
                1234, new Container());
        assertEquals("1", key.getName());
        assertEquals(1234, key.getCode());
    }
}