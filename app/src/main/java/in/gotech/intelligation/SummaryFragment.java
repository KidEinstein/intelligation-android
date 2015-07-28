package in.gotech.intelligation;

/**
 * Created by anirudh on 21/07/15.
 */

import android.content.Context;
import android.hardware.Sensor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SummaryFragment extends Fragment {
    SummaryListAdapter summaryListAdapter;
    ArrayList<Sensor> sensorArrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ListView summaryListView = (ListView) inflater.inflate(
                R.layout.summary, container, false);

//        for (int i = 0; i < 10; i++) {
//            CardView summaryCardView = (CardView) inflater.inflate(
//                    R.layout.summary_card, summaryLinearLayout, false);
//
//            TextView sensorValueTextView = (TextView) summaryCardView.findViewById(R.id.sensor_value_text_view);
//
//            Random rn = new Random();
//
//            sensorValueTextView.setText("" + rn.nextInt(100) + "%");
//
//            summaryLinearLayout.addView(summaryCardView);
//        }

        sensorArrayList = new ArrayList<Sensor>();

        summaryListAdapter = new SummaryListAdapter(getActivity(), R.layout.summary_card, sensorArrayList);

        summaryListView.setAdapter(summaryListAdapter);

        new FetchListItems().execute("/request", "1");
        new FetchListItems().execute("/request", "2");
        return summaryListView;

    }

    public static class Sensor {
        int sensorId;
        String cropName;
        int sensorValue;

        public Sensor(int sensorId, int sensorValue, String cropName) {
            this.sensorId = sensorId;
            this.sensorValue = sensorValue;
            this.cropName = cropName;
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

    public class FetchListItems extends AsyncTask<String, Void, Sensor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Sensor doInBackground(String... params) {
            String charset = "UTF-8";
            String url = getString(R.string.server_ip) + params[0];
            String sensorId = params[1];
            InputStream response = null;
            String responseString = null;
            try {
                String query = String.format("sensor_id=%s",
                        URLEncoder.encode(sensorId, charset));
                URLConnection connection = new URL(url + "?" + query).openConnection();
                connection.setRequestProperty("Accept-Charset", charset);
                response = connection.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(response, writer, charset);
                responseString = writer.toString();
            } catch (Exception e) {
                Log.e("SummaryFragment", "Oops! Network's bad!");
            }

            Sensor newSensorReading = new Sensor(Integer.parseInt(sensorId), Integer.parseInt(responseString), "Banana");
            return newSensorReading;

        }

        @Override
        protected void onPostExecute(Sensor newSensorReading) {
            super.onPostExecute(newSensorReading);
            sensorArrayList.add(newSensorReading);
            summaryListAdapter.notifyDataSetChanged();
        }
    }
}
