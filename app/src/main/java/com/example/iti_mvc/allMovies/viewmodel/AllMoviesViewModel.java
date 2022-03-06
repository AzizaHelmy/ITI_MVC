package com.example.iti_mvc.allMovies.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.iti_mvc.model.Movies;
import com.example.iti_mvc.model.RepositoryInterface;
import com.example.iti_mvc.network.NetworkDelegate;

import java.util.List;

public class AllMoviesViewModel extends ViewModel implements NetworkDelegate {
    private static final String TAG = "TAG";
    private RepositoryInterface repositoryInterface;

    private MutableLiveData<List<Movies>> movieList;
    private MutableLiveData<String> errorMsg;

    public AllMoviesViewModel(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
        movieList = new MutableLiveData<>();
        errorMsg = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared: ViewModel Cleared");
    }

    //================================================
    public void getAllMovies() {
        repositoryInterface.getAllMovies(this);
    }

    public LiveData<List<Movies>> getOnlineMovies() {
        return movieList;
    }
    public LiveData<String> getOnlineMovieserror() {
        return errorMsg;
    }

    public void deleteMovieFromFav(Movies movies) {
        repositoryInterface.deleteMovie(movies);

    }

    public void insertMovie(Movies movies) {
        repositoryInterface.insertMovie(movies);
    }


    //=====================================================
    @Override
    public void onSuccessResult(List<Movies> movies) {
        movieList.postValue(movies);
    }

    @Override
    public void onFailureResult(String movies) {

        errorMsg.postValue(movies);
    }
}
