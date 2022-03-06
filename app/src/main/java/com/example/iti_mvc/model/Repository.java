package com.example.iti_mvc.model;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.iti_mvc.db.LocalSource;
import com.example.iti_mvc.network.NetworkDelegate;
import com.example.iti_mvc.network.RemoteSource;
import com.example.iti_mvc.network.RetrofitFactory;

import java.util.List;

public class Repository implements RepositoryInterface {
    private Context context;
    LocalSource localSource;
    RemoteSource remoteSource;
    private static Repository repository = null;
    RetrofitFactory retrofitFactory;

    public Repository(Context context, LocalSource localSource, RemoteSource remoteSource) {
        this.context = context;
        this.localSource = localSource;
        this.remoteSource = remoteSource;
    }

    public static Repository getInstance(Context context, LocalSource localSource, RemoteSource remoteSource) {
        if (repository == null) {
            repository = new Repository(context, localSource, remoteSource);
        }
        return repository;
    }

    //====================================
    public int isFav(String title) {
        int res = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //res=movieDAO.isFavorite(title);
            }
        }).start();
        return res;
    }

    //====================================
    @Override
    public void deleteMovie(Movies movie) {
       localSource.deleteMovie(movie);
    }

    //==============================================
    @Override
    public void insertMovie(Movies movie) {
       localSource.insertMovie(movie);
    }

    //=================================================
    @Override
    public LiveData<List<Movies>> getFavMovies() {
        return localSource.getAllFavMovies();
    }

    @Override
    public LiveData<List<Movies>> getAllMovies(NetworkDelegate networkDelegate) {
        return remoteSource.enqueueCall(networkDelegate);
    }
}
