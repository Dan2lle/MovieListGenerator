package persistence;

import model.ShowList;
import model.TVShow;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ReaderTest {
    @Test
    public void testParseShowsFile1() {
        try {
            ShowList showList = Reader.readShows(new File("data/testShowListFile1"));
            assertEquals("Criminal Minds", showList.myList.get(0).getName());
            assertEquals("crime", showList.myList.get(0).getCategory());
            assertFalse(showList.myList.get(0).isWatched());
            assertEquals("Goblin", showList.myList.get(1).getName());
            assertEquals("kdrama", showList.myList.get(1).getCategory());
            assertFalse(showList.myList.get(1).isWatched());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testParseShowsFile2() {
        try {
            ShowList showList = Reader.readShows(new File("data/testShowListFile2"));
            assertEquals("Beauty Inside", showList.myList.get(0).getName());
            assertEquals("romance", showList.myList.get(0).getCategory());
            assertFalse(showList.myList.get(0).isWatched());
            assertEquals("NCIS", showList.myList.get(1).getName());
            assertEquals("crime", showList.myList.get(1).getCategory());
            assertTrue(showList.myList.get(1).isWatched());
            assertEquals("Vampire Diary", showList.myList.get(2).getName());
            assertEquals("romance", showList.myList.get(2).getCategory());
            assertFalse(showList.myList.get(2).isWatched());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testIOException() {
        try {
            Reader.readShows(new File("./path/does/not/exist/testAccount.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
