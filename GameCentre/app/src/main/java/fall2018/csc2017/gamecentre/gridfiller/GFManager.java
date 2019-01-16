package fall2018.csc2017.gamecentre.gridfiller;

import fall2018.csc2017.gamecentre.BoardManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages of GFBoard and current and next tetrominos
 */
public class GFManager implements BoardManager, Serializable {

    /**
     * The board being managed.
     */
    private GFBoard board;

    /**
     * The number of undo's a player can have in a game.
     */
    private int numOfUndo;

    /**
     * The move count for the game.
     */
    private int score = 0;

    /**
     * The undo stack.
     */
    private ArrayList<List<Integer>> undoStack;

    /**
     * Whether or not the player has infinite undo's.
     */
    private boolean infiniteUndo;

    /**
     * The list of Tetrominos
     */
    private List<Tetromino> tetrominos;

    /**
     * The start and end of the list tetrominos
     */
    private int start, end;

    /**
     * A default BoardManager constructor.
     */
    public GFManager() {
    }

    /**
     * A constructor with a pre-populated GFBoard.
     */
    public GFManager(GFBoard board) {
        this.board = board;
        this.tetrominos = new ArrayList<>();
        this.undoStack = new ArrayList<>();
    }

    /**
     * Return the current board.
     *
     * @return the current board
     */
    public GFBoard getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    public GFManager(int size, int numOfUndo) {
        List<GFTile> tiles = new ArrayList<>();
        start = -3;
        end = 0;

        for (int tileNum = 0; tileNum != size * size; tileNum++) {
            tiles.add(new GFTile(false));
        }
        this.board = new GFBoard(tiles, size);
        this.numOfUndo = numOfUndo;
        this.undoStack = new ArrayList<>();
        this.tetrominos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            updateTetrominos();
        }
    }

    /**
     * Return the move count.
     *
     * @return the number of moves made
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public String getTileDrawable(int index) {
        return "gf_" + board.getTile(index).getId();
    }

    /**
     * Return the tetrominos currently visible
     *
     * @return three tetrominos currently visible
     */
    List<Tetromino> getTetrominos() {
        return tetrominos.subList(start, end);
    }

    /**
     * Return the length of undo stack.
     *
     * @return the size of the undo stack
     */
    int getStackLength() {
        return this.undoStack.size();
    }

    /**
     * Set infinite undo on.
     */
    void setInfiniteUndo() {
        this.infiniteUndo = true;
    }

    /**
     * Set the list of tetrominos and update start and end
     *
     * @param tetrominos a list of tetrominos
     */
    public void setTetrominos(List<Tetromino> tetrominos) {
        this.tetrominos = tetrominos;
        start = 0;
        end = tetrominos.size();
    }

    /**
     * Create a new tetromino and update the list of tetrominos
     */
    private void updateTetrominos() {
        if (end >= tetrominos.size()) {
            tetrominos.add(new Tetromino());
        }
        start++;
        end++;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        for (int i = 0; i != getBoard().numTiles(); i++) {
            if (isValidTap(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {
        int[] positionList = Tetromino.tetrominoMap.get(tetrominos.get(start).getShape());
        for (int num : positionList) {
            int tilePosition = num + position;
            if (tilePosition >= getBoard().numTiles()) {
                return false;
            }
            if (position % getBoard().getBoardWidth() >= 8 && tilePosition / getBoard().getBoardWidth() > (tilePosition - 1) / getBoard().getBoardWidth()) {
                return false;
            }
            if (num  == getBoard().getBoardWidth() - 1 && num == tilePosition % getBoard().getBoardWidth()) {
                return false;
            }
            if (getBoard().getTile(tilePosition).isPlaced()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position that was tapped
     */
    public void touchMove(int position) {
        int[] positionList = Tetromino.tetrominoMap.get(tetrominos.get(start).getShape());
        updateTetrominos();
        undoStack.add(board.placeTiles(positionList, position));
        if (undoStack.size() > numOfUndo && !infiniteUndo) {
            undoStack.remove(0);
        }
        score += 1;
    }


    /**
     * Process an undo, undoing the previous move made.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            int lastMoveIndex = undoStack.size() - 1;
            board.switchTiles(undoStack.get(lastMoveIndex));
            undoStack.remove(lastMoveIndex);
            start--;
            end--;
            score -= 1;
        }
    }
}
