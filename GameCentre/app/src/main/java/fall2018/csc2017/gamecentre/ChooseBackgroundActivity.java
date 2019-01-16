package fall2018.csc2017.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ChooseBackgroundActivity extends AppCompatActivity {

    /**
     * The name of the selected background image.
     */
    private String selected = "tile_";

    /**
     * The buttons to display.
     */
    private ArrayList<Button> backgrounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
        backgrounds = new ArrayList<>();
        addCloseButtonListener();
        createBackgroundButtons();
    }

    /**
     * Create the buttons for different backgrounds.
     */
    private void createBackgroundButtons() {
        addBackgroundButtonListener(R.id.tile_, "tile_");
        addBackgroundButtonListener(R.id.autumn_, "autumn_");
        addBackgroundButtonListener(R.id.flower_, "flower_");
    }


    /**
     * Add listener to the buttons for different backgrounds.
     */
    private void addBackgroundButtonListener(int id, final String name) {
        final Button backgroundButton = findViewById(id);
        backgrounds.add(backgroundButton);
        backgroundButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                selected = name;
                for (Button b : backgrounds) {
                    b.setPressed(false);
                }
                backgroundButton.setPressed(true);
                return true;
            }
        });
    }

    /**
     * Activate the close button.
     */
    private void addCloseButtonListener() {
        Button closeButton = findViewById(R.id.CloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPreStarting();
            }
        });
    }

    /**
     * Switch back to the PreStartingActivity view.
     */
    private void switchToPreStarting() {
        Intent tmp = new Intent(this, PreStartingActivity.class);
        tmp.putExtra("background", selected);
        startActivity(tmp);
    }

}
