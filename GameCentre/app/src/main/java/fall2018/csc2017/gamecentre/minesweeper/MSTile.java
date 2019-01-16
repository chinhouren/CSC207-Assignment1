package fall2018.csc2017.gamecentre.minesweeper;

import fall2018.csc2017.gamecentre.Tile;

/**
 * A class representing a Minesweeper Tile.
 */
public class MSTile extends Tile {

    /**
     * The number of mines adjacent to this tile, including diagonals.
     */
    private int numMines;
    /**
     * Whether this tile is revealed or not.
     */
    private boolean revealed;

    /**
     * Whether this tile is flagged or not.
     */
    private boolean flagged;

    /**
     * Whether this tile has a mine or not.
     */
    private boolean hasMine;

    /**
     * Constructor for a MineSweeper Tile, set default for an unrevealed Tile.
     *
     * @param backgroundId the unique id of this tile.
     */
    public MSTile(int backgroundId) {
        super(backgroundId);
        revealed = false;
        flagged = false;
        hasMine = false;
    }

    /**
     * Set this Tile to have a mine.
     */
    public void setMine() {
        hasMine = true;
    }

    /**
     * Return whether this tile has a mine.
     *
     * @return whether this tile has a mine.
     */
    public boolean hasAMine() {
        return this.hasMine;
    }

    /**
     * Set this tile to be revealed.
     */
    public void setRevealed() {
        revealed = true;
    }

    /**
     * Return whether this tile is revealed.
     *
     * @return whether this tile is revealed.
     */
    public boolean isRevealed() {
        return this.revealed;
    }

    /**
     * Return whether this tile is flagged.
     *
     * @return whether this tile is flagged.
     */
    public boolean isFlagged() {
        return this.flagged;
    }

    /**
     * Set this tile to be flagged.
     */
    public void setFlagged() {
        flagged = true;
    }

    /**
     * Un-flag this tile.
     */
    void unFlag() {
        flagged = false;
    }

    /**
     * Set the number of adjacent mines to this tile.
     *
     * @param mines the number of adjacent mines
     */
    void setAdjacentMines(int mines) {
        numMines = mines;
    }

    /**
     * Return the number of adjacent mines to this tile.
     *
     * @return the number of adjacent mines
     */
    int getNumMines() {
        return numMines;
    }

}
