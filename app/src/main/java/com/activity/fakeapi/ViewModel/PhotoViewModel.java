package com.activity.fakeapi.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.activity.fakeapi.API.APIService;
import com.activity.fakeapi.API.RetrofitInstance;
import com.activity.fakeapi.Model.PhotoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoViewModel extends ViewModel {

    public MutableLiveData<List<PhotoModel>> photoList;

    public PhotoViewModel(){
        photoList = new MutableLiveData<>();
    }

    public MutableLiveData<List<PhotoModel>> getPhotoList(){
        return photoList;
    }

    public void makeApiCall(){
        APIService apiService = RetrofitInstance.getRetrofitInstance().create( APIService.class );
        Call<List<PhotoModel>> call = apiService.getPhotoList();
        call.enqueue( new Callback<List<PhotoModel>>() {
            @Override
            public void onResponse(Call<List<PhotoModel>> call, Response<List<PhotoModel>> response) {
                photoList.postValue( response.body() );
            }

            @Override
            public void onFailure(Call<List<PhotoModel>> call, Throwable t) {
                Log.e("Error: ", t.getMessage() );
            }
        } );

    }

}
