package com.example.tmdb.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tmdb.Adapter.MovieListAdapter;
import com.example.tmdb.Model.GetMovieResponseModel;
import com.example.tmdb.Model.MovieModel;
import com.example.tmdb.R;
import com.example.tmdb.Retrofit.ApiInterface;
import com.example.tmdb.Retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private SearchView searchView;
    RecyclerView recyclerView;
    int page = 1;
    private Boolean isLoading;
    private int totalPage;
    MovieListAdapter movieListAdapter;
    RelativeLayout rl_progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rv_search);
        rl_progressBar = view.findViewById(R.id.rl_search_pg_background);
        rl_progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_atas, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() > 1) {
                    showLoading(true);
                    page = 1;
                    doLoadData(newText);
                    initListener(newText);
                }
                return false;
            }
        });
    }

    private void doLoadData(String searchQuery) {
        ApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<GetMovieResponseModel> call = apiInterface.searchMovie(searchQuery, RetrofitInstance.API_KEY, page);
        call.enqueue(new Callback<GetMovieResponseModel>() {
            @Override
            public void onResponse(Call<GetMovieResponseModel> call, Response<GetMovieResponseModel> response) {
                GetMovieResponseModel getMovieResponseModel = response.body();
                List<MovieModel> movieModelList = getMovieResponseModel.getMovieModels();
                if (page == 1) {
                    movieListAdapter = new MovieListAdapter(movieModelList, getActivity());
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    recyclerView.setAdapter(movieListAdapter);
                } else {
                    try {
                        movieListAdapter.refreshAdapter(movieModelList);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                totalPage = getMovieResponseModel.getTotal_pages();
                hideLoading();
            }

            @Override
            public void onFailure(Call<GetMovieResponseModel> call, Throwable t) {

            }
        });
    }

    private void initListener(String searchQuery) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int countItem = gridLayoutManager.getItemCount();
                int lastVisiblePosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                boolean isLastPosition = (countItem - 1) == lastVisiblePosition;
                if (!isLoading && isLastPosition && page < totalPage) {
                    showLoading(true);
                    page += 1;
                    doLoadData(searchQuery);
                }
            }
        });
    }

    private void showLoading(Boolean isRefresh) {
        isLoading = true;
        rl_progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(isRefresh ? View.VISIBLE : View.GONE);
    }

    private void hideLoading() {
        isLoading = false;
        rl_progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}