package fall2018.csc2017.gamecentre.minesweeper;

import fall2018.csc2017.gamecentre.Board;
import fall2018.csc2017.gamecentre.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class representing a MineSweeper Board.
 */
public class MSBoard extends Board implements Serializable, Iterable<Tile> {

    /**
     * The board size.
     */
    private int boardSize;

    /**
     * The number of mines on the board.
     */
    private int totalMines;

    /**
     * The locations of the mines on the board.
     */
    public ArrayList<Integer> mineLocations;

    /**
     * Constructor for a Minesweeper Board.
     *
     * @param tiles  list of tiles
     * @param width  width of the board/number of columns
     * @param height height of the board/number of rows
     */
    public MSBoard(List<MSTile> tiles, int width, int height) {
        super(tiles, width, height);
        boardSize = width * height;
        totalMines = ((width * height + 6) / 7);
    }

    /**
     * Return the board size.
     *
     * @return the size of the board
     */
    private int getBoardSize() {
        return boardSize;
    }

    /**
     * Return the total number of mines.
     *
     * @return the total number of mines
     */
    public int getTotalMines() {
        return totalMines;
    }

    /**
     * Generate the locations of the mines.
     */
    public void createMines() {
        int mines = totalMines;
        ArrayList<Integer> locationList = new ArrayList<>();
        Random r = new Random();
        while (mines > 0) {
            int location = r.nextInt(boardSize);
            ((MSTile) getTile(location)).setMine();
            locationList.add(location);
            mineLocations = locationList;
            mines--;
        }
    }


    /**
     * Count the number of mines in the adjacent positions of position.
     *
     * @param position the position that we want to look at the neighbours of
     * @return the number of mines adjacent to position
     */
    private int countMines(int position) {
        if (!cornerCheck(position).isEmpty()) {
            return cornerCount(cornerCheck(position));
        } else if (!borderCheck(position).isEmpty()) {
            return borderCount(borderCheck(position), position);
        }
        return standardCount(position);

    }

    /**
     * Check if the given position is a corner tile.
     *
     * @return the corner that the tile belongs to, if it is a corner tile.
     */
    private String cornerCheck(int position) {
        if (position == 0) {
            return "top left";
        } else if (position == (getBoardWidth() - 1)) {
            return "top right";
        } else if (position == getBoardWidth() * (getBoardHeight() - 1)) {
            return "bottom left";
        } else if (position == ((getBoardWidth() * getBoardHeight()) - 1)) {
            return "bottom right";
        }
        return "";
    }

    /**
     * Count the number of mines adjacent to a given corner.
     *
     * @param corner the corner that we need to count adjacent mines for
     * @return the number of mines adjacent to the given corner
     */
    private int cornerCount(String corner) {
        ArrayList<Integer> neighbours = new ArrayList<>();
        switch (corner) {
            case "top left":
                neighbours.add(1);
                neighbours.add(getBoardWidth());
                neighbours.add(getBoardWidth() + 1);
            case "top right":
                neighbours.add(getBoardWidth() - 2);
                neighbours.add(2 * getBoardWidth() - 2);
                neighbours.add(2 * getBoardWidth() - 1);
            case "bottom left":
                neighbours.add(getBoardWidth() * (getBoardHeight() - 2));
                neighbours.add(getBoardWidth() * (getBoardHeight() - 2) + 1);
                neighbours.add(getBoardWidth() * (getBoardHeight() - 1) + 1);
            case "bottom right":
                neighbours.add(getBoardWidth() * getBoardHeight() - 2);
                neighbours.add(getBoardWidth() * (getBoardHeight() - 1) - 1);
                neighbours.add(getBoardWidth() * (getBoardHeight() - 1) - 2);
        }
        int mines = 0;
        for (int i = 0; i < 3; i++) {
            if (mineLocations.contains(neighbours.get(i))) {
                mines++;
            }
        }
        return mines;


    }

    /**
     * Check if the given position is on the border of the board.
     *
     * @param position the given position to check
     * @return which border the position is on, or an empty string.
     */
    private String borderCheck(int position) {
        if (position % getBoardWidth() == 0) {
            return "left";
        } else if (position % getBoardWidth() == getBoardWidth() - 1) {
            return "right";
        } else if (position >= 0 && position < getBoardWidth()) {
            return "top";
        } else if (position >= getBoardWidth() * (getBoardHeight() - 1) &&
                position < getBoardHeight() * getBoardHeight()) {
            return "bottom";
        } else {
            return "";
        }
    }

