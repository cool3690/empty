package com.example.empty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.empty.mydb.dbcarselect;
import com.example.empty.mydb.dbpermission;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Record extends AppCompatActivity {
    Spinner spncar,spndate,spntime;
    ListView listshow;
    String vender="";
    String web="https://vehicle.chansing.com.tw/car/";
    String selA="308-HC",selB="20210705",selC="09";
    String course[]= {"    233-VG","    787-VG","    289-UT","    787-VG","    AAQ-636"};
    String day[]={"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
    String countdate[]={"","",""};
    String[] Balls= new String[] {"CH1","CH2","CH3","CH4"};
    ArrayList<Team> teams = new ArrayList<Team>();
    ArrayList<String> data = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
      //  Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        spncar=(Spinner)findViewById(R.id.spncar);
        spndate=(Spinner)findViewById(R.id.spndate);
        spntime=(Spinner)findViewById(R.id.spntime);
        listshow=(ListView)findViewById(R.id.listshow);
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

        spncar.setAdapter(choosespn);
        spncar.setOnItemSelectedListener(spncarbtn);
////dayspn
        ArrayAdapter<String> choosedayspn=new ArrayAdapter<String>(this,android.R.layout.
                simple_spinner_dropdown_item,day);

        spntime.setAdapter(choosedayspn);
        spntime.setOnItemSelectedListener(spntimebtn);
////date spn
        Date dNow = new Date( );
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        Date date_1=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date_1);
        calendar.add(calendar.DATE, -1);
        date_1 = calendar.getTime();

        /////
        Date date_2=new Date();
        calendar.setTime(date_2);
        calendar.add(calendar.DATE, -2);
        date_2 = calendar.getTime();

        countdate[0]=format.format(dNow)+"";
        countdate[1]=format.format(date_1)+"";
        countdate[2]=format.format(date_2)+"";
        ArrayAdapter<String> choosedate=new ArrayAdapter<String>(this,android.R.layout.
                simple_spinner_dropdown_item,countdate);

        spndate.setAdapter(choosedate);
        spndate.setOnItemSelectedListener(spndatebtn);
        /////////////
/*
        ArrayAdapter<String> adapterBalls=new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,Balls);
        listshow.setAdapter(adapterBalls);


*/
        Team  team = new Team("" );
        teams.add(team);
        teams.clear();
        selA=selA.trim();

    final TeamsAdapter adapter = new TeamsAdapter(this, R.layout.team, teams);
        listshow.setAdapter(adapter);
        listshow.setTextFilterEnabled(true);
        listshow.setOnItemClickListener(lstPreferListener);
///BottomNavigationView
        BottomNavigationView nav_view=(BottomNavigationView)findViewById(R.id.nav_view);
        nav_view.setSelectedItemId(R.id.video);
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
        //////finish//////

    }
    private Spinner.OnItemSelectedListener spncarbtn=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    selA=parent.getSelectedItem().toString();
                    selA=selA.trim();
                    find_connect();

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };
    private Spinner.OnItemSelectedListener spndatebtn=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    selB=parent.getSelectedItem().toString();
                    find_connect();

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };


    private Spinner.OnItemSelectedListener spntimebtn=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    selC=parent.getSelectedItem().toString();
                    find_connect();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };

    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    // 顯示 ListView 的選項內容
                    String tmp="";
                    if(data.get(position).contains("CH1")){tmp="CH1";}
                    else if(data.get(position).contains("CH2")){tmp="CH2";}
                    else if(data.get(position).contains("CH3")){tmp="CH3";}
                    else if(data.get(position).contains("CH4")){tmp="CH4";}

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(web+selA+"/"+selB+"/"+selC+"/"+tmp+"/"+data.get(position))));
                   // mytoast(data.get(position));
                }
            };
    public void find_connect(){
        try {
            URL url = new URL(web+selA+"/"+selB+"/"+selC);
           // mytoast(web+selA+"/"+selB+"/"+selC);
         //   mytoast(web+selA+"/"+selB+"/"+selC);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            int code = connection.getResponseCode();
            String result = dbcarselect.executeQuery(selA,selB,selC);

            if(code == 200) {
                teams.clear();
                data.clear();
                String sel_channel[]=result.split(":");
               // mytoast(sel_channel[0]);
                String sel_ch1[]=sel_channel[0].split(",");
                for(int i=0;i<sel_ch1.length-1;i++){

                    Team  team = new Team(sel_ch1[i]);
                    teams.add(team);
                    data.add(sel_ch1[i]);
                }
                String sel_ch2[]=sel_channel[1].split(",");
                for(int i=0;i<sel_ch2.length-1;i++){

                    Team  team = new Team(sel_ch2[i]);
                    teams.add(team);
                    data.add(sel_ch2[i]);
                }
                String sel_ch3[]=sel_channel[2].split(",");
                for(int i=0;i<sel_ch3.length-1;i++){

                    Team  team = new Team(sel_ch3[i]);
                    teams.add(team);
                    data.add(sel_ch3[i]);
                }
                String sel_ch4[]=sel_channel[3].split(",");
                for(int i=0;i<sel_ch4.length-1;i++){

                    Team  team = new Team(sel_ch4[i]);
                    teams.add(team);
                    data.add(sel_ch4[i]);
                }
            }
            else{
                teams.clear();
                data.clear();
                Team  team = new Team("無此筆資料");
                teams.add(team);
            }
            final TeamsAdapter adapter = new TeamsAdapter(this, R.layout.team, teams);
            listshow.setAdapter(adapter);
        }
        catch (IOException e) { }
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}