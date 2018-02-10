package com.example.maikhoi.bakingapp.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maikhoi.bakingapp.Adapter.RecipeNameAdapter;
import com.example.maikhoi.bakingapp.models.RecipesData;
import com.example.maikhoi.bakingapp.R;
import com.example.maikhoi.bakingapp.RecipeDetailActivity;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by MaiKhoi on 2/1/18.
 */

public class RecipeNameFragment extends Fragment implements RecipeNameAdapter.CardViewOnClickHandler {
    private static  String ENDPOINT;
    private RequestQueue requestQueue;
    private Gson gson;
    private GridLayoutManager gridLayoutManager;
    private RecipeNameAdapter recipeNameAdapter;
    private RecyclerView recyclerView;

    private RecipesData[] recipesDatas;
    private static final String SHARED_PREFERENCE_NAME = "pref";
    private static final String RECIPES_PREFERENCE_KEY = "recipes";
    private static final String RECIPE_ID_PREFERENCE_KEY = "recipeId";
    private static final String RECIPE_SIZE_PREFERENCE_KEY = "recipeSize";

    private int onsharedPreferferenceRecipeID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_layout_recipe_name,container,false);
        ENDPOINT = getResources().getString(R.string.ENDPOINT);
        gson = new Gson();
        recyclerView = rootView.findViewById(R.id.recycler_view_for_recipe_name_activity);
        gridLayoutManager = new GridLayoutManager(getContext(),1);
        recipeNameAdapter = new RecipeNameAdapter(getContext(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        requestQueue = Volley.newRequestQueue(getContext());

        fetchPosts();
        return rootView;
    }

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private void saveDataToSharedPreference(RecipesData[] data) {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String serialized = gson.toJson(data);
        editor.putString(RECIPES_PREFERENCE_KEY, serialized);
        editor.putInt(RECIPE_ID_PREFERENCE_KEY,onsharedPreferferenceRecipeID);
        editor.putInt(RECIPE_SIZE_PREFERENCE_KEY, data.length);
        editor.apply();
    }


    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            recipesDatas = gson.fromJson(response,RecipesData[].class);

            recipeNameAdapter.setRecipeData(recipesDatas);

            recyclerView.setAdapter(recipeNameAdapter);
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    @Override
    public void onClick(RecipesData recipesData,int position) {
        Intent intent = new Intent(getContext(),RecipeDetailActivity.class);
        saveDataToSharedPreference(recipesDatas);
        onsharedPreferferenceRecipeID = position;
        intent.putExtra(getResources().getString(R.string.KEY_VALUE_FOR_DATA_TRANSFER),recipesData);
        intent.putExtra("adapterposition",position);
        startActivity(intent);
    }
}
