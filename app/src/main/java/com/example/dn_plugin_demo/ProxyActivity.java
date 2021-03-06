package com.example.dn_plugin_demo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.plugin_common.PayInterfaceActivity;

import java.lang.reflect.Constructor;

public class ProxyActivity extends Activity {

    //需要加载插件的全类名
    private String className;
    PayInterfaceActivity payInterfaceActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        className = getIntent().getStringExtra("className");

        try {
            //TaoMainActivity
            Class<?> aClass = getClassLoader().loadClass(className);
            Constructor constructor = aClass.getConstructor(new Class[]{});
            Object in = constructor.newInstance(new Object[]{});
            payInterfaceActivity = (PayInterfaceActivity) in;
            payInterfaceActivity.attach(this);

            //如果需要参数，可以使用bundle
            Bundle bundle = new Bundle();
            payInterfaceActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //重写加载插件中的类
    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    //重写加载插件中的资源
    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        Intent m = new Intent(this,ProxyActivity.class);
        m.putExtra("className",className);
        super.startActivity(m);
    }

    @Override
    protected void onStart() {
        super.onStart();
        payInterfaceActivity.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        payInterfaceActivity.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        payInterfaceActivity.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payInterfaceActivity.onDestory();
    }
}
