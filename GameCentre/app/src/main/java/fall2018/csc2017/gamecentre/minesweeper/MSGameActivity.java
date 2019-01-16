package fall2018.csc2017.gamecentre.minesweeper;

import android.os.Bundle;
import android.widget.Toast;

import fall2018.csc2017.gamecentre.GameActivity;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.R;

import java.util.Observable;

public class MSGameActivity extends GameActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        boardmanager = currentCentre.loadGame(this, true);
        createTileButtons(this);
        setContentView(R.layout.activity_ms_main);

        setupGridView();
        ((MSManager) boardmanager).activateTimer();
        addSaveButtonListener();
        addReturnButtonListener();
    }

    /**
     * Display that a game has ended if a bomb is triggered.
     */
    private void makeToastLoseText() {
        Toast.makeText(this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (((MSManager) boardmanager).gameOverCheck()) {
            currentCentre.clearSavedGame(MSGameActivity.this, false);
            makeToastLoseText();
        } else if (boardmanager.puzzleSolved()) {
            currentCentre.clearSavedGame(MSGameActivity.this, false);
            String size = String.valueOf(boardmanager.getBoard().getBoardWidth());
            String title;
            switch (size) {
                case "10":
                    title = getString(R.string.hard);
                    break;
                case "8":
                    title = getString(R.string.normal);
                    break;
                default:
                    title = getString(R.string.easy);
                    break;
            }
            if (currentCentre.addScore(this, size, boardmanager.getScore(), true)) {
                Toast.makeText(this, "You got a high score!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
            }
            switchToScoreBoard(size, title, boardmanager.getScore());
        } else {
            autoSave();
        }
        display(this);
    }
}