package com.example.tmdb.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.tmdb.Adapter.GenreListAdapter;
import com.example.tmdb.Model.GenreModel;
import com.example.tmdb.Model.GetGenreResponseModel;
import com.example.tmdb.R;
import com.example.tmdb.Retrofit.ApiInterface;
import com.example.tmdb.Retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreFragment extends Fragment {
    GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genre, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.gv_genre_list);

        ApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<GetGenreResponseModel> call = apiInterface.getGenreList(RetrofitInstance.API_KEY);
        call.enqueue(new Callback<GetGenreResponseModel>() {
            @Override
            public void onResponse(Call<GetGenreResponseModel> call, Response<GetGenreResponseModel> response) {
                GetGenreResponseModel getGenreResponseModel = response.body();
                List<GenreModel> genreModelList = getGenreResponseModel.getGenreModels();
                GenreListAdapter genreListAdapter = new GenreListAdapter(genreModelList, getActivity());
                gridView.setAdapter(genreListAdapter);
            }

            @Override
            public void onFailure(Call<GetGenreResponseModel> call, Throwable t) {

            }
        });
    }
}