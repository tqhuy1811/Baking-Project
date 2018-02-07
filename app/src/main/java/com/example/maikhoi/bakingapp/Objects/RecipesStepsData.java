package com.example.maikhoi.bakingapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MaiKhoi on 1/31/18.
 */

public class RecipesStepsData implements Parcelable {
   public String id;
   public String shortDescription;
   public String description;
   public String videoURL;
   public String thumbnailURL;

    public RecipesStepsData(String id,String shortDescription,String description,String videoURL,String thumbnailURL){
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.thumbnailURL = thumbnailURL;
        this.videoURL = videoURL;

    }

    protected RecipesStepsData(Parcel in) {
        id = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<RecipesStepsData> CREATOR = new Creator<RecipesStepsData>() {
        @Override
        public RecipesStepsData createFromParcel(Parcel in) {
            return new RecipesStepsData(in);
        }

        @Override
        public RecipesStepsData[] newArray(int size) {
            return new RecipesStepsData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }
}
