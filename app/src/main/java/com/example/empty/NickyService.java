package com.example.empty;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.empty.mydb.carin;


//繼承android.app.Service
@SuppressLint("NewApi")
public class NickyService extends Service {
    private Handler handler = new Handler();
    String latitude="",longtitude="",address="",car_num="";
    private Location mLocation;
    private LocationManager mLocationManager;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Intent getIntent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //intent=this.getIntent();
        latitude = intent.getStringExtra("LATITUDE");
        longtitude= intent.getStringExtra("LONGTITUDE");
        address=intent.getStringExtra("ADDRESS");
        car_num=intent.getStringExtra("CAR_NUM");
        car_num.toUpperCase();
      handler.postDelayed(showTime, 1000);
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(showTime);
        super.onDestroy();
    }

    private Runnable showTime = new Runnable() {
        public void run() {
            //log目前時間
            //  Log.i("time:", account);

            try {
                //date
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String dateStr = sdf.format(date);
                //time
                Date time =new Date();
                SimpleDateFormat t=new SimpleDateFormat("HHmm");
                String timestr=t.format(time);
                if(car_num!=null) {

                    mLocation = getLocation();
                    if(mLocation != null)
                    {
                        mLocation = getLocation();
                        address= getAddress();
                        latitude=mLocation.getLatitude()+"";
                        longtitude= mLocation.getLongitude()+"";
                    }
                    carin.executeQuery(car_num,dateStr,timestr,address,latitude,longtitude);
                    handler.postDelayed(this, 180000);
                }
            } catch(Exception e) {}

        }
    };

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
        String addr="";
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> locationList = gc.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
            if (locationList != null) {
                Address address = locationList.get(0);
                addr=address.getAddressLine(0);
             }
        } catch (Exception e) {}
        return  addr;
    }



}