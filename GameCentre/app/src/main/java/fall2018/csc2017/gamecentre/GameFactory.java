package fall2018.csc2017.gamecentre;

import fall2018.csc2017.gamecentre.gridfiller.GFManager;
import fall2018.csc2017.gamecentre.minesweeper.MSManager;
import fall2018.csc2017.gamecentre.slidingtiles.STManager;

class GameFactory {

    /**
     * Returns an empty BoardManager for the current game.
     *
     * @param game the current game
     * @return a BoardManager instance
     */
    static BoardManager getManager(String game) {
        switch (game) {
            case "ST":
                return new STManager();
            case "MS":
                return new MSManager();
            case "GF":
                return new GFManager();
        }

        return null;
    }

    /**
     * Returns the full name of a game given its' identifier
     *
     * @param game the current game
     * @return the ID for the string resource
     */
    static int getNameID(String game) {
        switch (game) {
            case "ST":
                return R.string.slidingtiles;
            case "MS":
                return R.string.minesweeper;
            case "GF":
                return R.string.gridfiller;
        }

        return 0;
    }

    /**
     * Returns the instruction for the current game
     *
     * @param game the current game
     * @return the ID for the string resource
     */
    static int getInstructionsID(String game) {
        switch (game) {
            case "ST":
                return R.string.STinstructions;
            case "MS":
                return R.string.MSinstructions;
            case "GF":
                return R.string.GFinstructions;
        }

        return 0;
    }

}
