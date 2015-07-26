package in.gotech.intelligation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by anirudh on 20/07/15.
 */
public class Login extends AppCompatActivity {
    public static final String PREFS_NAME = "Credentials";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void signUp(View v) {
        Log.i("Login", "Sign up button clicked");
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

    public void login(View v) {
        SharedPreferences credentialsSharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor credentialsEditor = credentialsSharedPref.edit();

        EditText usernameEditText = (EditText) findViewById(R.id.aadhaar_id_edit_text);
        EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        credentialsEditor.putString("username", usernameEditText.getText() + "");
        credentialsEditor.putString("password", passwordEditText.getText() + "");

        credentialsEditor.commit();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
