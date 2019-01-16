package fall2018.csc2017.gamecentre;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The activity in login page.
 */
public class LoginActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
     */
    public static GameCentre currentCentre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        setContentView(R.layout.activity_login_);
        addLoginButtonListener();
        addRegisterButtonListener();
        addGuestLoginButtonListener();
    }

    /**
     * Activate the Login button.
     */
    private void addLoginButtonListener() {
        Button loginButton = findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tempUser = findViewById(R.id.username);
                EditText tempPass = findViewById(R.id.password);
                String user = tempUser.getText().toString();
                String pass = tempPass.getText().toString();
                if (currentCentre.checkLogin(user, pass)) {
                    switchToActivity(SelectingGameActivity.class);
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    /**
     * Activate the Guest Login button.
     */
    private void addGuestLoginButtonListener() {
        Button guestLoginButton = findViewById(R.id.GuestLoginButton);
        guestLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(SelectingGameActivity.class);
            }
        });
    }

    /**
     * Activate the Register button.
     */
    private void addRegisterButtonListener() {
        Button registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(RegisterActivity.class);
            }
        });
    }
}
