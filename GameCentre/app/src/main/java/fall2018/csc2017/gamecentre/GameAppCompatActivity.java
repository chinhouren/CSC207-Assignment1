package fall2018.csc2017.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import fall2018.csc2017.gamecentre.gridfiller.GFGameActivity;
import fall2018.csc2017.gamecentre.gridfiller.GFPreStartingActivity;
import fall2018.csc2017.gamecentre.minesweeper.MSGameActivity;
import fall2018.csc2017.gamecentre.minesweeper.MSPreStartingActivity;


public class GameAppCompatActivity extends AppCompatActivity {
    /**
     * Switch to the chosen Activity view.
     */
    protected void switchToActivity(Class activity) {
        Intent tmp = new Intent(this, activity);
        startActivity(tmp);
    }

    /**
     * Switch to the chosen GameActivity view.
     */
    protected void switchToGame(String game) {
        Class activity;
        switch (game) {
            case "ST":
                activity = GameActivity.class;
                break;
            case "GF":
                activity = GFGameActivity.class;
                break;
            case "MS":
                activity = MSGameActivity.class;
                break;
            default:
                Toast.makeText(this, "Unable to start game", Toast.LENGTH_LONG).show();
                return;
        }
        Intent tmp = new Intent(this, activity);
        startActivity(tmp);
    }

    /**
     * Switch to the chosen PreStartingActivity view.
     */
    protected void switchToPreStarting(String game) {
        Class activity;
        switch (game) {
            case "ST":
                activity = PreStartingActivity.class;
                break;
            case "GF":
                activity = GFPreStartingActivity.class;
                break;
            case "MS":
                activity = MSPreStartingActivity.class;
                break;
            default:
                Toast.makeText(this, "Unable to start game", Toast.LENGTH_LONG).show();
                return;
        }
        Intent tmp = new Intent(this, activity);
        startActivity(tmp);
    }

    /**
     * Switch to the Score Board view.
     */
    protected void switchToScoreBoard(String identifier, String title, int current) {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        tmp.putExtra("identifier", identifier);
        tmp.putExtra("title", title);
        tmp.putExtra("current", current);
        startActivity(tmp);
    }

}
