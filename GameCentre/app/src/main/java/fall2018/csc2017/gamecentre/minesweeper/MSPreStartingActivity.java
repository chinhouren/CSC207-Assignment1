package fall2018.csc2017.gamecentre.minesweeper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.gamecentre.*;

public class MSPreStartingActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
     */
    private BoardManager boardManager;

    /**
     * A reference to the GameCentre singleton instance.
     */
    private GameCentre currentCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        setContentView(R.layout.activity_ms_prestarting);
        addReturnButtonListener();
        addEasyButtonListener();
        addNormalButtonListener();
        addHardButtonListener();
    }

    /**
     * Get back to the previous page
     */
    private void addReturnButtonListener() {
        Button backToMenuButton = findViewById(R.id.back);
        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(StartingActivity.class);
            }
        });
    }

    /**
     * Set difficulty as easy
     */
    private void addEasyButtonListener() {
        Button easyButton = findViewById(R.id.first);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMSManager("easy");
                save();
                switchToGame(currentCentre.getCurrentGame());
            }
        });
    }


    /**
     * Set difficulty as normal
     */
    private void addNormalButtonListener() {
        Button normalButton = findViewById(R.id.second);
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMSManager("normal");
                save();
                switchToGame(currentCentre.getCurrentGame());
            }
        });
    }


    /**
     * Set difficulty as hard
     */
    private void addHardButtonListener() {
        Button hardButton = findViewById(R.id.third);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMSManager("difficult");
                save();
                switchToGame(currentCentre.getCurrentGame());
            }
        });
    }


    /**
     * Create a BoardManager for the game selected
     *
     * @param difficulty the difficulty of the board being played
     */
    private void createMSManager(String difficulty) {
        switch (difficulty) {
            case "easy":
                boardManager = new MSManager(6, 8);
                break;
            case "normal":
                boardManager = new MSManager(8, 10);
                break;
            default:
                boardManager = new MSManager(10, 12);
                break;
        }
        ((MSBoard) boardManager.getBoard()).createMines();
    }

    /**
     * Save the current game state.
     */
    private void save() {
        GameCentre.getInstance(MSPreStartingActivity.this).saveGame(MSPreStartingActivity.this, boardManager, true);
        GameCentre.getInstance(MSPreStartingActivity.this).saveGame(MSPreStartingActivity.this, boardManager, false);
    }

}
