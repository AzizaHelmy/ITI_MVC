package com.example.iti_mvc.allMovies.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.iti_mvc.model.RepositoryInterface;

public class MoviesViewModelFactory implements ViewModelProvider.Factory {
    private RepositoryInterface repositoryInterface;

    public MoviesViewModelFactory(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AllMoviesViewModel.class)){
            return (T) new AllMoviesViewModel(repositoryInterface);
        }else{
            throw new IllegalArgumentException("View Model class Not found");
        }

    }
}
