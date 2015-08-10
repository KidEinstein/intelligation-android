package in.gotech.intelligation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONArray;

import java.util.HashSet;
import java.util.Set;

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

                        Intent i = new Intent(getApplicationContext(), NavDrawerAdapter.class);
                        startActivity(i);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                        } else if (error.networkResponse.statusCode == HttpStatus.SC_FORBIDDEN) {
                            Toast.makeText(getApplicationContext(), "Wrong aadhaar id", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(credentialVerificationRequest);
    }
}
