package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TVShowTest {
    private TVShow goblin;
    private TVShow ncis;
    private TVShow pokemon;

    @BeforeEach
    public void setUp() {
        goblin = new TVShow("Goblin", "kdrama");
        ncis = new TVShow("NCIS", "crime");
        pokemon = new TVShow("Pokemon", "anime");
    }

    @Test
    public void testGetName() {
        assertEquals("Pokemon", pokemon.getName());
    }

    @Test
    public void testIsWatchedNotWatched() {
        assertFalse(goblin.isWatched());
    }

    @Test
    public void testIsWatchedWatched() {
        ncis.watch();
        assertTrue(ncis.isWatched());
    }
}
