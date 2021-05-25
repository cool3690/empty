package com.example.empty;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.empty.mydb.Write;
import com.example.empty.mydb.dbauthority;
import com.example.empty.mydb.dbc1;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private EditText acc;
    private Button login, c1,c2,c3,c4,c5,c6;
    RelativeLayout Rimg;
    boolean tf=false;
    //private Button gmap ;
    private Location mLocation;
    private LocationManager mLocationManager;
    private Write write = new Write(MainActivity.this);
    //date
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    String dateStr = sdf.format(date);
    String company_name="";
    //time


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

       findViews();
         setListeners();

/*
        Context context = MainActivity.this;
        Dialog   dia = new Dialog(context, R.style.edit_AlertDialog_style);
        dia.setContentView(R.layout.imgshow);
        //  Button btok=(Button)dia.findViewById(R.id.btok);
        dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
        Window w = dia.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        lp.x = 0; // 新位置X坐標
         lp.y=height-200;
         lp.width =width; // 寬度
        dia.show();
        dia.onWindowAttributesChanged(lp);

 */



        BottomNavigationView nav_view=(BottomNavigationView)findViewById(R.id.nav_view);
        nav_view.setSelectedItemId(R.id.stamp);
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

    private void findViews() {
        login = (Button) findViewById(R.id.login);
        c1 = (Button) findViewById(R.id.c1);
        c2 = (Button) findViewById(R.id.c2);
        c3 = (Button) findViewById(R.id.c3);
        c4 = (Button) findViewById(R.id.c4);
        c5 = (Button) findViewById(R.id.c5);
        c6 = (Button) findViewById(R.id.c6);
        Rimg= (RelativeLayout) findViewById(R.id.Rimg);

        acc = (EditText) findViewById(R.id.acc);
        SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
        String name_str = remdname.getString("acc", "");
        acc.setText(name_str);

        Date time = new Date();
        SimpleDateFormat t = new SimpleDateFormat("HH:mm");
        String timestr = t.format(time);

      write.WriteFileExample(name_str + "/" + dateStr + "/" + timestr);
        if (!gpsIsOpen()) {
            mytoast("請開GPS和網路");
        }
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable()) {
            mytoast("請開網路:");
            write.WriteFileExample(dateStr + "請開網路");
        }
        Rimg.setVisibility(View.GONE);
        Rimg.setOnClickListener(Rimgbtn);

