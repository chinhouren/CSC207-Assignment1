package fall2018.csc2017.gamecentre.gridfiller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fall2018.csc2017.gamecentre.BoardManager;
import fall2018.csc2017.gamecentre.GameAppCompatActivity;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.R;

public class GFPreStartingActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
     */
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gf_choosing);
        addSelectButtonListener();
    }

    /**
     * Activate the confirm button to select the size of the game board.
     */
    private void addSelectButtonListener() {
        Button selectButton = findViewById(R.id.SelectButton);
        selectButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int boardSize = 10;
                EditText tempUndo = findViewById(R.id.undo);
                String und = tempUndo.getText().toString();
                if (und.length() == 0) {
                    boardManager = new GFManager(boardSize, 0);
                    ((GFManager) boardManager).setInfiniteUndo();// default case: a player can undo infinitely
                } else {
                    boardManager = new GFManager(boardSize, Integer.parseInt(und));
                }
                save();
                switchToActivity(GFGameActivity.class);
            }
        }));
    }

    /**
     * Save the current game state.
     */
    private void save() {
        GameCentre.getInstance(GFPreStartingActivity.this).saveGame(GFPreStartingActivity.this, boardManager, true);
        GameCentre.getInstance(GFPreStartingActivity.this).saveGame(GFPreStartingActivity.this, boardManager, false);
    }
}
