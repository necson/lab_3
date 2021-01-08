package com.example.photogallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.photogallery.api.FlickrAPI;
import com.example.photogallery.api.ServiceAPI;
import com.example.photogallery.db.PhotosDB;
import com.example.photogallery.db.PhotosDao;
import com.example.photogallery.model.Photo;
import com.example.photogallery.model.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class PhotoGallery extends AppCompatActivity {

    PhotoAdapter adapter;
    RecyclerView r_view;
    Response respon;
    Context context;
    PhotosDB DB;
    PhotosDao DAO;
    List<Photo> ph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
        r_view = findViewById(R.id.r_view);
        r_view.setLayoutManager(new GridLayoutManager(this,3));
        context = this;
        DB = Room.databaseBuilder(context,PhotosDB.class,"database").allowMainThreadQueries().build();
        DAO = DB.photoDao();
        Retrofit retrofit = ServiceAPI.getRetrofit();
        retrofit.create(FlickrAPI.class) .getRecent().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                respon = response.body();
                List<Photo> ph = respon.getPhotos().getPhoto();
                adapter = new PhotoAdapter(ph,context,DAO);
                r_view.setAdapter(adapter);

                Toast.makeText(PhotoGallery.this, "GOOD REQUEST",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(PhotoGallery.this, "BAD REQUEST",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view,menu);
        MenuItem searchItem = menu.findItem(R.id.Item_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        r_view = findViewById(R.id.r_view);
        r_view.setLayoutManager(new GridLayoutManager(this,3));
        context = this;
        final Retrofit retrofit = ServiceAPI.getRetrofit();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                retrofit.create(FlickrAPI.class).getSearchPhotos(query).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        respon = response.body();
                        List<Photo> ph = respon.getPhotos().getPhoto();
                        adapter = new PhotoAdapter(ph,context,DAO);
                        r_view.setAdapter(adapter);

                        Toast.makeText(PhotoGallery.this, "GOOD REQUEST",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.OnlineDB:
                Retrofit retrofit = ServiceAPI.getRetrofit();
                retrofit.create(FlickrAPI.class) .getRecent().enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        respon = response.body();
                        List<Photo> ph = respon.getPhotos().getPhoto();
                        adapter = new PhotoAdapter(ph,context,DAO);
                        r_view.setAdapter(adapter);

                        Toast.makeText(PhotoGallery.this, "GOOD REQUEST",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
                return true;
            case R.id.LocalDB:
                List<Photo> ph = DAO.LoadAll();
                adapter = new PhotoAdapter(ph,context,DAO);
                r_view.setAdapter(adapter);
                return true;
            case R.id.item_delete:
                ph = DAO.LoadAll();
                for (int i = 0; i < ph.size(); i++ ){
                    Photo z = ph.get(i);
                    DAO.deletePhoto(z);
                }
                ph = DAO.LoadAll();
                adapter = new PhotoAdapter(ph,context,DAO);
                r_view.setAdapter(adapter);
                return true; 
        }

        return super.onOptionsItemSelected(item);
    }
}