    /**
     * Count the number of mines adjacent to this border position.
     *
     * @param side     which border the position is on
     * @param position the position to count mines for
     * @return the number of mines adjacent to the position given
     */
    private int borderCount(String side, int position) {
        ArrayList<Integer> neighbours = new ArrayList<>();
        switch (side) {
            case "left":
                neighbours.add(position - getBoardWidth());
                neighbours.add(position - getBoardWidth() + 1);
                neighbours.add(position + 1);
                neighbours.add(position + getBoardWidth());
                neighbours.add(position + getBoardWidth() + 1);
            case "right":
                neighbours.add(position - getBoardWidth());
                neighbours.add(position - getBoardWidth() - 1);
                neighbours.add(position - 1);
                neighbours.add(position + getBoardWidth() - 1);
                neighbours.add(position + getBoardWidth());
            case "top":
                neighbours.add(position - 1);
                neighbours.add(position + 1);
                neighbours.add(position + getBoardWidth() - 1);
                neighbours.add(position + getBoardWidth());
                neighbours.add(position + getBoardWidth() + 1);
            case "bottom":
                neighbours.add(position - 1);
                neighbours.add(position - getBoardWidth() - 1);
                neighbours.add(position - getBoardWidth());
                neighbours.add(position - getBoardWidth() + 1);
                neighbours.add(position + 1);
        }
        int mines = 0;
        for (int i = 0; i < 5; i++) {
            if (mineLocations.contains(neighbours.get(i))) {
                mines++;
            }
        }
        return mines;

    }

    /**
     * Counting the number of adjacent mines for a tile that is not a border or corner.
     *
     * @param position the position to count adjacent mines for
     * @return the number of adjacent mines
     */
    private int standardCount(int position) {
        ArrayList<Integer> neighbours = new ArrayList<>();
        neighbours.add(position - 1);
        neighbours.add(position - getBoardWidth());
        neighbours.add(position - getBoardWidth() + 1);
        neighbours.add(position - getBoardWidth() - 1);
        neighbours.add(position + 1);
        neighbours.add(position + getBoardWidth());
        neighbours.add(position + getBoardWidth() + 1);
        neighbours.add(position + getBoardWidth() - 1);
        int mines = 0;
        for (int i = 0; i < 8; i++) {
            if (mineLocations.contains(neighbours.get(i))) {
                mines++;
            }
        }
        return mines;
    }


    /**
     * Set all tiles to be revealed.
     */
    public void revealAll() {
        for (int i = 0; i != boardSize; i++) {
            ((MSTile) getTile(i)).setRevealed();
        }
    }


    /**
     * Flag or un-flag a tile.
     *
     * @param position the position of the tile to be flagged
     */
    void flagTile(int position) {
        MSTile tile = ((MSTile) getTile(position));
        if (tile.isFlagged()) {
            tile.unFlag();
        } else {
            tile.setFlagged();
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Return the id of each tile
     *
     * @param index the index in the array containing tiles
     * @return id of the tile
     */
    String getID(int index) {
        if (!(((MSTile) getTile(index)).isRevealed()) && !(((MSTile) getTile(index)).isFlagged())) {
            return "ms_default";
        } else if (!(((MSTile) getTile(index)).isRevealed()) && (((MSTile) getTile(index)).isFlagged())) {
            return "ms_flagged";
        } else if ((((MSTile) getTile(index)).hasAMine())) {
            return "ms_bomb";
        } else if (countMines(index) != 0) {
            return "ms" + "_" + countMines(index);
        } else {
            return "ms_blank";
        }
    }

    /**
     * Processing revealing tiles after a tap.
     *
     * @param position location tapped
     */
    public void reveal(int position) {
        if (((MSTile) getTile(position)).hasAMine()) {
            revealAll();
            setChanged();
            notifyObservers();
        } else {
            recursiveReveal(position);
        }
    }

    /**
     * Processing the recursive process of revealing multiple tiles.
     *
     * @param position the position to reveal at
     */
    private void recursiveReveal(int position) {
        if (position < 0 || position >= getBoardSize()) {
            return;
        }
        MSTile currentTile = (MSTile) getTile(position);
        if (currentTile.isRevealed() || currentTile.hasAMine()) {
            return;
        }
        currentTile.setAdjacentMines(countMines(position));
        if (currentTile.getNumMines() > 0) {
            currentTile.setRevealed();
        } else {
            currentTile.setRevealed();
            if ((position + 1) % getBoardWidth() != 0) {
                recursiveReveal(position + 1);
            }
            if ((position - 1) % getBoardWidth() != (getBoardWidth() - 1)) {
                recursiveReveal(position - 1);
            }
            recursiveReveal(position + getBoardWidth());
            recursiveReveal(position - getBoardWidth());
        }
        setChanged();
        notifyObservers();
    }


}
