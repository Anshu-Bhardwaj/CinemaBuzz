package com.example.anshubhardwaj.cinemabuzz.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<VideoObject> videoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<VideoObject> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoObject> videoList) {
        this.videoList = videoList;
    }
}
