package persistence;

import exceptions.ShowCannotBeFoundException;
import model.ShowList;
import model.TVShow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    private static final String TEST_FILE = "data/testList";
    private Writer testWriter;
    private TVShow theSimpsons;
    private TVShow pokemon;
    private ShowList showList;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        theSimpsons = new TVShow("the Simpsons", "sitcom");
        pokemon = new TVShow("Pokemon", "anime");
        showList = new ShowList();
        showList.addShow(theSimpsons);
        showList.addShow(pokemon);
    }

    @Test
    void testWriteShowsAddTwoShows() {
        // save TV shows to file
        testWriter.write(showList);
        testWriter.close();

        // now read them back in and verify that the shows have the expected value
        try {
            ShowList showList = Reader.readShows(new File(TEST_FILE));
            assertEquals("the Simpsons", showList.myList.get(0).getName());
            assertEquals("sitcom", showList.myList.get(0).getCategory());
            assertFalse(showList.myList.get(0).isWatched());
            assertEquals("Pokemon", showList.myList.get(1).getName());
            assertEquals("anime", showList.myList.get(1).getCategory());
            assertFalse(showList.myList.get(1).isWatched());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriteShowsAddMarkAsWatched() {
        // save TV shows to file
        showList.markShowAsWatched("Pokemon");
        testWriter.write(showList);
        testWriter.close();

        // now read them back in and verify that the shows have the expected value
        try {
            ShowList showList = Reader.readShows(new File(TEST_FILE));
            assertEquals("the Simpsons", showList.myList.get(0).getName());
            assertEquals("sitcom", showList.myList.get(0).getCategory());
            assertFalse(showList.myList.get(0).isWatched());
            assertEquals("Pokemon", showList.myList.get(1).getName());
            assertEquals("anime", showList.myList.get(1).getCategory());
            assertTrue(showList.myList.get(1).isWatched());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriteShowsRemoveShow() {
        // save TV shows to file
        try {
            showList.removeShow("Pokemon");
        } catch (ShowCannotBeFoundException e) {
            // nothing happens;
        }
        testWriter.write(showList);
        testWriter.close();

        // now read them back in and verify that the shows have the expected value
        try {
            ShowList showList = Reader.readShows(new File(TEST_FILE));
            assertEquals("the Simpsons", showList.myList.get(0).getName());
            assertEquals("sitcom", showList.myList.get(0).getCategory());
            assertFalse(showList.myList.get(0).isWatched());
            assertEquals(1, showList.getSize());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
