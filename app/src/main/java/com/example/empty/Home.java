package com.example.empty;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    TextView create_date,status,person;
    EditText topic,question ;
    Spinner spinner,problem_spn;
    String[]choose={"面談","手寫"};
    String[]problem={"面談","手寫"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
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
        create_date=(TextView)findViewById(R.id.create_date);
        status=(TextView)findViewById(R.id.status);
        person=(TextView)findViewById(R.id.person);
        topic=(EditText)findViewById(R.id.topic);
        question=(EditText)findViewById(R.id.question);
        problem_spn=(Spinner)findViewById(R.id.problem_spn);
        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> probspn=new ArrayAdapter<String>(this,android.R.layout.
                simple_spinner_dropdown_item,problem);

        spinner.setAdapter(probspn);
        spinner.setOnItemSelectedListener(prospn);
////dayspn
        ArrayAdapter<String> choosedayspn=new ArrayAdapter<String>(this,android.R.layout.
                simple_spinner_dropdown_item,choose);

        spinner.setAdapter(choosedayspn);
        spinner.setOnItemSelectedListener(spinnerbtn);
        BottomNavigationView   nav_view=(BottomNavigationView)findViewById(R.id.nav_view);
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
    private Spinner.OnItemSelectedListener spinnerbtn=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                   String selC=parent.getSelectedItem().toString();

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };
    private Spinner.OnItemSelectedListener prospn=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    String selC=parent.getSelectedItem().toString();

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };
}