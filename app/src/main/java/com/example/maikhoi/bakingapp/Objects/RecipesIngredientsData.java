package com.example.maikhoi.bakingapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MaiKhoi on 1/31/18.
 */

public class RecipesIngredientsData implements Parcelable {
   public String quantity;
   public String measure;
   public String ingredient;
    public RecipesIngredientsData(String quantity,String measure,String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected RecipesIngredientsData(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<RecipesIngredientsData> CREATOR = new Creator<RecipesIngredientsData>() {
        @Override
        public RecipesIngredientsData createFromParcel(Parcel in) {
            return new RecipesIngredientsData(in);
        }

        @Override
        public RecipesIngredientsData[] newArray(int size) {
            return new RecipesIngredientsData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
}
