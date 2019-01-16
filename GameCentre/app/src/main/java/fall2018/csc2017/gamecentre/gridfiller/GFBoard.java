
package fall2018.csc2017.gamecentre.gridfiller;

import fall2018.csc2017.gamecentre.Board;
import fall2018.csc2017.gamecentre.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The grid filler board.
 */
public class GFBoard extends Board implements Serializable, Iterable<Tile> {

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == BOARD_SIZE * BOARD_SIZE
     *
     * @param tiles the tiles for the board
     * @param size  the size for the board
     */
    public GFBoard(List<GFTile> tiles, int size) {
        super(tiles, size, size);
    }

    /**
     * Set the tile at positions in positionList to revealed and update the rows and columns if they
     * are filled
     *
     * @param positionList    list of tile positions to be revealed
     * @param currentPosition the position that is tapped
     * @return a list of tiles whose status changed at least once
     */
    public List<Integer> placeTiles(int[] positionList, int currentPosition) {
        List<Integer> moveList = new ArrayList<>();
        for (int i : positionList) {
            (getTile(currentPosition + i)).placeTile();
            moveList.add(currentPosition + i);
        }
        List<Integer> rowList = new ArrayList<>();
        List<Integer> colList = new ArrayList<>();
        for (int i : positionList) {
            int row = (currentPosition + i) / getBoardWidth();
            int col = (currentPosition + i) % getBoardWidth();
            checkCol(col, colList);
            checkRow(row, rowList);
        }
        clearAll(colList, rowList, moveList);
        setChanged();
        notifyObservers();
        return moveList;
    }

    /**
     * Switch the state of tiles in the given list of positions.
     *
     * @param positionList the list of position of tiles to be placed
     */
    void switchTiles(List<Integer> positionList) {
        for (int i : positionList) {
            (getTile(i)).placeTile();
        }
        setChanged();
        notifyObservers();
    }


    /**
     * Adds a given row to the list if the row is filled with placed tiles.
     *
     * @param row     the given row
     * @param rowList the list of rows that are filled with placed tiles
     */
    private void checkRow(int row, List<Integer> rowList) {
        boolean filled = true;
        int start = row * getBoardWidth();
        for (int i = 0; i < getBoardWidth(); i++) {
            if (!(getTile(i + start).isPlaced())) {
                filled = false;
            }
        }
        if (!rowList.contains(row) && filled) {
            rowList.add(row);
        }
    }

    /**
     * Adds a given column to the list if the column is filled with placed tiles.
     *
     * @param col     the given column
     * @param colList the list of columns that are filled with placed tiles
     */
    private void checkCol(int col, List<Integer> colList) {
        boolean filled = true;
        for (int i = 0; i < numTiles(); i += getBoardWidth()) {
            if (!(getTile(i + col).isPlaced())) {
                filled = false;
            }
        }
        if (!colList.contains(col) && filled) {
            colList.add(col);
        }
    }

    /**
     * Clears a given column of all placed tiles, ignores the tiles that were not placed.
     *
     * @param col      the given column
     * @param moveList the list of positions of tiles that are placed
     */
    private void clearCol(int col, List<Integer> moveList) {
        for (int i = 0; i < numTiles(); i += getBoardWidth()) {
            if (getTile(i + col).isPlaced()) {
                getTile(i + col).placeTile();
                moveList.add(i + col);
            }
        }
    }

    /**
     * Clears a given row of all placed tiles, ignoring the tiles that were not placed.
     *
     * @param row      the given row
     * @param moveList the list of positions of tiles that are placed
     */
    private void clearRow(int row, List<Integer> moveList) {
        int start = row * getBoardWidth();
        for (int i = 0; i < getBoardWidth(); i++) {
            if (getTile(i + start).isPlaced()) {
                getTile(i + start).placeTile();
                moveList.add(i + start);
            }
        }
    }

    /**
     * Clears all the rows and columns that are filled
     *
     * @param colList  list of columns that are filled
     * @param rowList  list of rows that are filled
     * @param moveList list of positions of tiles that are placed
     */
    private void clearAll(List<Integer> colList, List<Integer> rowList, List<Integer> moveList) {
        for (int col : colList) {
            clearCol(col, moveList);
        }
        for (int row : rowList) {
            clearRow(row, moveList);
        }
    }

    @Override
    public GFTile getTile(int index) {
        return (GFTile) super.getTile(index);
    }

}


