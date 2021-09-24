package com.example.empty;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.empty.mydb.dbpermission;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cartrack extends AppCompatActivity {
    WebView myweb;
    String vender="";
    String account="",passwd="",names="",course_num="";
    Spinner choose,choosedate;
    String sel="";
    String date="";
    Button back;
    private Menu menu;
    String course[]= {"    KLE-5592" ,"    788-UG","    785-UG ","    233-VG","    787-VG"};
    String [] mydate=new String[30];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartrack);
       // Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
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

        choose=(Spinner)findViewById(R.id.choose);
        choosedate=(Spinner)findViewById(R.id.choosedate);

        Date date_1 = new Date( );
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        for(int i=0;i<30;i++){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date_1);
            calendar.add(calendar.DATE, (-1) );
            date_1 = calendar.getTime();
            mydate[i]="   "+format.format(date_1)+"";
        }


        ArrayAdapter<String> choosed=new ArrayAdapter<String>(this,android.R.layout.
                simple_spinner_dropdown_item,mydate);

        choosedate.setAdapter(choosed);

        choosedate.setOnItemSelectedListener(chdate);
        String result = dbpermission.executeQuery(vender,"1234","1234");
        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
            if(jsonArray.length()>0){
                course= new String[jsonArray.length()];
                for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
                {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                    String license_plate=jsonData.getString("license_plate");
                    //revise
                    course[i]="    "+license_plate;

                }
            }



        }

        catch(Exception e){}
        ArrayAdapter<String> choosespn=new ArrayAdapter<String>(this,android.R.layout.
                simple_spinner_dropdown_item,course);

        choose.setAdapter(choosespn);

        choose.setOnItemSelectedListener(chbtn);
        myweb = (WebView) findViewById(R.id.webview);

        myweb.getSettings().setBuiltInZoomControls(true);
        myweb.getSettings().setJavaScriptEnabled(true);
        myweb.setWebViewClient(new WebViewClient());
        //  https://happylilac.net/movie/movie-age4/
        //http://211.23.243.109:6060/GetGpsHistoryTrack/100?DeviceId=1906270011&BeginTime=2021~03~10~00~00~00&EndTime=2021~03~10~23~59~59'


       // myweb.loadUrl("http://vehicle.chansing.com.tw/car/track1.php");

        BottomNavigationView nav_view=(BottomNavigationView)findViewById(R.id.nav_view);
        nav_view.setSelectedItemId(R.id.path);
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
    private Button.OnClickListener backbtn=new Button.OnClickListener(){//軌跡
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Cartrack.this, Home.class);
            startActivity(intent);
        }
    };
    private Spinner.OnItemSelectedListener chbtn= new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v,
                                   int position, long id) {
            sel=parent.getSelectedItem().toString();
            sel=sel.replaceAll("\\s","");
            myweb.loadUrl("http://vehicle.chansing.com.tw/car/track1.php?num="+sel+"&date="+date);

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };
    private Spinner.OnItemSelectedListener chdate= new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v,
                                   int position, long id) {
             date=parent.getSelectedItem().toString();
            date=date.replaceAll("/","~");
            date=date.replaceAll(" ","");
            myweb.loadUrl("http://vehicle.chansing.com.tw/car/track1.php?num="+sel+"&date="+date);

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };
    public void onLoadResource(WebView view, String url) {

        myweb.scrollTo(0,1400);

    }

    public void onBackPressed() {
        if (myweb.canGoBack()) {
            myweb.goBack();
            return;
        }

        super.onBackPressed();
    }

}