package com.example.empty;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Cartrack extends AppCompatActivity {
    WebView myweb;
    String account="",passwd="",names="",course_num="";
    Spinner choose;
    Button back;
    private Menu menu;
    String course[]= {"KLE-5592" ,"788-UG"," 785-UG ","233-VG","787-VG"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartrack);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        back=(Button) findViewById(R.id.back);
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


        myweb.loadUrl("http://vehicle.chansing.com.tw/car/track1.php");

       // myweb.scrollTo(0,800);
        back.setOnClickListener(backbtn);

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
            int tmp=position+1;
            myweb.loadUrl("http://vehicle.chansing.com.tw/car/track"+tmp+".php");

            //  myweb.scrollTo(0,1400);
            //  sel=parent.getSelectedItem().toString();

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