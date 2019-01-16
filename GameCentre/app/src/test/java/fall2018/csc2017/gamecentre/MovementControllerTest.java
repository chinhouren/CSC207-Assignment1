package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.gamecentre.gridfiller.GFManager;
import fall2018.csc2017.gamecentre.gridfiller.GFTile;
import fall2018.csc2017.gamecentre.minesweeper.MSBoard;
import fall2018.csc2017.gamecentre.minesweeper.MSManager;
import fall2018.csc2017.gamecentre.minesweeper.MSTile;
import fall2018.csc2017.gamecentre.slidingtiles.STBoard;
import fall2018.csc2017.gamecentre.slidingtiles.STManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test if methods in MovermentController work properly
 */
public class MovementControllerTest {
    /**
     * Context needed to test MovermentController
     */
    private Context con;

    /**
     * Instance of MovermentController
     */
    private MovementController mc;

    /**
     * Class to get Context
     */
    private static class TempActivity extends GameAppCompatActivity {
        private static Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            context = getContext();
        }

        /**
         * Help MovementControllerTest get Context
         *
         * @return Context of TempActivity
         */
        static Context getContext() {
            return TempActivity.context;
        }

    }

    /**
     * Make a solved STBoard and set it to STManager.
     *
     * @return STManager with a solved Board
     */
    private STManager setUpCorrect() {
        int boardSize = 4;
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum < boardSize * boardSize; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        STBoard board = new STBoard(tiles, boardSize);
        return new STManager(board);
    }

    /**
     * Make a MSBoard with no mine and set it to MSManager.
     *
     * @param size size of MSBoard
     * @return MSManager with MSBoard with no mine
     */
    private MSManager createEmptyBoard(int size) {
        List<MSTile> tiles = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            tiles.add(new MSTile(0));
        }
        MSBoard board = new MSBoard(tiles, size, size);
        board.mineLocations = new ArrayList<>();
        return new MSManager(board);
    }

    /**
     * Test processTapMovement for grid filler
     */
    @Test
    public void testProcessTapMovementGF() {
        GFManager manager = new GFManager(10, 3);
        mc.setBoardManager(manager);
        mc.processTapMovement(con, 43, false);
        assertTrue(manager.getBoard().getTile(43).isPlaced());
    }

    /**
     * Test processTapMovement for minesweeper
     */
    @Test
    public void testProcessTapMovementMS() {
        MSManager manager = createEmptyBoard(6);
        mc.setBoardManager(manager);
        mc.processTapMovement(con, 5, false);
        assertTrue(((MSTile) manager.getBoard().getTile(5)).isRevealed());

    }

    /**
     * Test processTapMovement for sliding tiles
     */
    @Test
    public void testProcessTapMovementST() {
        STManager manager = setUpCorrect();
        mc.setBoardManager(manager);
        mc.processTapMovement(con, 5, false);
        assertTrue(manager.puzzleSolved());
        mc.processTapMovement(con, 11, false);
        assertFalse(manager.puzzleSolved());
    }

    /**
     * Test processPressMovement for grid filler
     */
    @Test
    public void testProcessPressMovementGF() {
        GFManager manager = new GFManager(10, 3);
        mc.setBoardManager(manager);
        mc.processPressMovement(con, 22, false);
        assertTrue(manager.getBoard().getTile(22).isPlaced());
    }

    /**
     * Test processPressMovement for minesweeper
     */
    @Test
    public void testProcessPressMovementMS() {
        MSManager msManager = createEmptyBoard(6);
        mc.setBoardManager(msManager);
        mc.processPressMovement(con, 0, false);
        assertTrue(((MSTile) msManager.getBoard().getTile(0)).isFlagged());
    }

    /**
     * Test processPressMovement for sliding tiles
     */
    @Test
    public void testProcessPressMovementST() {
        STManager manager = setUpCorrect();
        mc.setBoardManager(manager);
        mc.processPressMovement(con, 14, false);
        assertFalse(manager.puzzleSolved());
    }

    /**
     * Initialize MovementController and Context
     */
    @Before
    public void setUp() {
        mc = new MovementController();
        con = TempActivity.getContext();
    }
}
