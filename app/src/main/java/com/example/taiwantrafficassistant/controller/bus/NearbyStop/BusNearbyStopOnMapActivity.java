package com.example.taiwantrafficassistant.controller.bus.NearbyStop;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taiwantrafficassistant.controller.bus.routeSearch.BusRouteInformation;
import com.example.taiwantrafficassistant.model.bus.api.BusStopsCoordinateUrlBuilder;
import com.example.taiwantrafficassistant.model.bus.json.BusRouteJsonUtils;
import com.example.taiwantrafficassistant.model.bus.json.StopCoordinateJsonAnalysis;
import com.example.taiwantrafficassistant.model.utilities.network.NetworkUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.taiwantrafficassistant.R;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.google.android.gms.tasks.OnSuccessListener;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BusNearbyStopOnMapActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private GoogleMap map;
    private Marker marker_taroko;
    private Marker marker_yushan;
    private Marker marker_kenting;
    private Marker marker_yangmingshan;
    private TextView tvMarkerDrag;
    private TextView tvBusStopResult;
    private LatLng taroko;
    private LatLng yushan;
    private LatLng kenting;
    private LatLng yangmingshan;
    LocationManager mLocationManager;
    List<LatLng> locations;
    Location mylocation;
    private List<BusStopInformation> informationList = new ArrayList<>();
    private final static int REQUEST_CODE_RESOLUTION = 1;
    private final static String TAG = "MainActivity";
    private GoogleApiClient googleApiClient;
    private Location lastLocation;

    private com.google.android.gms.location.LocationListener locationListener = new com.google.android.gms.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mylocation = location;
            makeNearbyStopQuery();
        }
    };

    private GoogleApiClient.ConnectionCallbacks connectionCallbacks =
            new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    Log.i(TAG, "GoogleApiClient connected");
                    if (ActivityCompat.checkSelfPermission(BusNearbyStopOnMapActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                        lastLocation = LocationServices.FusedLocationApi
                                .getLastLocation(googleApiClient);
                        LocationRequest locationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setSmallestDisplacement(10000);
                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                googleApiClient, locationRequest, locationListener);
                    }
                }

                @Override
                public void onConnectionSuspended(int i) {
                    showToast("12");
                }
            };

    private GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(@NonNull ConnectionResult result) {
                    showToast("12");
                    if (!result.hasResolution()) {
                        GoogleApiAvailability.getInstance().getErrorDialog(
                                BusNearbyStopOnMapActivity.this,
                                result.getErrorCode(),
                                0
                        ).show();
                        return;
                    }
                    try {
                        result.startResolutionForResult(
                                BusNearbyStopOnMapActivity.this,
                                REQUEST_CODE_RESOLUTION);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Exception while starting resolution activity");
                    }
                }
            };
    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(connectionCallbacks)
                    .addOnConnectionFailedListener(onConnectionFailedListener)
                    .build();
        }
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_bus_nearby_stop_on_map);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fmMap);
        mapFragment.getMapAsync(this);
        //tvBusStopResult = findViewById(R.id.tv_bus_nearby_stop_result);
        //tvMarkerDrag = (TextView) findViewById(R.id.tvMarkerDrag);

    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;


    }

    private void initPoints() {
        locations = new ArrayList<>();
        for(int i = 0;i < informationList.size();i++){
            locations.add(new LatLng(informationList.get(i).getLatitude(), informationList.get(i).getLongitude()));
        }

    }

    private void makeNearbyStopQuery(){
        String tmp = "25.061397,121.482854";
        System.out.println(tmp);
            tmp = lastLocation.getLatitude() +
                    "," +
                    lastLocation.getLongitude();
            System.out.println(tmp);
        new QueryTask().execute(tmp);
    }

    private void reloadInformation(){
        initPoints();
        setUpMap();
    }

    public class QueryTask extends AsyncTask<String, Void, List<BusStopInformation>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<BusStopInformation> doInBackground(String... params) {
            URL url= BusStopsCoordinateUrlBuilder.buildUrl(params[0]);
            String jsonResponse = null;
            if(url == null){
                return null;
            }else{

            }

            System.out.println(url.toString());
            try {
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
                System.out.println(jsonResponse);
            }catch (Exception e){

            }
            List<BusStopInformation> result = null;
            if(jsonResponse == null){
                System.out.println(jsonResponse == null);
                return null;
            }else{
                result =  StopCoordinateJsonAnalysis.analsys(jsonResponse);
            }

            return result;

        }

        @Override
        protected void onPostExecute(List<BusStopInformation> result) {
            informationList = result;
            for(int i = 0;i < result.size();i++){
                System.out.println(result.get(i).getStopName());
            }
            reloadInformation();
        }
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        map.getUiSettings().setZoomControlsEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(locations.get(0))
                .zoom(17)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);

        addMarkersToMap();

        //map.setInfoWindowAdapter(new MyInfoWindowAdapter());

        MyMarkerListener myMarkerListener = new MyMarkerListener();
        map.setOnMarkerClickListener(myMarkerListener);
        map.setOnInfoWindowClickListener(myMarkerListener);

    }

    private void addMarkersToMap() {
        for(int i = 0;i < informationList.size();i++){
            map.addMarker(new MarkerOptions()
            .position(locations.get(i))
            .title(informationList.get(i).getStopName())
                    .snippet(informationList.get(i).getStopName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_pin)
            ));
        }
    }

    private class MyMarkerListener implements OnMarkerClickListener,
            OnInfoWindowClickListener {
        @Override
        public boolean onMarkerClick(Marker marker) {
            showToast(marker.getTitle());
            return false;
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            showToast(marker.getTitle());
        }
    }

    public void onClearMapClick(View view) {
        map.clear();
    }

    public void onResetMapClick(View view) {
        map.clear();
        addMarkersToMap();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

