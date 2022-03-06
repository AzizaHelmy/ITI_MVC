package com.example.iti_mvc.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.iti_mvc.model.Movies;

import java.util.List;

public class ConcretLocalSource implements LocalSource {
    private MovieDAO movieDAO;
    private LiveData<List<Movies>> storedMovies;
    private static ConcretLocalSource localSource = null;

    public ConcretLocalSource(Context context) {
        MovieDataBase dataBase = MovieDataBase.getInstance(context.getApplicationContext());
        movieDAO = dataBase.movieDAO();
        storedMovies = movieDAO.getAllFavMovies();
    }

    public static ConcretLocalSource getInstance(Context context) {
        if (localSource == null) {
            localSource = new ConcretLocalSource(context);
        }
        return localSource;
    }

    @Override
    public void deleteMovie(Movies movie) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieDAO.deleteMovie(movie);
            }
        }).start();
    }

    @Override
    public void insertMovie(Movies movie) {
        System.out.println("in insert");
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieDAO.insertMovie(movie);
            }
        }).start();
    }

    @Override
    public LiveData<List<Movies>> getAllFavMovies() {
        return storedMovies;
    }
}
