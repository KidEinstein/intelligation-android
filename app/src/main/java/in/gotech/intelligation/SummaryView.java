package in.gotech.intelligation;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

/**
 * Created by anirudh on 27/07/15.
 */
public class SummaryView extends android.support.v7.widget.CardView {
    private TextView mSensorValueTextView;
    private TextView mCropNameTextView;
    private ToggleButton mAutoToggleButton;
    private Switch mMotorSwitch;
    private Button mRefreshButton;
    private StringRequest mAutoEnableRequest;
    private StringRequest mAutoDisableRequest;
    private StringRequest mMotorEnableRequest;
    private StringRequest mMotorDisableRequest;
    private JsonObjectRequest mRefreshRequest;


    public SummaryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.summary_card_children, this, true);
        setupChildren();
    }

    public SummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.summary_card_children, this, true);
        setupChildren();

    }

    public static SummaryView inflate(ViewGroup parent) {
        SummaryView summaryView = (SummaryView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_card, parent, false);
        return summaryView;
    }

    private void setupChildren() {
        mSensorValueTextView = (TextView) findViewById(R.id.sensor_value_text_view);
        mCropNameTextView = (TextView) findViewById(R.id.crop_name_text_view);
        mAutoToggleButton = (ToggleButton) findViewById(R.id.auto_toggle_button);
        mMotorSwitch = (Switch) findViewById(R.id.motor_switch);
        mRefreshButton = (Button) findViewById(R.id.refresh_button);
    }

    public void setItem(Sensor item) {
        mSensorValueTextView.setText(item.getSensorValue() + "");
        mCropNameTextView.setText(item.getCropName());
        mAutoToggleButton.setChecked(item.getAutoStatus());
        mMotorSwitch.setChecked(item.getMotorStatus());
        mMotorSwitch.setEnabled(!item.getAutoStatus());
        attachAutoListener();
        attachMotorListener();
        mAutoDisableRequest = getAutoStringRequest(item.sensorId, 0);
        mAutoEnableRequest = getAutoStringRequest(item.sensorId, 1);
        mMotorDisableRequest = getMotorStringRequest(item.sensorId, 0);
        mMotorEnableRequest = getMotorStringRequest(item.sensorId, 1);
        mRefreshRequest = getRefreshJsonObjectRequest(item.sensorId);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyApplication.getInstance().getRequestQueue().add(mRefreshRequest);
            }
        });
    }

    private JsonObjectRequest getRefreshJsonObjectRequest(int sensorId) {
        String url = getContext().getString(R.string.server_ip) + "/request?sensor_id=" + sensorId;
        return new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int sensorId = 0;
                        int sensorValue = 0;
                        String cropName = null;
                        boolean autoStatus = false;
                        boolean motorStatus = false;
                        try {
                            sensorId = response.getInt("sensor_id");
                            sensorValue = response.getInt("current_value");
                            cropName = response.getString("crop_name");
                            autoStatus = response.getInt("auto") == 1;
                            motorStatus = response.getInt("motor_status") == 1;

                        } catch (Exception e) {
                            Log.e("SummaryFragment", "Oops! JSON's bad!");
                        }
                        Sensor newSensorReading = new Sensor(sensorId, sensorValue, cropName, autoStatus, motorStatus);
                        Toast.makeText(getContext(), "Refereshed", Toast.LENGTH_SHORT).show();
                        setItem(newSensorReading);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("SummaryFragment", "Oops! Volley's Network's bad!");
                    }
                }
        );
    }
    private void attachAutoListener() {
        mAutoToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Toast.makeText(getContext(), "Auto toggle checked!", Toast.LENGTH_SHORT).show();
                    mMotorSwitch.setEnabled(false);

                    VolleyApplication.getInstance().getRequestQueue().add(mAutoEnableRequest);


                } else {
                    // The toggle is disabled
                    mMotorSwitch.setEnabled(true);

                    VolleyApplication.getInstance().getRequestQueue().add(mAutoDisableRequest);

                }
            }
        });
    }

    private void attachMotorListener() {
        mMotorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Toast.makeText(getContext(), "Motor toggle checked!", Toast.LENGTH_SHORT).show();
                    VolleyApplication.getInstance().getRequestQueue().add(mMotorEnableRequest);
                } else {
                    // The toggle is disabled
                    VolleyApplication.getInstance().getRequestQueue().add(mMotorDisableRequest);
                }
            }
        });
    }

    public StringRequest getAutoStringRequest(int sensor_id, int enable) {
        String url = getResources().getString(R.string.server_ip) + "/auto?sensor_id=" + sensor_id + "&enable=" + enable;
        return new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Motor toggle checked!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("SummaryView", "Oops! Volley's Network's bad!");
                    }
                }
        );
    }

    public StringRequest getMotorStringRequest(int sensor_id, int enable) {
        String url = getContext().getString(R.string.server_ip) + "/motor?sensor_id=" + sensor_id + "&enable=" + enable;
        return new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Motor toggle checked!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("SummaryView", "Oops! Volley's Network's bad!");
                    }
                }
        );
    }
}