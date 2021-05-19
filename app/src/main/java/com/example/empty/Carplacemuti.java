package com.example.empty;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class Carplacemuti extends AppCompatActivity {
    WebView myweb;

    Button back;
    private Menu menu;
   // String course[]= {"KLE-5592" ,"788-UG","785-UG ","233-VG","787-VG"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carplacemuti);
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


        back=(Button) findViewById(R.id.back);

        myweb = (WebView) findViewById(R.id.webview);

        myweb.getSettings().setBuiltInZoomControls(true);
        myweb.getSettings().setJavaScriptEnabled(true);
        myweb.setWebViewClient(new WebViewClient());

        myweb.loadUrl("http://vehicle.chansing.com.tw/car/map2.php");
        back.setOnClickListener(backbtn);

    }
    private Button.OnClickListener backbtn=new Button.OnClickListener(){//軌跡
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Carplacemuti.this, Home.class);
            startActivity(intent);
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