package fall2018.csc2017.gamecentre;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The activity in registering a new account.
 */
public class RegisterActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
     */
    public static GameCentre currentCentre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        setContentView(R.layout.activity_register_);
        addHomeButtonListener();
        addSignupButtonListener();
    }


    /**
     * Activate the Signup button.
     */
    private void addSignupButtonListener() {
        Button signupButton = findViewById(R.id.SignupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tempUser = findViewById(R.id.SignupName);
                EditText tempPass = findViewById(R.id.SignupPass);
                String user = tempUser.getText().toString();
                String pass = tempPass.getText().toString();
                if (currentCentre.addUser(RegisterActivity.this, user, pass)) {
                    Toast.makeText(RegisterActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                    switchToActivity(SelectingGameActivity.class);
                } else {
                    Toast.makeText(RegisterActivity.this, "Username already in use or invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Activate the Home button.
     */
    private void addHomeButtonListener() {
        Button homeButton = findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(LoginActivity.class);

            }
        });
    }
}
