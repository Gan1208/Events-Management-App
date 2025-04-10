package com.example.eventsmanagementapp;

import androidx.fragment.app.FragmentActivity;

import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.eventsmanagementapp.databinding.ActivityGoogleMapBinding;

import java.util.Locale;

public class CategoryGoogleMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;

    SupportMapFragment mapFragment;
    private String categoryName;
    private String categoryLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryName = getIntent().getExtras().getString("selectedCategoryName",null);
        categoryLocation = getIntent().getExtras().getString("selectedCategoryLocation","Melbourne");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

// this method would already be there is you have correctly added StudentCountryMapsActivity
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /**
         * Change map display type, feel free to explore other available map type:
         * MAP_TYPE_NORMAL: Basic map.
         * MAP_TYPE_SATELLITE: Satellite imagery.
         * MAP_TYPE_HYBRID: Satellite imagery with roads and labels.
         * MAP_TYPE_TERRAIN: Topographic data.
         * MAP_TYPE_NONE: No base map tiles
         */
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.getUiSettings().setMapToolbarEnabled(true);


        if(categoryLocation.isEmpty()) {
            LatLng melbourne = new LatLng(-37.814, 144.96332);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(melbourne));
            mMap.addMarker(new MarkerOptions().position(melbourne).title("Melbourne"));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
            Toast.makeText(getApplicationContext(),"Category address not found",Toast.LENGTH_LONG).show();
        }
        findCountryMoveCamera();
    }


    private void findCountryMoveCamera() {
        // initialise Geocode to search location using String
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        // getFromLocationName method works for API 33 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            /**
             * countryToFocus: String value, any string we want to search
             * maxResults: how many results to return if search was successful
             * successCallback method: if results are found, this method will be executed
             *                          runs in a background thread
             */
            geocoder.getFromLocationName(categoryLocation, 1, addresses -> {
                // if there are results, this condition would return true
                if (!addresses.isEmpty()) {
                    // run on UI thread as the user interface will update once set map location
                    runOnUiThread(() -> {
                        // define new LatLng variable using the first address from list of addresses
                        LatLng newAddressLocation = new LatLng(
                                addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude()
                        );

                        // repositions the camera according to newAddressLocation
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));
                        // just for reference add a new Marker
                        MarkerOptions markerOptions = new MarkerOptions().position(newAddressLocation).title(categoryName);
                        Marker marker = mMap.addMarker(
                                markerOptions
                        );
                        assert marker != null;
                        marker.showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
                    });
                }
                else{
                    runOnUiThread(() -> {
                        LatLng melbourne = new LatLng(-37.814, 144.96332);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(melbourne));
                        mMap.addMarker(new MarkerOptions().position(melbourne).title("Melbourne"));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
                        Toast.makeText(getApplicationContext(),"Category address not found",Toast.LENGTH_LONG).show();
                });}
            });
        }
    }
}