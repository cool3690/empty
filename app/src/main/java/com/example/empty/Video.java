package com.example.empty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empty.mydb.dbcarname;
import com.example.empty.mydb.dbpermission;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class Video extends AppCompatActivity {
    String account="",passwd="",vender="";
    Spinner choose;
    Button ok;
    LinearLayout L0;
    int x=0;
    WebView ch1,ch2,ch3,ch4,ch5;
    Button bt1,bt2,bt4;
    String sel="";
    Timer timer = new Timer();
    String course[]= {"    233-VG","    787-VG","    289-UT","    787-VG","    AAQ-636"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);

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
      /*  account= Account.getAccount();
        passwd=Account.getPasswd();
        vender=Account.getVendor_id();

       */
        choose=(Spinner)findViewById(R.id.choose);
        // ok=(Button)findViewById(R.id.ok);
        ch1=(WebView)findViewById(R.id.ch1);
        ch2=(WebView)findViewById(R.id.ch2);
        ch3=(WebView)findViewById(R.id.ch3);
        ch4=(WebView)findViewById(R.id.ch4);
        bt1=(Button)findViewById(R.id.bt1);
        bt2=(Button)findViewById(R.id.bt2);
        bt4=(Button)findViewById(R.id.bt4);
        L0=(LinearLayout)findViewById(R.id.L0);
        ch5=(WebView)findViewById(R.id.ch5);
        L0.setVisibility(View.GONE);
        bt1.setOnClickListener(bt1A);
        bt2.setOnClickListener(bt2B);
        bt4.setOnClickListener(bt4C);
        String result = dbpermission.executeQuery(vender);
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

/**/

        ArrayAdapter<String> choosespn=new ArrayAdapter<String>(this,android.R.layout.
                simple_spinner_dropdown_item,course);

        choose.setAdapter(choosespn);

        choose.setOnItemSelectedListener(chbtn);
      //  begin();
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
    }
    private Button.OnClickListener bt1A=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            L0.setVisibility(View.VISIBLE);
            L0.bringToFront();
        }
    };
    private Button.OnClickListener bt2B=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            ch3.setVisibility(View.GONE);
            ch4.setVisibility(View.GONE);
            L0.setVisibility(View.GONE);
        }
    };
    private Button.OnClickListener bt4C=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            L0.setVisibility(View.GONE);
            ch3.setVisibility(View.VISIBLE);
            ch4.setVisibility(View.VISIBLE);
        }
    };
    private Spinner.OnItemSelectedListener chbtn=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    TextView textView = (TextView) v;
                     ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    x=0;
                    sel=parent.getSelectedItem().toString();
                  show(sel);

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };

        public void show(String sel){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date=new Date();
            String dts=sdf.format(date);
            dts="20210624";
            SimpleDateFormat t=new SimpleDateFormat("HH");
            Date date2=new Date();
            String t2=t.format(date2);
            t2="13";
            String a= dbcarname.executeQuery(sel.trim());
           // mytoast(a+"");
           // ch1.loadUrl("http://52.155.115.220:6061/ProcessedFile/testVideo/20210622/15/CH1/Converted/testVideo_20210622_1533_CH1.mp4");

            if(a!=null){
                String b[]=a.split(":");

                if(b.length>0){
                    String c[]=b[0].split(",");
                    if(x<c.length){
                        //  mytoast("http://52.155.115.220:6061/ProcessedFile/"+sel.trim()+"/"+dts+"/"+t2+"/CH1/Converted/"+c[x]);
                        // sel.trim();
                        ch1.loadUrl("http://52.155.115.220:6061/ProcessedFile/"+sel.trim()+"/"+dts+"/"+t2+"/CH1/Converted/"+c[x]);
                        ch5.loadUrl("http://52.155.115.220:6061/ProcessedFile/"+sel.trim()+"/"+dts+"/"+t2+"/CH1/Converted/"+c[x]);
                    }
                }
                if(b.length>1) {
                    String d[] = b[1].split(",");
                    if(x<d.length){
                        ch2.loadUrl("http://52.155.115.220:6061/ProcessedFile/"+sel.trim()+"/"+dts+"/"+t2+"/CH2/Converted/"+d[x]);
                        //ch2.loadUrl("https://chansing.com.tw/car/video/"+sel+"/"+dts+"/"+t2+"/CH2/"+d[x]);
                    }
                }
                if(b.length>2) {
                    String e[]=b[2].split(",");
                    if(x<e.length){

                        ch3.loadUrl("http://52.155.115.220:6061/ProcessedFile/"+sel.trim()+"/"+dts+"/"+t2+"/CH3/Converted/"+e[x]);

                    }
                }
                if(b.length>3) {
                    String f[]=b[3].split(",");
                    if(x<f.length){
                        ch4.loadUrl("http://52.155.115.220:6061/ProcessedFile/"+sel.trim()+"/"+dts+"/"+t2+"/CH4/Converted/"+f[x]);

                    }
                }
                
                //////////////////////
            }
             /**/


        } /*
        public void begin() {
            timer.schedule(task, 1000, 1000) ;       }

           TimerTask task = new TimerTask() {
               @Override
               public void run() {

                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {

                           secondLeft--;

                           if (secondLeft < 1) {
                               secondLeft=180;
                               x++;
                               show(sel);
                           }
                       }
                   });
               }
           };
          */
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
