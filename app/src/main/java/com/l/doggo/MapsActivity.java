package com.l.doggo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    LocationManager locationManager;
    LocationListener locationListener;
    private GoogleMap mMap;


    //Om vi har tillstånd till platsdata så ber vi om gps uppdateringar
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Uppdaterar kartan var 4e minut eller var 10e meter
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 240000, 10, locationListener);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_maps, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;

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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Map typ = hybrid
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                // Sätter en lila markering vid användarens position och flyttar kameran dit

                LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                // Tar bort markeringar från kartan
                mMap.clear();
                // Lägger till en ny lila markering på användarens nuvarande plats
                mMap.addMarker(new MarkerOptions().position(myLocation).title("Your Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

                // Kartan zoomar in (1 - 20) 1 = ser hela världen, 20 = ser gator på nära håll
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
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


        // Om tillstånd att använda gps inte finns så fråga om tillstånd, annars hämta platsdata
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            //CameraUpdate cameraUpdate = ;
            //mMap.animateCamera(cameraUpdate);


            // Uppdaterar positionen var 4e minut eller var 10e meter
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 240000, 10, locationListener);
            // Hämtar senast kända position
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // Gör kartan till en hybrid mellan satellit och vanlig karta
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            // Sätter en lila markering vid användarens position och flyttar kameran dit
            LatLng myLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(myLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

            // Kartan zoomar in (1 - 20) 1 = ser hela världen, 20 = ser gator på nära håll
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
        }
    }
}
