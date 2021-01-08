package com.example.photogallery.api;

import com.example.photogallery.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrAPI {
    @GET("services/rest/?method=flickr.photos.getRecent&api_key=03f8a99a51f22fd7a4163dfb21f0d341&extras=url_s&format=json&nojsoncallback=1")
            Call<Response> getRecent();
        @GET("services/rest/?method=flickr.photos.search&api_key=03f8a99a51f22fd7a4163dfb21f0d341&extras=url_s&format=json&nojsoncallback=1")
        Call<Response> getSearchPhotos(@Query("text") String keyWord);
}

