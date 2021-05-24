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

    int x=0;
    WebView ch1,ch2;
    String sel="";
    Timer timer = new Timer();
    String course[]= {"052-QK","    787-VG","    289-UT","    787-VG","    AAQ-636"};
    private int secondLeft = 180;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  GlobalVariable Account = (GlobalVariable)getApplicationContext();
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
        // ch1.setVisibility(View.GONE);
      //  ch2.setVisibility(View.GONE);
        /*
        String result = dbpermission.executeQuery(vender);
        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
             if(jsonArray.length()>0){
              Balls= new String[jsonArray.length()];
                for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
                     {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                         String license_plate=jsonData.getString("license_plate");
                            //revise
                         Balls[i]=license_plate;

                     }
            }



        }

        catch(Exception e){}

*/

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
            dts="20210420";
            SimpleDateFormat t=new SimpleDateFormat("HH");
            Date date2=new Date();
            String t2=t.format(date2);
            t2="03";
            String a= dbcarname.executeQuery(sel);
           // mytoast(a+"");

            if(!a.contains(" ") ||a!=null){
                String b[]=a.split(":");
                String c[]=b[0].split(",");
                String d[]=b[1].split(",");

                if(x<c.length){
                    ch1.loadUrl("https://chansing.com.tw/car/video/"+sel+"/"+dts+"/"+t2+"/CH1/"+c[x]);

                }
                if(x<d.length){
                    ch2.loadUrl("https://chansing.com.tw/car/video/"+sel+"/"+dts+"/"+t2+"/CH2/"+d[x]);
                }
            }

             /* */

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
