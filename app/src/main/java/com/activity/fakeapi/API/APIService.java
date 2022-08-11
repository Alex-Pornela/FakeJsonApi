package com.activity.fakeapi.API;

import com.activity.fakeapi.Model.PhotoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("/photos")
    Call<List<PhotoModel>> getPhotoList();


}
