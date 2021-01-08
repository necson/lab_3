package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //объявление переменных
        TextView tv_origin=findViewById(R.id.origin_tv);
        TextView description_tv=findViewById(R.id.description_tv);
        TextView ingredients_tv=findViewById(R.id.ingredients_tv);
        TextView also_know_tv=findViewById(R.id.also_known_tv);
        //Задаем текст переменным
        tv_origin.setText(sandwich.getPlaceOfOrigin());
        description_tv.setText(sandwich.getDescription());
        //заполнение переменной массивом
        for(String string: sandwich.getIngredients()){
            ingredients_tv.append(string + "\n");
        }
        //проверка на пустоту массива
        if(sandwich.getAlsoKnownAs().size()!=0) {
            for (String string : sandwich.getAlsoKnownAs()) {
                also_know_tv.append(string + "\n");
            }
            //задаем заглавные буквы
            also_know_tv.setText(also_know_tv.getText().toString().substring(0, also_know_tv.getText().length() - 1));
        } else {
            also_know_tv.setVisibility(View.GONE);
        }
    }
}
