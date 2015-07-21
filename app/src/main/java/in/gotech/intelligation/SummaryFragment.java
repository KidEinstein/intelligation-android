package in.gotech.intelligation;

/**
 * Created by anirudh on 21/07/15.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class SummaryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View summaryView = inflater.inflate(
                R.layout.summary, container, false);

        LinearLayout summaryLinearLayout = (LinearLayout) summaryView.findViewById(R.id.summary_linear_layout);

        for (int i = 0; i < 10; i++) {
            CardView summaryCardView = (CardView) inflater.inflate(
                    R.layout.summary_card, summaryLinearLayout, false);

            TextView sensorValueTextView = (TextView) summaryCardView.findViewById(R.id.sensor_value_text_view);

            Random rn = new Random();

            sensorValueTextView.setText("" + rn.nextInt(100) + "%");

            summaryLinearLayout.addView(summaryCardView);
        }

        return summaryView;

    }
}
