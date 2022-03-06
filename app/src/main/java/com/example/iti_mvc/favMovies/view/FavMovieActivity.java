package com.example.iti_mvc.favMovies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.iti_mvc.R;
import com.example.iti_mvc.db.ConcretLocalSource;
import com.example.iti_mvc.favMovies.viewmodel.FavMoviesViewModel;
import com.example.iti_mvc.favMovies.viewmodel.FavMoviesViewModelFactory;
import com.example.iti_mvc.model.Movies;
import com.example.iti_mvc.model.Repository;
import com.example.iti_mvc.network.RetrofitFactory;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FavMovieActivity extends AppCompatActivity implements OnFavClickListener {
    RecyclerView recyclerFav;
    FavMovieAdapter favMovieAdapter;
    List<Movies> movies;
    ImageView imgHolderEmpty;
    ConstraintLayout favLayout;
    FavMoviesViewModelFactory favMoviesViewModelFactory;
    FavMoviesViewModel favMoviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_movie);
        setUpRecyclerView();
        //checkForFavMovies();
        favMoviesViewModelFactory = new FavMoviesViewModelFactory(Repository.getInstance(this, ConcretLocalSource.getInstance(FavMovieActivity.this), RetrofitFactory.getInstance()));
        favMoviesViewModel = new ViewModelProvider(this, favMoviesViewModelFactory).get(FavMoviesViewModel.class);
        favMoviesViewModel.getMovies().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(List<Movies> movies) {
                favMovieAdapter.setList(movies);
            }
        });
    }

    //===============================================================
    private void setUpRecyclerView() {
        recyclerFav = findViewById(R.id.rv_fav_movie);
        imgHolderEmpty = findViewById(R.id.empty_iv);
        favLayout = findViewById(R.id.fav_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerFav.setLayoutManager(layoutManager);
        favMovieAdapter = new FavMovieAdapter(FavMovieActivity.this, new ArrayList<Movies>(), this);
        recyclerFav.setAdapter(favMovieAdapter);
    }

    //===============================================================
    @SuppressLint("ResourceAsColor")
    @Override
    public void onFavClick(Movies movie, ImageView favImg) {
        //delet from repo
        favMoviesViewModel.deleteMovieFromFav(movie);
        Snackbar snackbar = Snackbar.make(favLayout, "Removed from Fav!", Snackbar.LENGTH_LONG);
        snackbar.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        snackbar.setActionTextColor(ContextCompat.getColor(getBaseContext(), R.color.red));
        snackbar.setBackgroundTint(ContextCompat.getColor(getBaseContext(), R.color.gry));
        snackbar.setAction("undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favMoviesViewModel.insertMovie(movie);
                Toast.makeText(FavMovieActivity.this, "added Again!", Toast.LENGTH_SHORT).show();
                favImg.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
        }).show();
        favMovieAdapter.notifyDataSetChanged();
        checkForFavMovies();
    }

    //===============================================
    private void checkForFavMovies() {

        if (favMoviesViewModel.getMovies().getValue().isEmpty()) {
            recyclerFav.setVisibility(View.GONE);
            imgHolderEmpty.setVisibility(View.VISIBLE);
        } else {
            imgHolderEmpty.setVisibility(View.GONE);
            recyclerFav.setVisibility(View.VISIBLE);
        }
    }
    //=======================================

}