package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Sandwich> SandwichList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] sandList = getResources().getStringArray(R.array.sandwich_details);
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);
        RecyclerView rv_view=findViewById(R.id.Rview);

        SandwichList = new ArrayList<>();
        for (int i = 0; i < sandwiches.length; i++) {
            String json = sandList[i];
            Sandwich sandwich = null;
            try {
                sandwich = JsonUtils.parseSandwichJson(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SandwichList.add(sandwich);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_view.setLayoutManager(mLayoutManager);

        RVadapter adapter = new RVadapter(SandwichList);
        rv_view.setAdapter(adapter);

    }

}
