package com.example.iti_mvc.favMovies.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.iti_mvc.model.RepositoryInterface;

public class FavMoviesViewModelFactory implements ViewModelProvider.Factory {
    private RepositoryInterface repositoryInterface;

    public FavMoviesViewModelFactory(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(FavMoviesViewModel.class)){
            return (T) new FavMoviesViewModel(repositoryInterface);
        }else{
            throw new IllegalArgumentException("View Model class Not found");
        }

    }
}
