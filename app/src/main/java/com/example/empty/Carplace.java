package com.example.empty;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empty.mydb.dbcarname;
import com.example.empty.mydb.dbmore;
import com.example.empty.mydb.dbpermission;
import com.example.empty.mydb.dbperson;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Carplace extends AppCompatActivity {
    WebView myweb;
    String  vender="";
    String account="",passwd="",names="",course_num="";
    TextView moreinfo;
    Spinner choose;
    Switch swall;
    String sel="KLE-5592";
    private Menu menu;
    boolean tf=false;
    int pos=0;
    Dialog dia;
    String course[]= {"    KLE-5592" ,"    788-UG","    785-UG ","    233-VG","    787-VG"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carplace);
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
        swall = findViewById(R.id.switch_all);
        choose=(Spinner)findViewById(R.id.choose);
        moreinfo=(TextView) findViewById(R.id.moreinfo);

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
        moreinfo.setOnClickListener(moreinfobtn);

        swall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(Carplace.this, "Sw1: "+isChecked, Toast.LENGTH_SHORT).show();
                tf=isChecked;
                sel=sel.replaceAll("\\s","");
                if(!tf){
                    myweb.loadUrl("http://vehicle.chansing.com.tw/car/map1.php?num="+sel);
                }

                else{
                    myweb.loadUrl("http://vehicle.chansing.com.tw/car/map3.php?num="+sel);
                }
            }
        });


        BottomNavigationView nav_view=(BottomNavigationView)findViewById(R.id.nav_view);
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
                        /*
                    case R.id.reply:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;

                         */
                }
                return false;
            }
        });

    }
    private Button.OnClickListener backbtn=new Button.OnClickListener(){//軌跡
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Carplace.this, Home.class);
            startActivity(intent);
        }
    };
    private TextView.OnClickListener moreinfobtn=new TextView.OnClickListener(){
        @Override
        public void onClick(View v) {
            dia = new Dialog(Carplace.this, R.style.edit_AlertDialog_style);
            dia.setContentView(R.layout.showtxt);

            TextView car_num = (TextView) dia.findViewById(R.id.car_num);
            TextView person = (TextView) dia.findViewById(R.id.person);
            TextView lat = (TextView) dia.findViewById(R.id.lat);
            TextView lot = (TextView) dia.findViewById(R.id.lot);
            TextView speed = (TextView) dia.findViewById(R.id.speed);
            TextView dirct = (TextView) dia.findViewById(R.id.dirct);
            Button close=(Button)dia.findViewById(R.id.close);
           close.setOnClickListener(
                   new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           dia.dismiss();
                       }
                   }
           );
            String result = dbperson.executeQuery(sel);
            try {
            JSONArray jsonArray = new JSONArray(result);
                String driver="";
                for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
                {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                     driver=jsonData.getString("driver");

                }
                String a= dbmore.executeQuery(sel);
                String b[]=a.split(",");
 /**/           b[0]= b[0].replaceAll("\n","");
                b[0]= b[0].replaceAll(" ","");
                car_num.setText("車牌:"+sel);
                person.setText("駕駛:"+driver);
                lat.setText("經度:"+b[0]);
                lot.setText("緯度:"+b[1]);
                speed.setText("速度:"+b[2]);
                dirct.setText("方向:"+b[3]);
                //   dia.setCanceledOnTouchOutside(false); // Sets whether this dialog is
                Window w = dia.getWindow();
                WindowManager.LayoutParams lp = w.getAttributes();
                lp.x = 0;
                lp.y = 20;

                lp.width = 600;


                dia.show();
                dia.onWindowAttributesChanged(lp);

            } catch (Exception e) {}
        }
    };
    private Spinner.OnItemSelectedListener chbtn= new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v,
                                   int position, long id) {
           int tmp=position+1;
           pos=position;
           sel=parent.getSelectedItem().toString();
            sel=sel.replaceAll("\\s","");
            if(!tf){
                myweb.loadUrl("http://vehicle.chansing.com.tw/car/map1.php?num="+sel);

            }

            else{
                myweb.loadUrl("http://vehicle.chansing.com.tw/car/map3.php?num="+sel);

            }


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
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}