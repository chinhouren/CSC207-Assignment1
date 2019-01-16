package fall2018.csc2017.gamecentre.gridfiller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A Tetromino in Grid Filler game.
 */
public class Tetromino implements Serializable {

    /**
     * Map of tetrominoes with the key being the name of the tetromino and the value being its
     * position relative to the anchor which is at index 0 on a 10x10 board.
     */
    public static final Map<String, int[]> tetrominoMap = new HashMap<String, int[]>() {
        {
            put("i", new int[]{0, 10, 20, 30});
            put("s", new int[]{0, 1, 9, 10});
            put("z", new int[]{0, 1, 11, 12});
            put("o", new int[]{0, 1, 10, 11});
            put("p", new int[]{0, 1, 10, 20});
            put("q", new int[]{0, 1, 11, 21});
            put("t", new int[]{0, 1, 2, 11});
        }
    };

    /**
     * The shape of this tetromino
     */
    private String shape;

    /**
     * Assign the shape of the tetromino randomly from the pre-defined set
     */
    Tetromino() {
        List<String> tempList = new ArrayList<>(tetrominoMap.keySet());
        shape = tempList.get(new Random().nextInt(tempList.size()));
    }


    /**
     * Constructor with a specific shape
     * Precondition: shapeName is one of the keys of tetrominoMap
     *
     * @param shapeName shape of this tetromino
     */
    public Tetromino(String shapeName) {
        shape = shapeName;
    }

    /**
     * Returns the shape of this tetromino
     *
     * @return the shape of this tetromino
     */
    String getShape() {
        return shape;
    }

}
