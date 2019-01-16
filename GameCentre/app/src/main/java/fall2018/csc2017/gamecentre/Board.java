package fall2018.csc2017.gamecentre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The board players interact with.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[] tiles;

    /**
     * The number of columns and row.
     */
    private int boardWidth, boardHeight;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == BOARD_SIZE * BOARD_SIZE
     *
     * @param tiles  the tiles for the board
     * @param width  the width for the board
     * @param height the height for the board
     */
    protected <T extends Tile> Board(List<T> tiles, int width, int height) {
        boardWidth = width;
        boardHeight = height;
        this.tiles = new Tile[boardWidth * boardHeight];
        Iterator<T> iter = tiles.iterator();

        for (int i = 0; i != boardWidth * boardHeight; i++) {
            this.tiles[i] = iter.next();
        }
    }

    /**
     * Return the tile at index index.
     *
     * @param index the index of the tile
     * @return the tile at the index
     */
    public Tile getTile(int index) {
        return tiles[index];
    }

    /**
     * Set the tile at index index.
     *
     * @param index the index of the tile to be set
     * @param tile  the tile to set at index index
     */
    protected void setTile(int index, Tile tile) {
        tiles[index] = tile;
    }

    /**
     * Return the width of the board.
     *
     * @return the size of the board.
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     * Return the height of the board.
     *
     * @return the size of the board.
     */
    public int getBoardHeight() {
        return boardHeight;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board.
     */
    public int numTiles() {
        return boardWidth * boardHeight;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }


    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    private class TileIterator implements Iterator<Tile> {

        // The index of the current tile to iterate on
        private int index = 0;

        @Override
        public boolean hasNext() {
            return (index < numTiles());
        }

        @Override
        public Tile next() {
            Tile returned = tiles[index];
            index++;
            return returned;
        }
    }

}