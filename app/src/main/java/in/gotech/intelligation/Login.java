package in.gotech.intelligation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashSet;

/**
 * Created by anirudh on 20/07/15.
 */
public class Login extends AppCompatActivity {
    public static final String PREFS_NAME = "Credentials";

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginButton = (Button) findViewById(R.id.login_button);
    }

    public void signUp(View v) {
        Log.i("Login", "Sign up button clicked");
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

    public void login(View v) {
        loginButton.setEnabled(false);
        final EditText usernameEditText = (EditText) findViewById(R.id.aadhaar_id_edit_text);
        final EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        String url = getString(R.string.server_ip) + "/login?aadhaar_id=" + usernameEditText.getText() + "&password=" + passwordEditText.getText();
        JsonArrayRequest credentialVerificationRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        SharedPreferences credentialsSharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor credentialsEditor = credentialsSharedPref.edit();
                        credentialsEditor.putString("username", usernameEditText.getText() + "");
                        credentialsEditor.putString("password", passwordEditText.getText() + "");

                        HashSet<String> sensorIdSet = new HashSet<String>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                sensorIdSet.add(response.getJSONObject(i).getString("sensor_id"));
                            } catch (Exception e) {
                                Log.e("Login", "Error parsing sensor ids");
                            }
                        }
                        credentialsEditor.putStringSet("sensor_ids", sensorIdSet);

                        credentialsEditor.commit();

                        Intent i = new Intent(getApplicationContext(), NavDrawerActivity.class);
                        startActivity(i);
                        finish();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loginButton.setEnabled(true);
                        if (error.networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                Toast.makeText(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        if (error.networkResponse.statusCode == 401) {
                            Toast.makeText(getApplicationContext(), "Wrong Aadhaar ID or password", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(getApplicationContext(), "Network Error! Try again after some time", Toast.LENGTH_SHORT).show();


                    }
                }

        );

        credentialVerificationRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = VolleyApplication.getInstance().getRequestQueue();

        queue.add(credentialVerificationRequest);
    }
}
