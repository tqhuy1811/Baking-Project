package com.example.maikhoi.bakingapp;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.maikhoi.bakingapp.fragments.InstructionsDetailFragment;
import com.example.maikhoi.bakingapp.models.RecipesStepsData;

import java.util.Arrays;

public class InstructionsDetailActivity extends AppCompatActivity {
    private RecipesStepsData[] recipesStepsData;
    private int adapterPosition;
    private Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions_detail);
        if(savedInstanceState==null) {
            if (getIntent() != null) {
                b = getIntent().getExtras();
                adapterPosition = b.getInt(getResources().getString(R.string.adapter_position));
                Parcelable[] array = b.getParcelableArray(getResources().getString(R.string.steps_detail));
                recipesStepsData = Arrays.copyOf(array, array.length, RecipesStepsData[].class);
                Log.i("hehe", recipesStepsData[adapterPosition].description);
                InstructionsDetailFragment instructionsDetailFragment = new InstructionsDetailFragment();
                instructionsDetailFragment.setData(recipesStepsData, adapterPosition);
                FragmentManager fragment = getSupportFragmentManager();
                fragment.beginTransaction().add(R.id.frame_layout_for_instructions_fragment, instructionsDetailFragment).commit();
            }
        }
    }
}
