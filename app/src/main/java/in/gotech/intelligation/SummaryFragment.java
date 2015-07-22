package in.gotech.intelligation;

/**
 * Created by anirudh on 21/07/15.
 */

import android.content.Context;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SummaryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View summaryView = inflater.inflate(
                R.layout.summary, container, false);

        ListView summaryListView = (ListView) summaryView.findViewById(R.id.summary_list_view);

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

        ArrayList<Sensor> sensorArrayList = new ArrayList<Sensor>();
        for (int i = 0; i < 100; i++) {
            sensorArrayList.add(new Sensor());
        }

        SummaryListAdapter summaryListAdapter = new SummaryListAdapter(getActivity(), R.layout.summary_card, sensorArrayList);

        summaryListView.setAdapter(summaryListAdapter);

        return summaryView;

    }

    public class Sensor {
        int sensorId;
        String cropName;
        int sensorValue;

        public Sensor() {
            Random rn = new Random();
            sensorId = rn.nextInt(100);
            cropName = "Banana";

        }
        public int getSensorId() {
            return sensorId;
        }
        public String getCropName() {
            return cropName;
        }

    }
    public class SummaryListAdapter extends ArrayAdapter<Sensor> {

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
            Sensor currentSensor = sensorArrayList.get(position);

            if (convertView == null) {

                convertView = inflater.inflate(
                        R.layout.summary_card, parent, false);
            }

            CardView summaryCardView = (CardView) convertView;
            TextView sensorValueTextView = (TextView) summaryCardView.findViewById(R.id.sensor_value_text_view);

            Random rn = new Random();

            sensorValueTextView.setText("" + rn.nextInt(100) + "%");

            TextView cropNameTextView = (TextView) summaryCardView.findViewById(R.id.crop_name_text_view);

            return summaryCardView;
        }
    }
}
