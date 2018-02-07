package com.example.maikhoi.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.maikhoi.bakingapp.Objects.RecipesData;
import com.example.maikhoi.bakingapp.Objects.RecipesIngredientsData;
import com.example.maikhoi.bakingapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by MaiKhoi on 2/6/18.
 */

public class ListProviderFactory implements RemoteViewsService.RemoteViewsFactory {
    private RecipesIngredientsData[] recipesIngredientsData;
    private Context context = null;

    private static final String SHARED_PREFERENCE_NAME = "pref";
    private static final String RECIPES_PREFERENCE_KEY = "recipes";
    private static final String RECIPE_ID_PREFERENCE_KEY = "recipeId";


    public ListProviderFactory(Context context){
        this.context = context;
    }

    @Override
    public void onCreate() {

    }
    public RecipesIngredientsData[] getIngredientsFromPreferences(Context context) {
        SharedPreferences SharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int recipeId = SharedPrefs.getInt(RECIPE_ID_PREFERENCE_KEY, 0);
        String jsonString = SharedPrefs.getString(RECIPES_PREFERENCE_KEY, null);
        Gson gson = new Gson();
        RecipesData[] recipes = gson.fromJson(jsonString, RecipesData[].class);
        return recipes[recipeId].recipesIngredientsData;
    }

    @Override
    public void onDataSetChanged() {
        recipesIngredientsData = getIngredientsFromPreferences(context);
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(recipesIngredientsData==null)return 0;
        return recipesIngredientsData.length;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(recipesIngredientsData==null)return null;
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_view_widget);
        remoteViews.setTextViewText(R.id.text_view_for_ingredients_widget,recipesIngredientsData[i].ingredient);
        remoteViews.setTextViewText(R.id.text_view_for_quantity_widget,recipesIngredientsData[i].quantity+" "+recipesIngredientsData[i].measure);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
