package com.example.empty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
Button stamp,place,track,real,placemuti;
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
        stamp.setOnClickListener(stampbtn);
        place.setOnClickListener(placebtn);
        track.setOnClickListener(trackbtn);
        real.setOnClickListener(realbtn);
        placemuti.setOnClickListener(placemutibtn);
        real.setVisibility(View.GONE);
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
}