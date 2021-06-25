package com.example.dn_plugin_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void load(View view) {
        loadPlugin();
    }

    private void loadPlugin() {
        //拷贝apk到指定目录
        File fileDir = this.getDir("plugin", Context.MODE_PRIVATE);
        String name = "plugin.apk";
        String filePath = new File(fileDir, name).getAbsolutePath();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        InputStream is = null;
        FileOutputStream os = null;
        try {
            Log.i(TAG, "加载插件" + new File(Environment.getExternalStorageDirectory(), name));
            is = new FileInputStream(new File(Environment.getExternalStorageDirectory(), name));
            os = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            File f = new File(filePath);
            if (f.exists()) {
                Toast.makeText(this, "dex overwrite", Toast.LENGTH_LONG).show();
            }

            PluginManager.getInstance().loadPath(this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void click(View view) {
        Intent intent = new Intent(this, ProxyActivity.class);
        //跳转插件中的activity
        intent.putExtra("className", PluginManager.getInstance().getPackageInfo().activities[0].name);
        startActivity(intent);
    }
}