package com.example.maikhoi.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by MaiKhoi on 1/31/18.
 */

public class RecipesData implements Parcelable {
     public String name;
     @SerializedName("ingredients")
    public RecipesIngredientsData[] recipesIngredientsData;
     @SerializedName("steps")
    public RecipesStepsData[] recipesStepsData;
    public String servings;
    public String image;


    public RecipesData(String name,RecipesIngredientsData[] recipesIngredientsData,RecipesStepsData[] recipesStepsData,String image,String servings)
    {
        this.name = name;
        this.recipesIngredientsData = recipesIngredientsData;
        this.recipesStepsData = recipesStepsData;
        this.servings = servings;
        this.image = image;
    }

    protected RecipesData(Parcel in) {
        name = in.readString();
        servings = in.readString();
        image = in.readString();
       Parcelable[] tempArrayForIngredients = in.readParcelableArray(RecipesIngredientsData.class.getClassLoader());
       if(tempArrayForIngredients != null){
           recipesIngredientsData = Arrays.copyOf(tempArrayForIngredients,tempArrayForIngredients.length,RecipesIngredientsData[].class);
       }
       Parcelable[] tempArrayForSteps = in.readParcelableArray(RecipesStepsData.class.getClassLoader());
       if(tempArrayForSteps != null){
           recipesStepsData = Arrays.copyOf(tempArrayForSteps,tempArrayForSteps.length,RecipesStepsData[].class);
       }

    }

    public static final Creator<RecipesData> CREATOR = new Creator<RecipesData>() {
        @Override
        public RecipesData createFromParcel(Parcel in) {
            return new RecipesData(in);
        }

        @Override
        public RecipesData[] newArray(int size) {
            return new RecipesData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(servings);
        parcel.writeString(image);
        parcel.writeParcelableArray(recipesIngredientsData,i);
        parcel.writeParcelableArray(recipesStepsData,i);


    }
}
