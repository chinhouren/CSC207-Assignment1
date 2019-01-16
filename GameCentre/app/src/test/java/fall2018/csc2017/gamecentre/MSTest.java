package fall2018.csc2017.gamecentre;

import fall2018.csc2017.gamecentre.minesweeper.MSBoard;
import fall2018.csc2017.gamecentre.minesweeper.MSManager;
import fall2018.csc2017.gamecentre.minesweeper.MSTile;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class MSTest {

    /**
     * Create an empty MSBoard with size
     *
     * @param size the size of the board to be created
     * @return a new MSBoard
     */
    private MSBoard createEmptyBoard(int size) {
        List<MSTile> tiles = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            tiles.add(new MSTile(0));
        }
        MSBoard board = new MSBoard(tiles, size, size);
        board.mineLocations = new ArrayList<>();
        return board;
    }

    /**
     * Check if the given board is solved
     */
    @Test
    public void testRevealedAndSolvedEmptyBoard() {
        MSBoard board = createEmptyBoard(5);
        MSManager manager = new MSManager(board);
        board.reveal(0);
        for (Tile t : board) {
            assertTrue(((MSTile) t).isRevealed());
        }
        assertTrue(manager.puzzleSolved());
    }

    /**
     * Check whether puzzleSolved is working on a completely revealed board
     */
    @Test
    public void testRevealedAndSolvedBoard() {
        MSBoard board = createEmptyBoard(5);
        board.createMines();
        MSManager manager = new MSManager(board);
        board.revealAll();
        assertTrue(manager.puzzleSolved());
    }

    /**
     * Check whether a tap is valid on a revealed board
     */
    @Test
    public void testRevealedInvalidTap() {
        MSBoard board = createEmptyBoard(5);
        board.createMines();
        MSManager manager = new MSManager(board);
        board.revealAll();
        for (int i = 0; i < board.getBoardWidth() * board.getBoardHeight(); i++) {
            assertFalse(manager.isValidTap(i));
        }
    }

    /**
     * Check whether the mines on a board are calculated correctly
     */
    @Test
    public void testMinePopulation() {
        MSBoard board = createEmptyBoard(1);
        board.createMines();
        int mines = 0;
        for (Tile t : board) {
            if (((MSTile) t).hasAMine()) {
                mines++;
            }
        }
        assertEquals(mines, board.getTotalMines());
    }

    /**
     * Check whether the tap is valid on a random position of the board
     */
    @Test
    public void testInvalidTap() {
        Random random = new Random();
        MSBoard board = createEmptyBoard(5);
        MSManager manager = new MSManager(board);
        int pos = 0;
        while (((MSTile) board.getTile(pos)).hasAMine()) {
            pos = random.nextInt();
        }
        board.reveal(pos);
        assertFalse(manager.isValidTap(pos));

    }

    /**
     * Check whether the game is over on a MSBoard not received any action yet
     */
    @Test
    public void testgameOverCheck1() {
        MSBoard board = createEmptyBoard(2);
        MSManager manager = new MSManager(board);
        assertFalse(manager.gameOverCheck());
    }

    /**
     * Check whether the game is over on a solved MSBoard
     */
    @Test
    public void testgameOverCheck2() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(0)).setRevealed();
        assertTrue(manager.gameOverCheck());
    }

    /**
     * Check whether the puzzle is solved on a solved board
     */
    @Test
    public void testpuzzleSolved1() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(0)).setMine();
        ((MSTile) board.getTile(0)).setRevealed();
        assertTrue(manager.puzzleSolved());
    }

    /**
     * Check whether the puzzle is solved on a unsolved board
     */
    @Test
    public void testpuzzleSolved2() {
        MSBoard board = createEmptyBoard(2);
        MSManager manager = new MSManager(board);
        MSTile tile = new MSTile(0);
        MSTile tile1 = new MSTile(1);
        board.setTile(0, tile);
        board.setTile(1, tile1);
        assertFalse(manager.puzzleSolved());
    }

    /**
     * Check whether the flag has an effect on the number of mines on the board
     */
    @Test
    public void testFlag1() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(0)).setMine();
        manager.setRemainingMines(manager.getRemainingMines() + 1);
        manager.flag(0);
        assertEquals(0, manager.getRemainingMines());
    }

    /**
     * Check whether the flag has an effect on a tile of a board with a mine
     */
    @Test
    public void testFlag() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(0)).setMine();
        manager.setRemainingMines(manager.getRemainingMines() + 1);
        manager.flag(0);
        manager.flag(0);
        assertEquals(1, manager.getRemainingMines());
    }

    /**
     * Check whether a press is valid on a blank tile
     */

    @Test
    public void testisValidPress() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        assertTrue(manager.isValidPress(0));
    }

    /**
     * Check whether the press is valid on blank tile with a flag
     */
    @Test
    public void testisValidPress1() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(0)).setFlagged();
        assertTrue(manager.isValidPress(0));
    }

    /**
     * Check whether a press is valid on a revealed tile
     */
    @Test
    public void testisValidPress2() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(0)).setRevealed();
        assertFalse(manager.isValidPress(0));
    }

    /**
     * Check whether the touchMove is valid on a blank tile
     */
    @Test
    public void testtouchMove() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        manager.touchMove(0);
        assertTrue(((MSTile) board.getTile(0)).isRevealed());
    }

    /**
     * Check whether adddMine could add a mine on a board with size 1
     */
    @Test
    public void testaddMine() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        manager.addMine();
        assertEquals(1, manager.getRemainingMines());
    }

    /**
     * Check whether subtractMine on a board with size 1
     */
    @Test
    public void testsubtractMine() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        manager.addMine();
        manager.subtractMine();
        assertEquals(0, manager.getRemainingMines());
    }

    /**
     * Check whether a timer is working on a board with size 1
     */
    @Test
    public void testTimer() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        manager.activateTimer();
        assertEquals(0, manager.getTimer());
    }

    /**
     * Check whether the tileDrawable return the correct file name for a tile
     */
    @Test
    public void testTileDrawable() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        boolean result1 = manager.getTileDrawable(0).equals("ms_default");
        ((MSTile) board.getTile(0)).setFlagged();
        boolean result2 = manager.getTileDrawable(0).equals("ms_flagged");
        assertTrue(result1 && result2);
    }

    /**
     * Check whether the tileDrawable return the correct file name for a tile
     */
    @Test
    public void testTileDrawable2() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(0)).setMine();
        ((MSTile) board.getTile(0)).setRevealed();
        boolean result1 = manager.getTileDrawable(0).equals("ms_bomb");
        assertTrue(result1);
    }

    /**
     * Check whether the tileDrawable return the correct file name for a tile
     */
    @Test
    public void testTileDrawable3() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(0)).setRevealed();
        boolean result1 = manager.getTileDrawable(0).equals("ms_blank");
        assertTrue(result1);
    }

    /**
     * Check whether the tileDrawable return the correct file name for a tile
     */
    @Test
    public void testTileDrawable4() {
        MSBoard board = createEmptyBoard(4);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(0)).setRevealed();
        boolean result1 = manager.getTileDrawable(0).equals("ms_blank");
        assertTrue(result1);
    }

    /**
     * Check whether the tileDrawable return the correct file name for a tile
     */
    @Test
    public void testTileDrawable5() {
        List<MSTile> tile = new ArrayList<>(0);
        MSTile tile1 = new MSTile(0);
        MSTile tile2 = new MSTile(1);
        MSTile tile3 = new MSTile(2);
        MSTile tile4 = new MSTile(3);
        tile.add(tile1);
        tile.add(tile2);
        tile.add(tile3);
        tile.add(tile4);
        MSBoard board = new MSBoard(tile, 2, 2);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(1)).setMine();
        board.mineLocations = new ArrayList<>(0);
        board.mineLocations.add(1);
        ((MSTile) board.getTile(0)).setRevealed();
        boolean result1 = manager.getTileDrawable(0).equals("ms_1");
        assertTrue(result1);
    }

    /**
     * Check whether the constructor of MSManager with 2 arguments work
     */
    @Test
    public void testManager() {
        MSManager ms = new MSManager(1, 2);
        ms.setRemainingMines(0);
        assertEquals(0, ms.getRemainingMines());
    }

    /**
     * Check whether the constructor of MSManager that takes no argumants work
     */
    @Test
    public void testManager1() {
        MSManager ms = new MSManager();
        ms.setRemainingMines(0);
        assertEquals(0, ms.getRemainingMines());
    }

    /**
     * Check whether the getter for MSBoard and getScore are working
     */
    @Test
    public void testBoardandScore() {
        MSManager ms = new MSManager();
        int i = ms.getScore();
        boolean result1 = i == 0;
        Board b = ms.getBoard();
        boolean result2 = b == ms.getBoard();
        assertTrue(result1 && result2);
    }

    /**
     * Check whether the tap a valid on a board with size 1
     */
    @Test
    public void testisValidTap() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        assertTrue(manager.isValidTap(0));
    }

    /**
     * Check whether reveal is working on a board with a bombed tile
     */
    @Test
    public void testReveal() {
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile) board.getTile(0)).setMine();
        board.reveal(0);
        assertTrue(manager.gameOverCheck());
    }
}
