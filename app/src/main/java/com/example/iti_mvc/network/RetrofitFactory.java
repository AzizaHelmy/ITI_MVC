package com.example.iti_mvc.network;


import androidx.lifecycle.LiveData;

import com.example.iti_mvc.model.Movies;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory implements RemoteSource {
    static final String url = "https://api.androidhive.info/";
    private static RetrofitFactory retrofitFactory = null;

    public static RetrofitFactory getInstance() {
        if (retrofitFactory == null) {
            retrofitFactory = new RetrofitFactory();
        }
        return retrofitFactory;
    }

    public RetrofitFactory() {
    }
//============================================================

    @Override
    public LiveData<List<Movies>> enqueueCall(NetworkDelegate networkDelegate) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesInterface moviesInterface = retrofit.create(MoviesInterface.class);
        Call<List<Movies>> listCall = moviesInterface.getAllMovies();
        listCall.enqueue(new Callback<List<Movies>>() {
            @Override
            public void onResponse(Call<List<Movies>> call, Response<List<Movies>> response) {
                if (response.isSuccessful()) {
                    networkDelegate.onSuccessResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Movies>> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
        return null;
    }
}
