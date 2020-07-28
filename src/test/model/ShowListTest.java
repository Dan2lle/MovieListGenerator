package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests for the show list
class ShowListTest {
    private ShowList myList;
    private TVShow prisonBreak;
    private TVShow goblin;
    private TVShow hospitalPlaylist;
    private TVShow criminalMinds;
    private TVShow ncis;
    private TVShow vampireDiaries;
    private TVShow pokemon;
    private TVShow theSimpsons;

    @BeforeEach
    public void setUp() {
        myList = new ShowList();
        prisonBreak = new TVShow("Prison Break", "crime");
        goblin = new TVShow("Goblin", "kdrama");
        hospitalPlaylist = new TVShow("Hospital Playlist", "kdrama");
        criminalMinds = new TVShow("Criminal Minds", "crime");
        ncis = new TVShow("NCIS", "crime");
        vampireDiaries = new TVShow("Vampire Diaries", "romance");
        pokemon = new TVShow("Pokemon", "anime");
        theSimpsons = new TVShow(" The Simpsons", "sitcom");
    }




    @Test
    public void testAddShowEmptyList() {
        assertEquals(0, myList.getSize());
        assertTrue(myList.addShow(hospitalPlaylist));
        assertEquals(1, myList.getSize());
    }

    @Test
    public void testAddShowMoreShows() {
        assertEquals(0, myList.getSize());
        assertTrue(myList.addShow(criminalMinds));
        assertTrue(myList.addShow(pokemon));
        assertTrue(myList.addShow(prisonBreak));
        assertTrue(myList.addShow(vampireDiaries));
        assertEquals(4, myList.getSize());
    }

    @Test
    public void testRemoveShowIsThere() {
        assertEquals(0, myList.getSize());
        assertTrue(myList.addShow(criminalMinds));
        assertTrue(myList.addShow(pokemon));
        assertTrue(myList.addShow(prisonBreak));
        assertTrue(myList.addShow(vampireDiaries));
        assertEquals(4, myList.getSize());
        assertTrue(myList.removeShow("Pokemon"));
        assertEquals(3,myList.getSize());
    }

    @Test
    public void testRemoveShowNotThere() {
        assertEquals(0, myList.getSize());
        assertTrue(myList.addShow(criminalMinds));
        assertTrue(myList.addShow(pokemon));
        assertTrue(myList.addShow(prisonBreak));
        assertEquals(3, myList.getSize());
        assertFalse(myList.removeShow("Goblin"));
        assertEquals(3, myList.getSize());
    }

    @Test
    public void testShowByCategoryHasResult() {
        assertEquals(0, myList.getSize());
        assertTrue(myList.addShow(criminalMinds));
        assertTrue(myList.addShow(pokemon));
        assertTrue(myList.addShow(prisonBreak));
        assertTrue(myList.addShow(vampireDiaries));
        assertTrue(myList.addShow(ncis));
        assertEquals(3, myList.showByCategory("crime").getSize());
    }

    @Test
    public void testShowByCategoryNoResult() {
        assertTrue(myList.addShow(criminalMinds));
        assertTrue(myList.addShow(pokemon));
        assertTrue(myList.addShow(prisonBreak));
        assertTrue(myList.addShow(vampireDiaries));
        assertTrue(myList.addShow(ncis));
        assertTrue(myList.addShow(hospitalPlaylist));
        assertTrue(myList.addShow(goblin));
        assertEquals(0, myList.showByCategory("horror").getSize());
    }

    @Test
    public void testMarkWatched() {
        assertTrue(myList.addShow(criminalMinds));
        assertTrue(myList.addShow(pokemon));
        assertTrue(myList.addShow(prisonBreak));
        assertTrue(myList.markShowAsWatched("Pokemon"));
        assertTrue(pokemon.isWatched());
        assertFalse(criminalMinds.isWatched());
        assertFalse(prisonBreak.isWatched());
    }

    @Test
    public void testMarkWatchedNotThere() {
        assertTrue(myList.addShow(criminalMinds));
        assertTrue(myList.addShow(pokemon));
        assertTrue(myList.addShow(prisonBreak));
        assertFalse(myList.markShowAsWatched("NCIS"));
        assertFalse(pokemon.isWatched());
        assertFalse(criminalMinds.isWatched());
        assertFalse(prisonBreak.isWatched());
    }
}