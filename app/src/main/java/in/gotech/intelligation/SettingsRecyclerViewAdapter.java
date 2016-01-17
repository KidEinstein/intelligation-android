package in.gotech.intelligation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

/**
 * Created by anirudh on 16/01/16.
 */
public class SettingsRecyclerViewAdapter extends RecyclerView.Adapter<SettingsRecyclerViewAdapter.SettingsCardViewHolder> {
    private ArrayList<Sensor> mSensors;
    private ArrayList<CharSequence> mCrops;
    private Context mContext;
    private static Drawable editDrawable;
    private static Drawable saveDrawable;

    public SettingsRecyclerViewAdapter(ArrayList<Sensor> sensors, Context context, ArrayList<CharSequence> crops) {
        mContext = context;
        mSensors = sensors;
        mCrops = crops;
        if (Build.VERSION.SDK_INT >= 21) {
            saveDrawable = mContext.getDrawable(R.drawable.ic_save_black_48dp);
        } else {
            saveDrawable = mContext.getResources().getDrawable(R.drawable.ic_save_black_48dp);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            editDrawable = mContext.getDrawable(R.drawable.ic_edit_black_48dp);
        } else {
            editDrawable = mContext.getResources().getDrawable(R.drawable.ic_edit_black_48dp);
        }
    }

    @Override
    public int getItemCount() {
        return mSensors.size();
    }

    @Override
    public void onBindViewHolder(SettingsCardViewHolder holder, int position) {
        Sensor currentSensor = mSensors.get(position);
        holder.pinNumberTextView.setText("Pin Number: " + currentSensor.pinNumber);
        holder.cropSpinner.setSelection(mCrops.indexOf(currentSensor.cropName));
        if (currentSensor.editing) {
            holder.editImageButton.setImageDrawable(saveDrawable);
        } else {
            holder.editImageButton.setImageDrawable(editDrawable);
        }
        holder.editImageButton.setTag(currentSensor);
    }

    @Override
    public SettingsCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_card, parent, false);
        SettingsCardViewHolder vh = new SettingsCardViewHolder(v, parent.getContext(), mCrops);
        return vh;
    }

    public static class SettingsCardViewHolder extends RecyclerView.ViewHolder {
        public Spinner cropSpinner;
        public TextView pinNumberTextView;
        public ImageButton editImageButton;
        private Context mContext;

        public SettingsCardViewHolder(View settingsCardView, Context context, ArrayList<CharSequence> crops) {
            super(settingsCardView);
            mContext = context;
            cropSpinner = (Spinner) settingsCardView.findViewById(R.id.crop_name_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, crops);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            cropSpinner.setAdapter(adapter);
            cropSpinner.setEnabled(false);
            editImageButton = (ImageButton) settingsCardView.findViewById(R.id.edit_image_button);
            pinNumberTextView = (TextView) settingsCardView.findViewById(R.id.pin_number_text_view);
            editImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editImageButton.getDrawable() == saveDrawable) {
                        Sensor currentSensor = (Sensor) v.getTag();
                        cropSpinner.setEnabled(false);
                        int cropId = cropSpinner.getSelectedItemPosition() + 1;
                        String url = mContext.getString(R.string.server_ip) + "/edit_sensor_settings?sensor_id=" + currentSensor.sensorId + "&crop_id=" + cropId;
                        StringRequest editSensorRequest = new StringRequest(url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(mContext, "Settings changed", Toast.LENGTH_SHORT).show();
                                        editImageButton.setImageDrawable(editDrawable);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("SettingsRecyclerView", "Oops! Volley's Network's bad!");
                                    }
                                }
                        );
                        VolleyApplication.getInstance().getRequestQueue().add(editSensorRequest);
                    } else {
                        editImageButton.setImageDrawable(saveDrawable);
                        cropSpinner.setEnabled(true);
                    }
                }
            });
        }
    }
}
