package fall2018.csc2017.gamecentre;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The activity in choosing which game to play.
 */
public class SelectingGameActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
     */
    public static GameCentre currentCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        setContentView(R.layout.activity_selecting_game);
        addTilesButtonListener();
        addMineButtonListener();
        addGridButtonListener();
    }

    /**
     * Choose the game sliding tiles.
     */
    private void addTilesButtonListener() {
        Button TileButton = findViewById(R.id.SlidingTiles);
        TileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCentre.setCurrentGame("ST");
                switchToActivity(InstructionsActivity.class);
            }
        });
    }

    /**
     * Choose the minesweeper game.
     */
    private void addMineButtonListener() {
        Button TileButton = findViewById(R.id.Minesweeper);
        TileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCentre.setCurrentGame("MS");
                switchToActivity(InstructionsActivity.class);
            }
        });
    }

    /**
     * Choose the grid filler game.
     */
    private void addGridButtonListener() {
        Button TileButton = findViewById(R.id.GridFiller);
        TileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCentre.setCurrentGame("GF");
                switchToActivity(InstructionsActivity.class);
            }
        });
    }

}
