package com.example.recyclerapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);
            Bundle arguments = getIntent().getExtras();
            String name = arguments.get("ARG_ITEM_ID").toString();

            TextView textView = findViewById(R.id.details);
            textView.setText(name);
    }
}