package fall2018.csc2017.gamecentre;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public interface BoardManager {

    /**
     * Return whether the game has been completed.
     *
     * @return whether the game has been completed
     */
    boolean puzzleSolved();

    /**
     * Return the current board.
     *
     * @return the current board
     */
    Board getBoard();

    /**
     * Return whether tapping the given tile is a valid move
     *
     * @param position the tile to check
     * @return whether tapping the given tile is a valid move
     */
    boolean isValidTap(int position);

    /**
     * Return the score.
     *
     * @return the score (based on game played)
     */
    int getScore();

    /**
     * Return the drawable for the tile at the given index
     *
     * @param index the index of the tile
     * @return the name of the drawable for the tile
     */
    String getTileDrawable(int index);

    /**
     * Process a touch at position in the board
     *
     * @param position the position that was tapped
     */
    void touchMove(int position);
}
