package in.gotech.intelligation.stats;

/**
 * Created by anirudh on 21/07/15.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import in.gotech.intelligation.R;
import in.gotech.intelligation.VolleyApplication;

public class StatsFragment extends Fragment {
    private LineChart mChart;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StatsRecyclerViewAdapter mStatsRecyclerViewAdapter;
    private ArrayList<TimeSeries> mSensorTimeSeriesList;
    private XYMultipleSeriesDataset mDataset;
    private XYMultipleSeriesRenderer mRenderer;
    List<double[]> values = new ArrayList<double[]>();
    private GraphicalView mChartView;
    private TimeSeries time_series;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.stats, container, false);
        LinearLayout statsLinearLayout = (LinearLayout) v.findViewById(R.id.linear_layout_stats);

        mSensorTimeSeriesList = new ArrayList<>();

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_stats);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mStatsRecyclerViewAdapter = new StatsRecyclerViewAdapter(getContext(), mSensorTimeSeriesList);
        mRecyclerView.setAdapter(mStatsRecyclerViewAdapter);

        populateLineData();

        return v;
    }


    private void populateLineData() {
        SharedPreferences credentialsSharedPref = getActivity().getSharedPreferences(VolleyApplication.PREFS_NAME, Activity.MODE_PRIVATE);

        Set<String> sensorIdSet = credentialsSharedPref.getStringSet("sensor_ids", null);

        for (String s : sensorIdSet) {
            JsonArrayRequest statRequest = getStatJsonArrayRequest(s);
            RequestQueue requestQueue = VolleyApplication.getInstance().getRequestQueue();
            requestQueue.add(statRequest);
        }
    }

    public JsonArrayRequest getStatJsonArrayRequest(String sensorId) {
        String url = getString(R.string.server_ip) + "/get_stats?sensor_id=" + sensorId;
        return new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        time_series = new TimeSeries("Sensor Values");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject sensorValue = response.getJSONObject(i);
                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                Date d = formatter.parse(sensorValue.getString("time_recorded"));
                                double value = sensorValue.getDouble("value");
                                time_series.add(d, value);
                                Log.d("StatFragment", "Added Date: " + d + " Value: " + value);
                            } catch (JSONException e) {
                                Log.e("StatsFragment", "Oh noes, can't parse JSON");
                            } catch (ParseException e) {
                                Log.e("StatsFragment", "Oh noes, can't parse date");
                            }
                        }
                        mSensorTimeSeriesList.add(time_series);
                        mStatsRecyclerViewAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("StatsFragment", "Oops! Volley's Network's bad!\n" + error);
                    }
                });
    }
    private LineData setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);x
            yVals.add(new Entry(val, i));
        }

        LineDataSet set1;
                    // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            // set1.setFillAlpha(110);
            // set1.setFillColor(Color.RED);

            // set the line to be drawn like this "- - - - - -"
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
//            set1.setColor(Color.BLACK);
//            set1.setCircleColor(Color.BLACK);
//            set1.setLineWidth(1f);
//            set1.setCircleRadius(3f);
//            set1.setDrawCircleHole(false);
//            set1.setValueTextSize(9f);
//            set1.setDrawFilled(true);

//            if (Utils.getSDKInt() >= 18) {
//                // fill drawable only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.stats_gradient);
//                set1.setFillDrawable(drawable);
//            }
//            else {
//                set1.setFillColor(Color.BLACK);
//            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);

            // set data
            return data;

    }
}
