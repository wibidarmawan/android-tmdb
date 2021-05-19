package com.example.tmdb.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmdb.Adapter.MovieListAdapter;
import com.example.tmdb.Model.GenreModel;
import com.example.tmdb.Model.GetMovieResponseModel;
import com.example.tmdb.Model.MovieModel;
import com.example.tmdb.R;
import com.example.tmdb.Retrofit.ApiInterface;
import com.example.tmdb.Retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreActivity extends AppCompatActivity {
    public static final String EXTRA_GENRE = "extra_genre";
    RecyclerView rvMovieGenre;
    GenreModel genreModel;
    String genreName;
    int genreId;
    private Boolean isLoading;
    private int page = 1;
    private int totalPage = 0;
    MovieListAdapter movieListAdapter;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        rvMovieGenre = findViewById(R.id.rv_movie_genre);
        relativeLayout = findViewById(R.id.rl_pg_background);

        genreModel = getIntent().getParcelableExtra(EXTRA_GENRE);

        genreName = genreModel.getName();
        genreId = genreModel.getId();

        ActionBar ab = getSupportActionBar();
        ab.setTitle(genreName);
        ab.setDisplayHomeAsUpEnabled(true);
        doLoadData();
        initListener();

    }

    private void doLoadData() {
        showLoading(true);
        ApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<GetMovieResponseModel> call = apiInterface.getGenre(RetrofitInstance.API_KEY, genreId, page);
        call.enqueue(new Callback<GetMovieResponseModel>() {
            @Override
            public void onResponse(Call<GetMovieResponseModel> call, Response<GetMovieResponseModel> response) {
                GetMovieResponseModel getMovieResponseModel = response.body();
                List<MovieModel> movieModelList = getMovieResponseModel.getMovieModels();
                if (page == 1) {
                    movieListAdapter = new MovieListAdapter(movieModelList, GenreActivity.this);
                    rvMovieGenre.setLayoutManager(new GridLayoutManager(GenreActivity.this, 3));
                    rvMovieGenre.setAdapter(movieListAdapter);
                } else {
                    try {
                        movieListAdapter.refreshAdapter(movieModelList);
                    } catch (Exception e) {
                        Toast.makeText(GenreActivity.this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                totalPage = getMovieResponseModel.getTotal_pages();
                hideLoading();
            }

            @Override
            public void onFailure(Call<GetMovieResponseModel> call, Throwable t) {
                Toast.makeText(GenreActivity.this, "error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener() {
        rvMovieGenre.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    doLoadData();
                }
            }
        });
    }

    private void showLoading(Boolean isRefresh) {
        isLoading = true;
        relativeLayout.setVisibility(View.VISIBLE);
        rvMovieGenre.setVisibility(isRefresh ? View.VISIBLE : View.GONE);
    }

    private void hideLoading() {
        isLoading = false;
        relativeLayout.setVisibility(View.GONE);
        rvMovieGenre.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}