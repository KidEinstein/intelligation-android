package in.gotech.intelligation;

/**
 * Created by anirudh on 21/07/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class SummaryFragment extends Fragment {
    SummaryListAdapter summaryListAdapter;
    ArrayList<Sensor> sensorArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ListView summaryListView = (ListView) inflater.inflate(
                R.layout.summary, container, false);

        sensorArrayList = new ArrayList<Sensor>();

        summaryListAdapter = new SummaryListAdapter(getActivity(), R.layout.summary_card, sensorArrayList);

        summaryListView.setAdapter(summaryListAdapter);

        SharedPreferences credentialsSharedPref = getActivity().getSharedPreferences(Login.PREFS_NAME, Activity.MODE_PRIVATE);

        HashSet<String> sensorIdSet = (HashSet<String>) credentialsSharedPref.getStringSet("sensor_ids", null);

        for (String s : sensorIdSet) {
            VolleyApplication.getInstance().getRequestQueue().add(getSensorJsonObjectRequest(Integer.parseInt(s)));
        }

        return summaryListView;

    }

    public JsonObjectRequest getSensorJsonObjectRequest(int sensor_id) {
        String url = getString(R.string.server_ip) + "/request?sensor_id=" + sensor_id;
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
                        sensorArrayList.add(newSensorReading);
                        summaryListAdapter.notifyDataSetChanged();
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


    public class Sensor {
        int sensorId;
        String cropName;
        int sensorValue;
        boolean motorStatus;
        boolean autoStatus;

        public Sensor(int sensorId, int sensorValue, String cropName, boolean autoStatus, boolean motorStatus) {
            this.sensorId = sensorId;
            this.sensorValue = sensorValue;
            this.cropName = cropName;
            this.autoStatus = autoStatus;
            this.motorStatus = motorStatus;
        }

        public int getSensorId() {
            return sensorId;
        }

        public String getCropName() {
            return cropName;
        }

        public int getSensorValue() {
            return sensorValue;
        }

        public boolean getAutoStatus() {
            return autoStatus;
        }

        public boolean getMotorStatus() {
            return motorStatus;
        }

    }


    private class SummaryListAdapter extends ArrayAdapter<Sensor> {

        ArrayList<Sensor> sensorArrayList;
        Context mContext;
        LayoutInflater inflater;

        public SummaryListAdapter(Context context, int layoutResourceId, ArrayList<Sensor> data) {
            super(context, layoutResourceId, data);
            sensorArrayList = data;
            mContext = context;
            inflater = getActivity().getLayoutInflater();
        }

        @Override

        public View getView(int position, View convertView, ViewGroup parent) {
            SummaryView summaryView = (SummaryView) convertView;
            if (null == summaryView)
                summaryView = SummaryView.inflate(parent);
            summaryView.setItem(getItem(position));
            return summaryView;
        }

    }

}

