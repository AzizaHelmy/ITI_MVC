package com.example.iti_mvc.network;

import androidx.lifecycle.LiveData;

import com.example.iti_mvc.model.Movies;

import java.util.List;

public interface RemoteSource {
LiveData<List<Movies>> enqueueCall(NetworkDelegate networkDelegate);
}
