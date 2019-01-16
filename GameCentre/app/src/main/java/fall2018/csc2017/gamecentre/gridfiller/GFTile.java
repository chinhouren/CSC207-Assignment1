package fall2018.csc2017.gamecentre.gridfiller;

import fall2018.csc2017.gamecentre.Tile;

import java.io.Serializable;

public class GFTile extends Tile implements Serializable {

    private boolean placed;

    /**
     * A tile with its state (whether it is filled or not)
     *
     * @param placed true if the tile is filled
     */
    public GFTile(boolean placed) {
        super(placed ? 1 : 0);
        this.placed = placed;
    }

    /**
     * Changes the state of the tile from placed to not placed and vice versa
     */
    void placeTile(){
        placed = !placed;
        setBackgroundId(placed ? 1 : 0);
    }

    /**
     * Returns whether the tile is placed
     */
    public boolean isPlaced(){
        return placed;
    }
}
