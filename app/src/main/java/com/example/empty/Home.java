package com.example.empty;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
Button stamp,place,track,real,placemuti,placemuti2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        stamp=(Button)findViewById(R.id.stamp);
        place=(Button)findViewById(R.id.place);
        track=(Button)findViewById(R.id.track);
        real=(Button)findViewById(R.id.real);
        placemuti=(Button)findViewById(R.id.placemuti);
        placemuti2=(Button)findViewById(R.id.placemuti2);
        stamp.setOnClickListener(stampbtn);
        place.setOnClickListener(placebtn);
        track.setOnClickListener(trackbtn);
        real.setOnClickListener(realbtn);
        placemuti.setOnClickListener(placemutibtn);
        placemuti2.setOnClickListener(placemuti2btn);
       // real.setVisibility(View.GONE);
    }
    private Button.OnClickListener stampbtn=new Button.OnClickListener(){//打卡
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Home.this, MainActivity.class);
            startActivity(intent);
        }
    };
    private Button.OnClickListener placebtn=new Button.OnClickListener(){//定位
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Home.this, Carplace.class);
            startActivity(intent);
        }
    };
    private Button.OnClickListener trackbtn=new Button.OnClickListener(){//軌跡
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Home.this, Cartrack.class);
            startActivity(intent);
        }
    };
    private Button.OnClickListener realbtn=new Button.OnClickListener(){//即時影像
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Home.this, Video.class);
            startActivity(intent);
        }
    };
    private Button.OnClickListener placemutibtn=new Button.OnClickListener(){//打卡
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Home.this, Carplacemuti.class);
            startActivity(intent);
        }
    };
    private Button.OnClickListener placemuti2btn=new Button.OnClickListener(){//打卡
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Home.this, Carplacemuti2.class);
            startActivity(intent);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.location) {
            return true;
        }
        else if (id == R.id.navigation_home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}