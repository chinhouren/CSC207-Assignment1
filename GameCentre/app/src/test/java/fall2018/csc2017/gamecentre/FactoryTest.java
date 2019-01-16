package fall2018.csc2017.gamecentre;

import fall2018.csc2017.gamecentre.gridfiller.GFManager;
import fall2018.csc2017.gamecentre.minesweeper.MSManager;
import fall2018.csc2017.gamecentre.slidingtiles.STManager;

import org.junit.Test;

import static org.junit.Assert.*;

public class FactoryTest {

    @Test
    public void testGetManager() {
        BoardManager boardManager;
        boardManager = GameFactory.getManager("ST");
        assertTrue(boardManager instanceof STManager);
        boardManager = GameFactory.getManager("MS");
        assertTrue(boardManager instanceof MSManager);
        boardManager = GameFactory.getManager("GF");
        assertTrue(boardManager instanceof GFManager);
        boardManager = GameFactory.getManager("not a game");
        assertNull(boardManager);
    }

    @Test
    public void testGetName() {
        int resID;
        resID = GameFactory.getNameID("ST");
        assertEquals(resID, R.string.slidingtiles);
        resID = GameFactory.getNameID("MS");
        assertEquals(resID, R.string.minesweeper);
        resID = GameFactory.getNameID("GF");
        assertEquals(resID, R.string.gridfiller);
        resID = GameFactory.getNameID("not a game");
        assertEquals(resID, 0);
    }

    @Test
    public void testGetInstructions() {
        int resID;
        resID = GameFactory.getInstructionsID("ST");
        assertEquals(resID, R.string.STinstructions);
        resID = GameFactory.getInstructionsID("MS");
        assertEquals(resID, R.string.MSinstructions);
        resID = GameFactory.getInstructionsID("GF");
        assertEquals(resID, R.string.GFinstructions);
        resID = GameFactory.getInstructionsID("not a game");
        assertEquals(resID, 0);
    }

}
