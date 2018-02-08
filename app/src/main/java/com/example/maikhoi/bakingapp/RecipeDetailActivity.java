package com.example.maikhoi.bakingapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.maikhoi.bakingapp.Fragments.RecipeDetailFragment;
import com.example.maikhoi.bakingapp.Objects.RecipesData;

import java.sql.Savepoint;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
            if(savedInstanceState==null) {
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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            name = savedInstanceState.getString("testing");
            setTitle(name);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("testing",name);
    }
}

