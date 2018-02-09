package com.example.maikhoi.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.maikhoi.bakingapp.models.RecipesData;
import com.example.maikhoi.bakingapp.R;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeListAppWidget extends AppWidgetProvider {
    private static final String SHARED_PREFERENCE_NAME = "pref";
    private static final String RECIPES_PREFERENCE_KEY = "recipes";
    private static final String RECIPE_ID_PREFERENCE_KEY = "recipeId";
    private static final String RECIPE_SIZE_PREFERENCE_KEY = "recipeSize";
    private static final String WIDGET_UPDATE_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";
    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_list_app_widget);
        String title = getIngredientsName(context);
        views.setTextViewText(R.id.widget_list_title, title);

        Intent intent = new Intent(context, ListViewServices.class);
        views.setRemoteAdapter(R.id.list_view, intent);

        // broadcast to update widget
        Intent updateRecipeIntent = new Intent();
        updateRecipeIntent.setAction(WIDGET_UPDATE_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                updateRecipeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_list_title, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list_view);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), RecipeListAppWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        updateId(context);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void updateId(Context context) {
        SharedPreferences SharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedPrefs.edit();
        int recipeId = SharedPrefs.getInt(RECIPE_ID_PREFERENCE_KEY, 0);
        int recipesSize = SharedPrefs.getInt(RECIPE_SIZE_PREFERENCE_KEY, 1);

        if (recipeId < recipesSize - 1) {
            recipeId++;
        } else {
            recipeId = 0;
        }
        editor.putInt(RECIPE_ID_PREFERENCE_KEY, recipeId);
        editor.apply();
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    public String getIngredientsName(Context context) {
        SharedPreferences SharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int id = SharedPrefs.getInt(RECIPE_ID_PREFERENCE_KEY, 0);

        String jsonString = SharedPrefs.getString(RECIPES_PREFERENCE_KEY, null);
        Gson gson = new Gson();
        RecipesData[] recipesData = gson.fromJson(jsonString , RecipesData[].class);

        return recipesData[id].name;
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

