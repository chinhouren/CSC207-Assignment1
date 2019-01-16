package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.widget.Toast;

import fall2018.csc2017.gamecentre.minesweeper.MSManager;


class MovementController {

    private BoardManager boardManager = null;

    MovementController() {
    }

    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
        } else if (display){
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    void processPressMovement(Context context, int position, boolean display) {
        if (boardManager instanceof MSManager && ((MSManager) boardManager).isValidPress(position)) {
            ((MSManager) boardManager).flag(position);
            if (display) {
                Toast.makeText(context, "Flagged/Unflagged successfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            processTapMovement(context, position, display);
        }
    }
}
