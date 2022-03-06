package com.example.iti_mvc.allMovies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.iti_mvc.R;
import com.example.iti_mvc.allMovies.viewmodel.AllMoviesViewModel;
import com.example.iti_mvc.allMovies.viewmodel.MoviesViewModelFactory;
import com.example.iti_mvc.db.ConcretLocalSource;
import com.example.iti_mvc.model.Movies;
import com.example.iti_mvc.model.Repository;
import com.example.iti_mvc.network.NetworkDelegate;
import com.example.iti_mvc.network.RetrofitFactory;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    List<Movies> movies;
    ConstraintLayout constraintLayout;
    private AllMoviesViewModel moviesViewModel;
    MoviesViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.cons);
        recyclerView = findViewById(R.id.rv_movie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        ConcretLocalSource local = ConcretLocalSource.getInstance(MainActivity.this);
        RetrofitFactory remote = RetrofitFactory.getInstance();
        Repository repo = Repository.getInstance(this, local, remote);
        viewModelFactory = new MoviesViewModelFactory(repo);
        moviesViewModel = new ViewModelProvider(this, viewModelFactory).get(AllMoviesViewModel.class);
        moviesViewModel.getAllMovies();
        movieAdapter = new MovieAdapter(MainActivity.this, new ArrayList<>(), this, moviesViewModel);
        recyclerView.setAdapter(movieAdapter);
        moviesViewModel.getOnlineMovies().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(List<Movies> movies) {
                movieAdapter.setList(movies);
                movieAdapter.notifyDataSetChanged();
            }
        });
    }

    //========================================================
    @SuppressLint("ResourceType")
    @Override
    public void onFavClicked(Movies movie, ImageView favImg) {
        if (favImg.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_baseline_favorite_24).getConstantState()) {
            moviesViewModel.deleteMovieFromFav(movie);
            Snackbar snackbar = Snackbar.make(constraintLayout, "Removed from Fav!", Snackbar.LENGTH_LONG);
            snackbar.setAction("undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moviesViewModel.insertMovie(movie);
                    Toast.makeText(MainActivity.this, "added Again!", Toast.LENGTH_SHORT).show();
                    favImg.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
            }).show();

        } else {
            Snackbar snackbar = Snackbar.make(constraintLayout, "added to Fav", Snackbar.LENGTH_LONG);
            moviesViewModel.insertMovie(movie);
            snackbar.setAction("undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moviesViewModel.deleteMovieFromFav(movie);
                    Toast.makeText(MainActivity.this, "Removed Again!", Toast.LENGTH_SHORT).show();
                    favImg.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
            }).show();
        }
        movieAdapter.notifyDataSetChanged();
    }

    //============================================================
    @Override
    public void onShareClicked(Movies movie) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Hey this film is so amazing " + movie.getTitle());
        intent.setType("text/plain");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Share With "));
        }
    }
}