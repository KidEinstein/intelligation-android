package in.gotech.intelligation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by anirudh on 27/07/15.
 */
public class SummaryView extends android.support.v7.widget.CardView {
    private TextView mSensorValueTextView;
    private TextView mCropNameTextView;
    private ToggleButton mAutoToggleButton;
    private Switch mMotorSwitch;

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
    }

    public void setItem(SummaryFragment.Sensor item) {
        mSensorValueTextView.setText(item.getSensorValue() + "");
        mCropNameTextView.setText(item.getCropName());
        mAutoToggleButton.setChecked(item.getAutoStatus());
        mMotorSwitch.setChecked(item.getMotorStatus());
        mMotorSwitch.setEnabled(!item.getAutoStatus());
    }

}