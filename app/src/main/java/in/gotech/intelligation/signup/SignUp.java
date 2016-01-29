package in.gotech.intelligation.signup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import in.gotech.intelligation.R;
import in.gotech.intelligation.VolleyApplication;

/**
 * Created by anirudh on 20/07/15.
 */
public class SignUp extends AppCompatActivity {
    EditText nameEditText;
    EditText mobileNumberEditText;
    EditText aadhaarIdEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        mobileNumberEditText = (EditText) findViewById(R.id.mobile_number_edit_text);
        aadhaarIdEditText = (EditText) findViewById(R.id.aadhaar_id_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);

    }

    public void submitSignUp(View v) {
        VolleyApplication.getInstance().getRequestQueue().add(getSignUpJsonObjectRequest());
    }

    private StringRequest getSignUpJsonObjectRequest() {
        String name = nameEditText.getText().toString();
        String mobileNumber = mobileNumberEditText.getText().toString();
        String aadhaarId = aadhaarIdEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String url = getString(R.string.server_ip) + "/sign_up?name=" + name + "&mobile_no=" + mobileNumber + "&aadhaar_id=" + aadhaarId + "&password=" + password;
        StringRequest signUpRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SignUp.this, R.string.signup_successfull_toast, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse.statusCode == 406) {
                            Toast.makeText(SignUp.this, R.string.signup_form_incomplete_toast, Toast.LENGTH_SHORT).show();
                        } else if (error.networkResponse.statusCode == 401) {
                            Toast.makeText(SignUp.this, R.string.sign_up_user_exists_toast, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );

        signUpRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return signUpRequest;

    }

}

