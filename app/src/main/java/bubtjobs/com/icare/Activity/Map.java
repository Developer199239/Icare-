package bubtjobs.com.icare.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import bubtjobs.com.icare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Map extends AppCompatActivity {

    private GoogleMap mMap;
    double lat1,lon1,lat2,lon2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent=getIntent();
        lat1=Double.parseDouble(intent.getStringExtra("lat1"));
        lat2=Double.parseDouble(intent.getStringExtra("lat2"));
        lon1=Double.parseDouble(intent.getStringExtra("lon1"));
        lon2=Double.parseDouble(intent.getStringExtra("lon2"));
        setup();
    }

    private void setup() {
        if(mMap==null)
        {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        }
        if(mMap!=null)
        {
            setupMap();
        }
    }
    private void setupMap() {
//
//        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
//        options.add(new LatLng(23.7508671, 90.3913638));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(23.7508671, 90.3913638)));
//        options.add(new LatLng(23.90, 90.90));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(23.90, 90.90)));
//
//
//        mMap.addPolyline(options);
//
//        mMap.addMarker(new MarkerOptions().position(new LatLng(23.7508671, 90.3913638)).title("start point").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).snippet("start")).showInfoWindow();
//        mMap.addMarker(new MarkerOptions().position(new LatLng(23.90, 90.90)).title("end point").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.7508671, 90.3913638), 17));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);


        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.add(new LatLng(lat1, lon1));
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat1, lon1)));
        options.add(new LatLng(lat2, lon2));
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat2, lon2)));


        mMap.addPolyline(options);

        mMap.addMarker(new MarkerOptions().position(new LatLng(lat1, lon1)).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).snippet("start")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat2, lon2)).title("end point").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1, lon1), 17));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }
}
