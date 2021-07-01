package com.example.empty;

import android.content.Intent;
import android.os.Bundle;

import com.example.empty.mydb.dbpermission;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main extends AppCompatActivity {
    EditText acc,pwd;
    Button login;
    String account="",password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      //  Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

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
        acc=(EditText)findViewById(R.id.acc);
        pwd=(EditText)findViewById(R.id.pwd);
        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(loginbtn);

    }
    private Button.OnClickListener loginbtn=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
             account=acc.getText().toString();
             password=pwd.getText().toString();
            String result = dbpermission.executeQuery("0",account,password);
            mytoast(result);
            try{
                JSONArray jsonArray = new JSONArray(result);
                if(jsonArray.length()>0){
                    startActivity(new Intent(getApplicationContext(),Carplace.class));
                }
            }

            catch(Exception e){}
        }
    };
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}