package in.gotech.intelligation.stats;

/**
 * Created by anirudh on 21/07/15.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import in.gotech.intelligation.signup.MapsActivity;
import in.gotech.intelligation.R;

public class StatsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.stats, container, false);
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(getContext(), "Location: " + location, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        Toast.makeText(getContext(), "Requesting Location", Toast.LENGTH_LONG).show();
//        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        ViewGroup ll = (ViewGroup) v.findViewById(R.id.stats_linear_layout);
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
        Button b = new Button(getContext());
        b.setText("Hola");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext().getApplicationContext(), MapsActivity.class);
                startActivity(i);
            }
        });
        ll.addView(b);
        return v;
    }
}
