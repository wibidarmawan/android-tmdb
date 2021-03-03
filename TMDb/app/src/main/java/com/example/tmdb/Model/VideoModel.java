package com.example.tmdb.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoModel {
    @SerializedName("results")
    private List<VideoResultModel> videoResultModels;

    public List<VideoResultModel> getVideoResultModels() {
        return videoResultModels;
    }

    public void setVideoResultModels(List<VideoResultModel> videoResultModels) {
        this.videoResultModels = videoResultModels;
    }
}
