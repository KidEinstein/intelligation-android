package in.gotech.intelligation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by anirudh on 20/07/15.
 */
public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void signUp(View v) {
        Log.i("Login", "Sign up button clicked");
        Intent i = new Intent(Login.this, SignUp.class);
        startActivity(i);
    }

    public void login(View v) {
        Intent i = new Intent(Login.this, MainActivity.class);
        startActivity(i);
    }
}
