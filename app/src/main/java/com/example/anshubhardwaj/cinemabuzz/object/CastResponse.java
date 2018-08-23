package com.example.anshubhardwaj.cinemabuzz.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private List<CastObject> castList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CastObject> getCastList() {
        return castList;
    }

    public void setCastList(List<CastObject> castList) {
        this.castList = castList;
    }
}
