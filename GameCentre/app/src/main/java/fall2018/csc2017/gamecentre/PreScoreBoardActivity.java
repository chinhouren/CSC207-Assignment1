package fall2018.csc2017.gamecentre;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * The activity before opening scoreboard.
 */
public class PreScoreBoardActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
     */
    public static GameCentre currentCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        setContentView(R.layout.activity_prescoreboard_);
        Spinner mySpinner = findViewById(R.id.BoardSizeChoices);
        String[] choiceArray = getChoices(currentCentre.getCurrentGame());
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, choiceArray);
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        addConfirmButtonListener();

    }

    private String[] getChoices(String game) {
        if (game.equals("ST")) {
            return getResources().getStringArray(R.array.STboardsizes);
        } else if (game.equals("MS")) {
            return getResources().getStringArray(R.array.MSdifficulty);
        }

        return new String[]{};
    }

    /**
     * Activate the confirm button to select the scoreboard.
     */
    private void addConfirmButtonListener() {
        Button confirmButton = findViewById(R.id.ConfirmButton);
        confirmButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner mySpinner = findViewById(R.id.BoardSizeChoices);
                String item = mySpinner.getSelectedItem().toString();
                if (currentCentre.getCurrentGame().equals("ST")) {
                    String size = item.substring(0, 1);
                    switchToScoreBoard(size, String.format("%sx%s", size, size), 0);
                } else if (currentCentre.getCurrentGame().equals("MS")) {
                    String identifier = getResources().getStringArray(R.array.MSboardsizes)[mySpinner.getSelectedItemPosition()];
                    switchToScoreBoard(identifier, item, 0);
                }
            }
        }));
    }
}
