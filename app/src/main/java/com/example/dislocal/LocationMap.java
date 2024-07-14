package com.example.dislocal;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.dislocal.databinding.ActivityLocationMapBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationMap extends FragmentActivity {

    private GoogleMap mMap;
    private ActivityLocationMapBinding binding;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    private SessionManager sessionManager;
    private RequestQueue requestQueue;

    // below are the latitude and longitude
    // of 4 different locations.
    LatLng kompleksSeni = new LatLng(3.082088166336685, 101.52276970654462);
    String kompleksSeniN = "Kompleks Seni Islam Antarabangsa Selangor";
    LatLng masjidSSAAS = new LatLng(3.0795222623653977, 101.52080373389244);
    String masjidSSAASN = "Masjid Sultan Salahuddin Abdul Aziz Shah";
    LatLng muziumSAS = new LatLng(3.0747733183141794, 101.52092823688785);
    String muziumSASN = "Sultan Alam Shah Museum";
    LatLng botani = new LatLng(3.0947427002731396, 101.51046210805201);
    String botaniN = "National Botanic Gardens Shah Alam";
    LatLng lakeSA = new LatLng(3.073144265071678, 101.51375416387258);
    String lakeSAN = "Shah Alam Lake Garden";
    LatLng extremeSA = new LatLng(3.0867543021361055, 101.54258775223153);
    String extremeSAN = "Shah Alam Extreme Park";
    LatLng icity = new LatLng(3.0657516522162527, 101.48323757757173);
    String icityN = "i-City Theme Park: A Night of Dazzling Lights";



    // creating array list for adding all our locations.
    private ArrayList<LatLng> locationArrayList;
    private ArrayList<String> locationNameArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);

        sessionManager = new SessionManager(getApplicationContext());

        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(LocationMap.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        //initialize fused Location
        client = LocationServices.getFusedLocationProviderClient(this);

// in below line we are initializing our array list.
        locationArrayList = new ArrayList<>();
        locationNameArrayList = new ArrayList<>();

        // on below line we are adding our
        // locations in our array list.
        locationArrayList.add(kompleksSeni);
        locationArrayList.add(masjidSSAAS);
        locationArrayList.add(muziumSAS);
        locationArrayList.add(botani);
        locationArrayList.add(lakeSA);
        locationArrayList.add(extremeSA);
        locationArrayList.add(icity);

        // on below line we are adding our
        // locations in our array list.
        locationNameArrayList.add(kompleksSeniN);
        locationNameArrayList.add(masjidSSAASN);
        locationNameArrayList.add(muziumSASN);
        locationNameArrayList.add(botaniN);
        locationNameArrayList.add(lakeSAN);
        locationNameArrayList.add(extremeSAN);
        locationNameArrayList.add(icityN);

        //Check permission
        if (ActivityCompat.checkSelfPermission(LocationMap.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            //get current location when permission is granted
            getCurrentLocation();

        } else {
            //request permission when permission is denied
            ActivityCompat.requestPermissions(LocationMap.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private void getCurrentLocation() {
        //Initialize task location
        if (ActivityCompat.checkSelfPermission(LocationMap.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            //get current location when permission is granted
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    //When success
                    if (location != null) {
                        //sync map
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                //Initialize Lat Lng
                                LatLng latLng = new LatLng(location.getLatitude(),
                                        location.getLongitude());

                                for (int i = 0; i < locationArrayList.size(); i++) {

                                    //Create marker options
                                    MarkerOptions options = new MarkerOptions()
                                            .position(locationArrayList.get(i))
                                            .title(locationNameArrayList.get(i));

                                    googleMap.addMarker(options);
                                }
                                //Create marker options
                                MarkerOptions options = new MarkerOptions()
                                        .position(latLng)
                                        .title("I am here");

                                //zoom map scale 15
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                googleMap.addMarker(options);




                            }
                        });


                    }
                }
            });
        } else {
            //request permission when permission is denied
            ActivityCompat.requestPermissions(LocationMap.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


    }







    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
//Get current location when permission granted
                getCurrentLocation();
            }
        }
    }
}



