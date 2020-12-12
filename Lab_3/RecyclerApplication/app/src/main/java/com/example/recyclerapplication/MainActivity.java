package com.example.recyclerapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         //добавляем RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        //добавляем менеджер слоев, связываем с RecyclerView


        //добавляем адаптор списка
        RViewAdapter rViewAdapter = new RViewAdapter(DummyContent.ITEMS);
        recyclerView.setAdapter(rViewAdapter);
    }
}