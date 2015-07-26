package in.gotech.intelligation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by anirudh on 27/07/15.
 */
public class SummaryView extends android.support.v7.widget.CardView {
    private TextView mSensorValueTextView;
    private TextView mCropNameTextView;


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
    }

    public void setItem(SummaryFragment.Sensor item) {
        mSensorValueTextView.setText(item.getSensorValue() + "");
        mCropNameTextView.setText(item.getCropName());
    }

}