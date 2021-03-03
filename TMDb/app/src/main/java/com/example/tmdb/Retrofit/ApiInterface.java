package com.example.tmdb.Retrofit;

import com.example.tmdb.Model.GetGenreResponseModel;
import com.example.tmdb.Model.GetMovieResponseModel;
import com.example.tmdb.Model.LoginResponseModel;
import com.example.tmdb.Model.MovieDetailModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/{category}")
    Call<GetMovieResponseModel> getMovie(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET("search/movie")
    Call<GetMovieResponseModel> searchMovie(
            @Query("query") String query,
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET("genre/movie/list")
    Call<GetGenreResponseModel> getGenreList(
            @Query("api_key") String api_key
    );

    @GET("discover/movie")
    Call<GetMovieResponseModel> getGenre(
            @Query("api_key") String api_key,
            @Query("with_genres") int with_genres,
            @Query("page") int page
    );

    @GET("movie/{id}")
    Call<MovieDetailModel> getDetail(
            @Path("id") int movieId,
            @Query("api_key") String api_key,
            @Query("append_to_response") String videos
    );

    @GET("login")
    Call<LoginResponseModel> login(
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("register")
    Call<LoginResponseModel> register(
            @Query("email") String email,
            @Query("name") String name,
            @Query("dob") String dob,
            @Query("address") String address,
            @Query("password") String password
    );
}
