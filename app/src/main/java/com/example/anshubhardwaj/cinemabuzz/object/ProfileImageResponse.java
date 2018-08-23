package com.example.anshubhardwaj.cinemabuzz.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileImageResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("profiles")
    private List<ImagesObject> imageList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ImagesObject> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImagesObject> imageList) {
        this.imageList = imageList;
    }
}
