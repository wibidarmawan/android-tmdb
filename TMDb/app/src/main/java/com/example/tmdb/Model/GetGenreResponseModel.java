package com.example.tmdb.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetGenreResponseModel {
    @SerializedName("genres")
    private List<GenreModel> genreModels;

    public List<GenreModel> getGenreModels() {
        return genreModels;
    }

    public void setGenreModels(List<GenreModel> genreModels) {
        this.genreModels = genreModels;
    }
}
