package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;
import android.content.res.Resources;

import fall2018.csc2017.gamecentre.slidingtiles.STManager;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class GameActivity extends GameAppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    protected BoardManager boardmanager;

    /**
     * A reference to the GameCentre singleton instance.
     */
    protected GameCentre currentCentre;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    protected GestureDetectGridView gridView;
    protected static int columnWidth;
    protected static int columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    protected void display(Context context) {
        updateTileButtons(context);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        boardmanager = currentCentre.loadGame(this, true);
        createTileButtons(this);
        setContentView(R.layout.activity_main);

        setupGridView();
        addSaveButtonListener();
        addUndoButtonListener();
        addReturnButtonListener();
    }

    protected void setupGridView() {
        final Context con = this;

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardmanager.getBoard().getBoardWidth());
        gridView.setBoardManager(boardmanager);
        boardmanager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardmanager.getBoard().getBoardWidth();
                        columnHeight = displayHeight / boardmanager.getBoard().getBoardHeight();

                        display(con);
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context used to create tile buttons
     */
    protected void createTileButtons(Context context) {
        Board board = boardmanager.getBoard();
        tileButtons = new ArrayList<>();
        Resources res = context.getResources();
        for (int index = 0; index != board.numTiles(); index++) {
            Button tmp = new Button(context);
            tmp.setBackground(res.getDrawable(res.getIdentifier(
                    boardmanager.getTileDrawable(index),
                    "drawable", context.getPackageName()), null));
            this.tileButtons.add(tmp);
        }
    }

    /**
     * Activate the undo button.
     */
    protected void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((STManager) boardmanager).getStackLength() == 0) {
                    Toast.makeText(GameActivity.this, "Undo failed", Toast.LENGTH_SHORT).show();
                } else {
                    ((STManager) boardmanager).undo();
                }
            }
        });
    }

    /**
     * Activate the save button.
     */
    protected void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCentre.saveGame(GameActivity.this, (boardmanager), false);
                currentCentre.saveGame(GameActivity.this, (boardmanager), true);
                makeToastSavedText();
            }
        });
    }

    /**
     * Activate the return button.
     */
    protected void addReturnButtonListener() {
        Button returnButton = findViewById(R.id.ReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCentre.saveGame(GameActivity.this, (boardmanager), false);
                currentCentre.saveGame(GameActivity.this, (boardmanager), true);
                switchToActivity(StartingActivity.class);
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was auto-saved successfully.
     */
    private void makeToastAutoSavedText() {
        Toast.makeText(this, "Game Auto-Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons(Context context) {
        Board board = boardmanager.getBoard();
        Resources res = context.getResources();
        int nextPos = 0;
        for (Button b : tileButtons) {
            b.setBackground(res.getDrawable(res.getIdentifier(
                    boardmanager.getTileDrawable(nextPos),
                    "drawable", context.getPackageName()), null));
            nextPos++;
        }
    }

    /**
     * Auto-save the game after a certain amount of moves.
     */
    protected void autoSave() {
        if (boardmanager.getScore() % 4 == 0) {
            currentCentre.saveGame(GameActivity.this, (boardmanager), true);
            makeToastAutoSavedText();
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        currentCentre.saveGame(this, boardmanager, true);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (boardmanager.puzzleSolved()) {
            currentCentre.clearSavedGame(GameActivity.this, false);
            String size = String.valueOf(boardmanager.getBoard().getBoardWidth());
            if (currentCentre.addScore(this, size, boardmanager.getScore(), true)) {
                Toast.makeText(this, "You got a high score!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
            }
            switchToScoreBoard(size, String.format("%sx%s", size, size), boardmanager.getScore());
        } else {
            autoSave();
        }
        display(this);
    }
}
