package com.example.anshubhardwaj.cinemabuzz.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImagesObject implements Parcelable {

    @SerializedName("aspect_ratio")
    private double aspectRatio;
    @SerializedName("file_path")
    private String filePath;
    @SerializedName("height")
    private int height;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("width")
    private int width;

    private ImagesObject(Parcel in) {
        aspectRatio = in.readDouble();
        filePath = in.readString();
        height = in.readInt();
        voteAverage = in.readDouble();
        voteCount = in.readInt();
        width = in.readInt();
    }

    public static final Creator<ImagesObject> CREATOR = new Creator<ImagesObject>() {
        @Override
        public ImagesObject createFromParcel(Parcel in) {
            return new ImagesObject(in);
        }

        @Override
        public ImagesObject[] newArray(int size) {
            return new ImagesObject[size];
        }
    };

    public double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(aspectRatio);
        parcel.writeString(filePath);
        parcel.writeInt(height);
        parcel.writeDouble(voteAverage);
        parcel.writeInt(voteCount);
        parcel.writeInt(width);
    }
}
