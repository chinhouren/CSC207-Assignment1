package fall2018.csc2017.gamecentre;

import fall2018.csc2017.gamecentre.slidingtiles.STBoard;
import fall2018.csc2017.gamecentre.slidingtiles.STManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class STTest {

    /** The board manager for testing. */
    private STManager boardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int boardSize) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = boardSize * boardSize;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        int boardSize = 4;
        List<Tile> tiles = makeTiles(boardSize);
        STBoard board = new STBoard(tiles, boardSize);
        boardManager = new STManager(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertTrue(boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test whether checkSolvable correctly identifies solvable Boards
     */
    @Test
    public void testSolvable() {
        setUpCorrect();
        List<Tile> tiles = new ArrayList<>();
        for(Tile t : boardManager.getBoard()) {
            tiles.add(t);
        }
        assertTrue(boardManager.checkSolvable(4, tiles));
        tiles.add(13, tiles.remove(14));
        assertFalse(boardManager.checkSolvable(4, tiles));
    }

    /**
     * Test whether undoing all moves reverts the Board
     */
    @Test
    public void testUndo() {
        Random random = new Random();
        boardManager = new STManager(5, 3, "");
        List<Tile> tiles = new ArrayList<>();
        for(Tile t : boardManager.getBoard()) {
            tiles.add(t);
        }
        for(int i = 0; i < 3; i++){
            int next = 0;
            while (boardManager.findBlankNeighbour(next) == -1) {
                next = random.nextInt(25);
            }
            boardManager.touchMove(next);
        }
        for(int i = 0; i < 3; i++){
            boardManager.undo();
        }
        for(int i = 0; i < 25; i++) {
            if(boardManager.getBoard().getTile(i).getId() != tiles.get(i).getId()) {
                fail();
            }
        }
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0).getId());
        assertEquals(2, boardManager.getBoard().getTile(1).getId());
        boardManager.getBoard().swapTiles(0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0).getId());
        assertEquals(1, boardManager.getBoard().getTile(1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(14).getId());
        assertEquals(16, boardManager.getBoard().getTile(15).getId());
        boardManager.getBoard().swapTiles(15, 14);
        assertEquals(16, boardManager.getBoard().getTile(14).getId());
        assertEquals(15, boardManager.getBoard().getTile(15).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertTrue(boardManager.isValidTap(11));
        assertTrue(boardManager.isValidTap(14));
        assertFalse(boardManager.isValidTap(10));
    }
}

