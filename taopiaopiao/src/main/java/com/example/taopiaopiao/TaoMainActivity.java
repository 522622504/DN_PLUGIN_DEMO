package com.example.taopiaopiao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TaoMainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(that, "插件", Toast.LENGTH_LONG).show();
                startActivity(new Intent(that, SecondActivity.class));
            }
        });
    }
}