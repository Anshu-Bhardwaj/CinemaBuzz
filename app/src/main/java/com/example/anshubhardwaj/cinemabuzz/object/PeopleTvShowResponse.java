package com.example.anshubhardwaj.cinemabuzz.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleTvShowResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private
    List<TvShowObject> peopleTvShowList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TvShowObject> getPeopleTvShowList() {
        return peopleTvShowList;
    }

    public void setPeopleTvShowList(List<TvShowObject> peopleTvShowList) {
        this.peopleTvShowList = peopleTvShowList;
    }
}
