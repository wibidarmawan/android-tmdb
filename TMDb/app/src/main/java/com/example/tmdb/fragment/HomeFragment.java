package com.example.tmdb.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tmdb.Adapter.MovieHorizontalAdapter;
import com.example.tmdb.Model.GetMovieResponseModel;
import com.example.tmdb.Model.MovieModel;
import com.example.tmdb.R;
import com.example.tmdb.Retrofit.ApiInterface;
import com.example.tmdb.Retrofit.RetrofitInstance;
import com.example.tmdb.Retrofit.RetrofitLoginInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView rvTopRated, rvPopular, rvUpcoming;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTopRated = view.findViewById(R.id.rv_top_rated);
        rvPopular = view.findViewById(R.id.rv_popular);
        rvUpcoming = view.findViewById(R.id.rv_upcoming);

        SetLayout(rvTopRated);
        SetLayout(rvPopular);
        SetLayout(rvUpcoming);

        ApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

        Call<GetMovieResponseModel> call = apiInterface.getMovie("top_rated", RetrofitInstance.API_KEY, page);
        call.enqueue(new Callback<GetMovieResponseModel>() {
            @Override
            public void onResponse(Call<GetMovieResponseModel> call, Response<GetMovieResponseModel> response) {
                GetMovieResponseModel getMovieResponseModel = response.body();
                List<MovieModel> movieModelList = getMovieResponseModel.getMovieModels();
                MovieHorizontalAdapter movieHorizontalAdapter = new MovieHorizontalAdapter(getActivity(), movieModelList);
                rvTopRated.setAdapter(movieHorizontalAdapter);
            }

            @Override
            public void onFailure(Call<GetMovieResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<GetMovieResponseModel> callPopular = apiInterface.getMovie("popular", RetrofitInstance.API_KEY, page);
        callPopular.enqueue(new Callback<GetMovieResponseModel>() {
            @Override
            public void onResponse(Call<GetMovieResponseModel> call, Response<GetMovieResponseModel> response) {
                GetMovieResponseModel getMovieResponseModel = response.body();
                List<MovieModel> movieModelList = getMovieResponseModel.getMovieModels();
                MovieHorizontalAdapter movieHorizontalAdapter = new MovieHorizontalAdapter(getActivity(), movieModelList);
                rvPopular.setAdapter(movieHorizontalAdapter);
            }

            @Override
            public void onFailure(Call<GetMovieResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<GetMovieResponseModel> callUpcoming = apiInterface.getMovie("upcoming", RetrofitInstance.API_KEY, page);
        callUpcoming.enqueue(new Callback<GetMovieResponseModel>() {
            @Override
            public void onResponse(Call<GetMovieResponseModel> call, Response<GetMovieResponseModel> response) {
                GetMovieResponseModel getMovieResponseModel = response.body();
                List<MovieModel> movieModelList = getMovieResponseModel.getMovieModels();
                MovieHorizontalAdapter movieHorizontalAdapter = new MovieHorizontalAdapter(getActivity(), movieModelList);
                rvUpcoming.setAdapter(movieHorizontalAdapter);
            }

            @Override
            public void onFailure(Call<GetMovieResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SetLayout(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
    }
}