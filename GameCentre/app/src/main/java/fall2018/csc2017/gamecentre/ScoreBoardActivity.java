package fall2018.csc2017.gamecentre;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The activity in scoreboard.
 */
public class ScoreBoardActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
     */
    public static GameCentre currentCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        setContentView(R.layout.activity_scoreboard_);
        addBackButtonListener();
        String identifier = this.getIntent().getStringExtra("identifier");
        String title = this.getIntent().getStringExtra("title");
        int[] scoreboard = currentCentre.loadScoreboard(ScoreBoardActivity.this, identifier);
        TextView titleView = findViewById(R.id.ScoreTitle);
        TextView firstScore = findViewById(R.id.FirstScore);
        TextView secondScore = findViewById(R.id.SecondScore);
        TextView thirdScore = findViewById(R.id.ThirdScore);
        TextView currentScore = findViewById(R.id.WinningScore);

        titleView.setText(String.format("%s HIGHSCORE", title));
        firstScore.setText(checkEmptyScore(scoreboard[0]));
        secondScore.setText(checkEmptyScore(scoreboard[1]));
        thirdScore.setText(checkEmptyScore(scoreboard[2]));


        int yourScore = this.getIntent().getIntExtra("current", 0);
        currentScore.setText(yourScore == 0 ? "0    " : String.format("Your Score: %s", checkEmptyScore(yourScore)));
    }

    /**
     * Check if the score is 0 and returns the string value of score.
     *
     * @param score the integer value of the score
     * @return the string value of score if score is not zero, else return the empty string
     */
    private String checkEmptyScore(int score) {
        if (score == 0) {
            return "";
        }
        return String.valueOf(score);
    }

    /**
     * Activate the Back button so it returns to starting menu.
     */
    private void addBackButtonListener() {
        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(StartingActivity.class);
            }
        });
    }
}