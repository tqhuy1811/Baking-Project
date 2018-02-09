package com.example.maikhoi.bakingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.maikhoi.bakingapp.Fragments.InstructionsDetailFragment;
import com.example.maikhoi.bakingapp.Fragments.RecipeDetailFragment;
import com.example.maikhoi.bakingapp.models.RecipesData;
import com.example.maikhoi.bakingapp.models.RecipesStepsData;

/**
 * Created by MaiKhoi on 2/1/18.
 */

public class RecipeDetailActivity extends AppCompatActivity {
    private RecipesData recipesData;
    private String measure;
    private String quantity;
    private String ingredients;
    private String key;
    private String name;
    private RecipesStepsData[] recipesStepsData;
    private int adapterPosition;
    public boolean mTwopane;
    private static final String SHARED_PREFERENCE_NAME = "pref2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (findViewById(R.id.layout_for_tablet) != null) {
            if (savedInstanceState == null) {
                if (getIntent() != null) {
                    measure = getResources().getString(R.string.ingredients_measure);
                    quantity = getResources().getString(R.string.ingredients_quantity);
                    key = getResources().getString(R.string.ingredients_expandable_list);
                    ingredients = getResources().getString(R.string.ingredieents_name);
                    mTwopane = true;
                    recipesData = getIntent().getParcelableExtra(getResources().getString(R.string.KEY_VALUE_FOR_DATA_TRANSFER));
                    recipesStepsData = recipesData.recipesStepsData;
                   RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();

                    saveTwoPannelToOnShare(mTwopane);
                    recipeDetailFragment.getRecipeData(recipesData);
                    name = recipesData.name;
                    setTitle(name);
                    recipeDetailFragment.getStringData(measure, quantity, ingredients, key);
                    recipeDetailFragment.setData();
                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.recipe_detail_fragment_instance, recipeDetailFragment).commit();
                    InstructionsDetailFragment instructionsDetailFragment = new InstructionsDetailFragment();
                    if (recipeDetailFragment.getAdapterPosition() != 0) {
                        adapterPosition = recipeDetailFragment.getAdapterPosition();
                    } else {
                        adapterPosition = 0;
                    }
                    instructionsDetailFragment.setData(recipesStepsData, adapterPosition);
                    FragmentManager fragment = getSupportFragmentManager();
                    fragment.beginTransaction().add(R.id.frame_layout_for_instructions_fragment, instructionsDetailFragment).commit();
                }
            }
        } else {
                if (savedInstanceState == null) {
                    measure = getResources().getString(R.string.ingredients_measure);
                    quantity = getResources().getString(R.string.ingredients_quantity);
                    key = getResources().getString(R.string.ingredients_expandable_list);
                    ingredients = getResources().getString(R.string.ingredieents_name);
                    if (getIntent() != null) {
                        recipesData = getIntent().getParcelableExtra(getResources().getString(R.string.KEY_VALUE_FOR_DATA_TRANSFER));
                        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                        recipeDetailFragment.getRecipeData(recipesData);
                        name = recipesData.name;
                        setTitle(name);

                        recipeDetailFragment.getStringData(measure, quantity, ingredients, key);
                        recipeDetailFragment.setData();
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().add(R.id.recipe_detail_fragment_instance, recipeDetailFragment).commit();
                    }
                }
            }

        }
    
    public void saveTwoPannelToOnShare(boolean mTwopane){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("testing2",mTwopane);
        editor.apply();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            name = savedInstanceState.getString("testing");
            mTwopane = savedInstanceState.getBoolean("testing1");
            saveTwoPannelToOnShare(mTwopane);
            Log.i("HAHAHAHAHAHA",String.valueOf(mTwopane));
            setTitle(name);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("testing",name);
        outState.putBoolean("testing1",mTwopane);
    }
}

