package com.example.iti_mvc.favMovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.iti_mvc.model.Movies;
import com.example.iti_mvc.model.RepositoryInterface;

import java.util.List;

public class FavMoviesViewModel extends ViewModel {
    private RepositoryInterface repositoryInterface;

    public FavMoviesViewModel(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }
//=======================================================
    public void deleteMovieFromFav(Movies movies) {
        repositoryInterface.deleteMovie(movies);
    }

    public LiveData<List<Movies>> getMovies() {
        return repositoryInterface.getFavMovies();
    }
    public void insertMovie(Movies movies){
        repositoryInterface.insertMovie(movies);
    }


}
