package fall2018.csc2017.gamecentre.gridfiller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.res.Resources;

import fall2018.csc2017.gamecentre.GameActivity;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.R;

import java.util.Observable;

public class GFGameActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        boardmanager = currentCentre.loadGame(this, true);
        createTileButtons(this);
        setContentView(R.layout.activity_gf_main);

        // Add View to activity
        setupGridView();
        addUndoButtonListener();
        addReturnButtonListener();
        updateTetromino();
    }

    /**
     * Update the images of current and next tetrominos
     */
    private void updateTetromino() {
        updateTetrominoHelper((ImageView) findViewById(R.id.CurrentTetromino), 0);
        updateTetrominoHelper((ImageView) findViewById(R.id.NextTetromino), 1);
        updateTetrominoHelper((ImageView) findViewById(R.id.NextTetromino2), 2);
    }

    /**
     * Set the corresponding image of the tetromino to ImageView
     *
     * @param tetro ImageView of tetromino
     * @param index 0 if CurrentTetromino, 1 if NextTetromino, and 2 if NextTetromino2
     */
    private void updateTetrominoHelper(@NonNull ImageView tetro, int index) {
        Resources res = this.getResources();
        tetro.setImageResource(res.getIdentifier(
                "gf_" + ((GFManager) boardmanager).getTetrominos().get(index).getShape(),
                "drawable", this.getPackageName()));
    }

    @Override
    protected void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((GFManager) boardmanager).getStackLength() == 0) {
                    Toast.makeText(GFGameActivity.this, "Undo failed", Toast.LENGTH_SHORT).show();
                } else {
                    ((GFManager) boardmanager).undo();
                    updateTetromino();
                }
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (boardmanager.puzzleSolved()) {
            currentCentre.clearSavedGame(GFGameActivity.this, false);
            String size = String.valueOf(boardmanager.getBoard().getBoardWidth());
            if (currentCentre.addScore(this, size, boardmanager.getScore() * 4, false)) {
                Toast.makeText(this, "You got a high score!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
            }
            switchToScoreBoard(size, "", boardmanager.getScore() * 4);
        } else {
            autoSave();

        }
        updateTetromino();
        display(this);
    }
}
