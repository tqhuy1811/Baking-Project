package com.example.maikhoi.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.maikhoi.bakingapp.Fragments.RecipeDetailFragment;
import com.example.maikhoi.bakingapp.Objects.RecipesData;

/**
 * Created by MaiKhoi on 2/1/18.
 */

public class RecipeDetailActivity extends AppCompatActivity {
    private RecipesData recipesData;
    private String measure;
    private String quantity;
    private String ingredients;
    private String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        measure = getResources().getString(R.string.ingredients_measure);
        quantity = getResources().getString(R.string.ingredients_quantity);
        key = getResources().getString(R.string.ingredients_expandable_list);
        ingredients = getResources().getString(R.string.ingredieents_name);
        setTitle(getResources().getString(R.string.Recipe_detail_title));
        if (getIntent() != null) {
            recipesData = getIntent().getParcelableExtra(getResources().getString(R.string.KEY_VALUE_FOR_DATA_TRANSFER));
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.getRecipeData(recipesData);
            recipeDetailFragment.getStringData(measure,quantity,ingredients,key);
            recipeDetailFragment.setData();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_detail_fragment_instance,recipeDetailFragment).commit();
        }
    }

}

