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

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashSet;

public class SummaryFragment extends Fragment {
    SummaryListAdapter mSummaryListAdapter;
    ArrayList<Sensor> mSensorArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ListView summaryListView = (ListView) inflater.inflate(
                R.layout.summary, container, false);

        mSensorArrayList = new ArrayList<Sensor>();

        mSummaryListAdapter = new SummaryListAdapter(getActivity(), R.layout.summary_card, mSensorArrayList);

        summaryListView.setAdapter(mSummaryListAdapter);

        SharedPreferences credentialsSharedPref = getActivity().getSharedPreferences(Login.PREFS_NAME, Activity.MODE_PRIVATE);

        HashSet<String> sensorIdSet = (HashSet<String>) credentialsSharedPref.getStringSet("sensor_ids", null);

        for (String s : sensorIdSet) {
            getSensorJsonObjectRequest(Integer.parseInt(s)).fetchSensor();
        }

        return summaryListView;

    }

    public JsonObjectSensorRequest getSensorJsonObjectRequest(int sensorId) {
        String url = getString(R.string.server_ip) + "/request?sensor_id=" + sensorId;
        return new JsonObjectSensorRequest(url,
                new SensorResponseListener() {
                    @Override
                    void onNewSensorReading(Sensor newSensorReading) {
                        mSensorArrayList.add(newSensorReading);
                        mSummaryListAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("SummaryFragment", "Oops! Volley's Network's bad!\n" + error);
                    }
                });
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