/*
        mLocation = getLocation();
         if(mLocation != null)
        {
           Intent intent2 = new Intent(MainActivity.this, NickyService.class);

            mLocation = getLocation();
            String la="";
            String lo="";
            name_str=name_str.toUpperCase();
            la=mLocation.getLatitude()+"";
            lo= mLocation.getLongitude()+"";
            String r= getAddress();
            intent2.putExtra("LATITUDE", la);
            intent2.putExtra("LONGTITUDE",lo);
            intent2.putExtra("ADDRESS",r);
            intent2.putExtra("CAR_NUM",name_str);

            // 執行附帶資料的 Intent
            startService(intent2);

        }

         */

    }

    private void setListeners() {
       login.setOnClickListener(c1btn);
        c1.setOnClickListener(c1btn);
        c2.setOnClickListener(c1btn);
        c3.setOnClickListener(c1btn);
        c4.setOnClickListener(c1btn);
        c5.setOnClickListener(c1btn);
        c6.setOnClickListener(c1btn);
        // gmap.setOnClickListener(getmap);
    }
    private RelativeLayout.OnClickListener Rimgbtn=new RelativeLayout.OnClickListener(){
        @Override
        public void onClick(View v) {
            tf=false;
            Rimg.setVisibility(View.GONE);
        }
    };
    /*
    private Button.OnClickListener getmap = new Button.OnClickListener() {
        public void onClick(View v) {
            Button btn = (Button)v;
            if(btn.getId() == R.id.gmap)
            {
                if(!gpsIsOpen())
                    return;
                mLocation = getLocation();
                if(mLocation != null)
                {
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,Mymap2.class);
                    Bundle bundle=new Bundle();
                    mLocation = getLocation();
                    String r= getAddress();
                    String la="";
                    String lo="";
                        la=mLocation.getLatitude()+"";
                        lo= mLocation.getLongitude()+"";

                    bundle.putString("LATITUDE",la );
                    bundle.putString("ADDRESS",r);
                    bundle.putString("LONGTITUDE",lo);

                    intent.putExtras(bundle);

                    // 執行附帶資料的 Intent
                    startActivity(intent);

                }

                else
                    mytoast("GPS定位中....");

            }
        }
    };

     */
    private boolean haveInternet() {
        boolean result = false;
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            result = false;

        if (!info.isAvailable())
            result = false;
        return result;
    }

    private Button.OnClickListener c1btn = new Button.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.login:
                    company_name=" ";

                    break;
                case R.id.c1:
                company_name=c1.getText().toString();
                break;
                case R.id.c2:
                company_name=c2.getText().toString();
                break;
                case R.id.c3:
                company_name=c3.getText().toString();
                break;
                case R.id.c4:
                company_name=c4.getText().toString();
                break;
                case R.id.c5:
                 company_name=c5.getText().toString();
                break;
                case R.id.c6:
                company_name=c6.getText().toString();
                break;
                default:
                    break;
            }
           // mytoast(company_name);


 /*
            mLocation = getLocation();
            if (!gpsIsOpen()) {
                write.WriteFileExample("GPS not open !" + dateStr);
                return;
            }
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (info == null || !info.isConnected() || !info.isAvailable()) {
                mytoast(dateStr + "請開網路:");
                write.WriteFileExample("請開網路");
                return;
            } else if (mLocation != null) {
                getAddress();
                //mytoast(getAddress()+"");
            } else {
                mytoast("GPS定位中....");
            }
*/
            getAddress();
            Date time = new Date();
            SimpleDateFormat t = new SimpleDateFormat("HH:mm");
            String timestr = t.format(time);

            String car_num = acc.getText().toString().toUpperCase();

            /*
            if(!TextUtils.isEmpty(car_num) &&car_num.contains("-")){
                dbc1.executeQuery(car_num ,dateStr,timestr,company_name);
                mytoast("簽到:"+dateStr+" "+timestr+" \n"+retaddress());
            }
            else{mytoast("車牌格式錯誤!");}
*/
            new DownloadFileAsync().execute(car_num, timestr);


        }
    };

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(final String... params) {


            if (!TextUtils.isEmpty(params[0]) && params[0].contains("-")) {

                dbc1.executeQuery(params[0], dateStr, params[1],company_name);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //mytoast("簽到:" + dateStr + " " + params[1] + " \n" + retaddress());
                       Rimg.setVisibility(View.VISIBLE);
                       tf=true;

                    }
                });

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mytoast("車牌格式錯誤!");
                    }
                });
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused) {

        }
    }

    private String retaddress() {
        String addr = "";
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> locationList = gc.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
            if (locationList != null) {
                Address address = locationList.get(0);
                addr = address.getAddressLine(0);

                //  carin.executeQuery(car_num,dateStr,timestr,addr,mLocation.getLatitude()+"",mLocation.getLongitude()+"");

            }
        } catch (Exception e) {
        }

        return addr;
    }

    private Button.OnClickListener getDBRecord = new Button.OnClickListener() {
        public void onClick(View v) {

            mLocation = getLocation();
            if (!gpsIsOpen()) {
                write.WriteFileExample("GPS not open !" + dateStr);
                return;
            }
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (info == null || !info.isConnected() || !info.isAvailable()) {
                mytoast(dateStr + "請開網路:");
                write.WriteFileExample("請開網路");
                return;
            } else if (mLocation != null) {
                getAddress();
            } else {
                mytoast("GPS定位中....");
            }
            Date time = new Date();
            SimpleDateFormat t = new SimpleDateFormat("HH:mm");
            String timestr = t.format(time);
            String car_num = acc.getText().toString().toUpperCase();
                /*
                if(!TextUtils.isEmpty(car_num) &&car_num.contains("-")){
                    dbc1.executeQuery(car_num.toUpperCase(),dateStr,timestr,"全興");

                    dbauthority.executeQuery(car_num.toUpperCase(),dateStr,"全興");
                    mytoast("簽到:"+dateStr+" "+timestr+" ");
                }
                else{mytoast("車牌格式錯誤!");}

                 */
            new chan().execute(car_num, timestr);
        }
    };

    class chan extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(final String... params) {


            if (!TextUtils.isEmpty(params[0]) && params[0].contains("-")) {
                dbc1.executeQuery(params[0], dateStr, params[1], "全興");
                dbauthority.executeQuery(params[0], dateStr, "全興");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mytoast("簽到:" + dateStr + " " + params[1] + " \n" + retaddress());
                    }
                });

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mytoast("車牌格式錯誤!");
                    }
                });
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused) {

        }
    }

    private void mytoast(String str) {
        Toast toast = Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private boolean gpsIsOpen() {
        boolean bRet = true;
        LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "未開啟GPS", Toast.LENGTH_SHORT).show();
            bRet = false;
        }

        return bRet;
    }

    private Location getLocation() {
        //獲取位置管理服務
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //查詢服務資訊
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
        criteria.setAltitudeRequired(false); //海拔資訊：不需要
        criteria.setBearingRequired(false); //方位資訊: 不需要
        criteria.setCostAllowed(false);  //是否允許付費
        criteria.setPowerRequirement(Criteria.POWER_LOW); //耗電量: 低功耗
        String provider = mLocationManager.getBestProvider(criteria, true); //獲取GPS資訊
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {



        }
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

    private void getAddress() {
        SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit=remdname.edit();
        edit.putString("acc", acc.getText().toString());
        edit.commit();

        //Geocoder
        String addr="";
        /*
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> locationList = gc.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
            if (locationList != null) {
                Address address = locationList.get(0);
                addr=address.getAddressLine(0);

                    carin.executeQuery(car_num,dateStr,timestr,addr,mLocation.getLatitude()+"",mLocation.getLongitude()+"");

            }
        } catch (Exception e) {}
 */

    }



}
