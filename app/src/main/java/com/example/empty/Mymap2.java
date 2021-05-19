package com.example.empty;

import android.Manifest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import java.util.Locale;


public class Mymap2 extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private Button btnFindPath;
    private String etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    String latitude,longtitude,address;
    private Location mLocation;

    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymap2);
        Intent intent=this.getIntent();

        Bundle bundle=intent.getExtras();
        latitude=bundle.getString("LATITUDE");
        longtitude=bundle.getString("LONGTITUDE");
        address=bundle.getString("ADDRESS");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnFindPath = findViewById(R.id.btnFindPath);
        //etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDestination = findViewById(R.id.etDestination);
        btnFindPath.setOnClickListener(getpath);
        etOrigin=address;
        BottomNavigationView nav_view=(BottomNavigationView)findViewById(R.id.nav_view);
        nav_view.setSelectedItemId(R.id.location);
        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.location:
                        startActivity(new Intent(getApplicationContext(),Carplace.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.path:
                        startActivity(new Intent(getApplicationContext(),Cartrack.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.video:
                        startActivity(new Intent(getApplicationContext(),Video.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.stamp:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    private Button.OnClickListener getpath = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            GoogleMap googleMap = null;
            mMap = googleMap;
            mLocation = getLocation();
            LatLng hcmus=new LatLng( mLocation.getLatitude(), mLocation.getLongitude());
            if(mLocation != null)
            {
                hcmus = new LatLng( mLocation.getLatitude(), mLocation.getLongitude());
                hcmus.toString();
            }
            mytoast(hcmus.toString());

            // sendRequest();
        }
    };
    private void sendRequest() {

        String origin = getAddress();
        String destination = etDestination.getText().toString();
        if (destination.isEmpty() ||origin.isEmpty()) {
            Toast.makeText(this, "enter address!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, origin, Toast.LENGTH_SHORT).show();
        try { new DirectionFinder(this, origin, destination).execute(); }
        catch (UnsupportedEncodingException e) {}
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mLocation = getLocation();
        LatLng hcmus=new LatLng( mLocation.getLatitude(), mLocation.getLongitude());
        if(mLocation != null)
        {
            hcmus = new LatLng( mLocation.getLatitude(), mLocation.getLongitude());

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 15));
            originMarkers.add(mMap.addMarker(new MarkerOptions().position(hcmus).title(address)));

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);

            mMap.getUiSettings().setZoomControlsEnabled(true);  // 右下角的放大縮小功能
            mMap.getUiSettings().setCompassEnabled(true);       // 左上角的指南針，要兩指旋轉才會出現
            mMap.getUiSettings().setMapToolbarEnabled(true);    // 右下角的導覽及開啟 Google Map功能

        }

    }
    private boolean gpsIsOpen()
    {
        boolean bRet = true;
        LocationManager alm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if(!alm.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Toast.makeText(this, "未開啟GPS", Toast.LENGTH_SHORT).show();
            bRet = false;
        }

        return bRet;
    }
    private Location getLocation()
    {
        //獲取位置管理服務
        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        //查詢服務資訊
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
        criteria.setAltitudeRequired(false); //海拔資訊：不需要
        criteria.setBearingRequired(false); //方位資訊: 不需要
        criteria.setCostAllowed(false);  //是否允許付費
        criteria.setPowerRequirement(Criteria.POWER_LOW); //耗電量: 低功耗
        String provider = mLocationManager.getBestProvider(criteria, true); //獲取GPS資訊
        Location location = mLocationManager.getLastKnownLocation(provider);
        mLocationManager.requestLocationUpdates(provider, 2000, 5, locationListener);
        return location;
    }
    private final LocationListener locationListener = new LocationListener()
    {
        public void onLocationChanged(Location location){}
        public void onProviderDisabled(String provider){}
        public void onProviderEnabled(String provider){}
        public void onStatusChanged(String provider, int status, Bundle extras){}
    };
    private String getAddress() {
        //Geocoder

        String adminArea="",locality="",addr="";
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> locationList = gc.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
            addr=locationList.get(0).getAddressLine(0);
            // gc.getFromLocationName("台南市潭頂派出所",1);
            if (locationList != null) {
                Address address = locationList.get(0);

                adminArea = address.getAdminArea();//tainan
                locality = address.getLocality();//山上鄉

                String subAdminArea = address.getSubAdminArea();
                String featureName = address.getFeatureName();

                String currentPosition = "緯度: " +mLocation.getLatitude()//22.545975
                        + "\n" + "經度:" + mLocation.getLongitude()//114.101232

                        + "\n" + "縣市:" + adminArea//tainan
                        + "\n" + "鄉鎮:" + locality;//山上鄉
            }
        } catch (Exception e) {}
        return addr;
    }
    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(Mymap2.this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.b))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.a))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}
