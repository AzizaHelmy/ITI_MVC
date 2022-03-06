package com.example.iti_mvc.db;

import androidx.lifecycle.LiveData;

import com.example.iti_mvc.model.Movies;

import java.util.List;

public interface LocalSource {
    void deleteMovie(Movies movie);
    void insertMovie(Movies movie);
    LiveData<List<Movies>> getAllFavMovies();
}
