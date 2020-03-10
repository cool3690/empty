package com.example.empty;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;

public class Write {
    private static Context context;

    public Write(Context context) {
        this.context = context;
    }

    public static void WriteFileExample(String message) {
        FileOutputStream fop = null;
        File file;
        String content = message;

        try {
            File sdcard = Environment.getExternalStorageDirectory();

            file = new File(sdcard, "myLog.txt"); //輸出檔案位置
        //    Log.i("Write File:", file + "");


            if (!file.exists()) { // 如果檔案不存在，建立檔案
                file.createNewFile();
            }
            // fop = new FileOutputStream(file);
           fop =new  FileOutputStream(file,true);
            byte[] contentInBytes = content.getBytes();// 取的字串內容bytes

            fop.write(contentInBytes); //輸出
           fop.write("\r\n".getBytes());

            //Runtime.getRuntime().exec("logcat -v time -f " +contentInBytes);
          //  Toast.makeText(context, "輸出完成", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
           // Log.i("Write E:", e + "");
           // e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
               // Log.i("Write IOException", e + "");
                //e.printStackTrace();
            }
        }
    }
}