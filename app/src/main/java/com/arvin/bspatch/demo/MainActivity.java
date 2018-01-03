package com.arvin.bspatch.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arvin.bspatch.BSPatch;
import com.arvin.bspatch.demo.R;

import java.io.File;

public class MainActivity extends AppCompatActivity implements BSPatch.Callback {

    // Used to load the 'native-lib' library on application startup.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method


    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"hahahah",0).show();
    }

    public void merge(View view) {
        try {
            BSPatch.bspatch(this, Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/demo_new.apk", Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/apk.patch",this);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"發生錯誤啦",0).show();
        }

    }

    @Override
    public void mergeResult(int result) {
        File apkfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/demo_new.apk");
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        startActivity(i);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

}
