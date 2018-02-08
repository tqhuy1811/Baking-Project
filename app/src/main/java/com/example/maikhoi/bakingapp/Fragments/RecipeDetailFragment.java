package com.example.maikhoi.bakingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.maikhoi.bakingapp.Adapter.ExpandableListAdapterForRecipeDetail;
import com.example.maikhoi.bakingapp.Adapter.RecipeDetailShortDescriptionAdapter;
import com.example.maikhoi.bakingapp.InstructionsDetailActivity;
import com.example.maikhoi.bakingapp.Objects.RecipesData;
import com.example.maikhoi.bakingapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MaiKhoi on 2/1/18.
 */

public class RecipeDetailFragment extends android.support.v4.app.Fragment implements RecipeDetailShortDescriptionAdapter.OnCardClickHandler {
    private  RecipesData recipesData;
    private ExpandableListAdapterForRecipeDetail expandableListAdapterForRecipeDetail;
    private ExpandableListView expandableListView;
    private GridLayoutManager gridLayoutManager;
    private RecipeDetailShortDescriptionAdapter recipeDetailShortDescriptionAdapter;
    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private ArrayList<String> listValue;
    private String key;
    private String measure;
    private String quantity;
    private String ingredients;
    private HashMap<String,List<String>> listHashMap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            recipesData = savedInstanceState.getParcelable("INFO");
            listValue = savedInstanceState.getStringArrayList("TESTING");
            list = savedInstanceState.getStringArrayList("TESTING1");
            listHashMap = (HashMap<String,List<String>> ) savedInstanceState.getSerializable("TESTING2");
            Log.i("HAHAHAHAHAHAHAHAHAHAH",String.valueOf(list.size()));
            Log.i("bbbbbbbbbbb",String.valueOf(listValue.size()));
        }
        final View view = inflater.inflate(R.layout.fragment_layout_recipe_detail,container,false);
        expandableListView = view.findViewById(R.id.expandable_list_view_for_recipe_ingredients);
        recyclerView = view.findViewById(R.id.recycler_view_for_recipe_short_description);
        gridLayoutManager = new GridLayoutManager(getContext(),1);

        recyclerView.setLayoutManager(gridLayoutManager);
        recipeDetailShortDescriptionAdapter = new RecipeDetailShortDescriptionAdapter(this,getContext());
        recipeDetailShortDescriptionAdapter.setRecipeData(recipesData.recipesStepsData);
        recyclerView.setAdapter(recipeDetailShortDescriptionAdapter);
        expandableListAdapterForRecipeDetail = new ExpandableListAdapterForRecipeDetail(getContext(),list,listHashMap);
        expandableListView.setAdapter(expandableListAdapterForRecipeDetail);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                expandableListView.collapseGroup(i);
                return  true;
            }
        });
        return view;

    }
    public RecipeDetailFragment(){

    }
    public  void getRecipeData(RecipesData recipeData){
        this.recipesData = recipeData;
    }
    public void setData(){
        init();
    }
    public void getStringData(String measure,String quantity,String ingredients,String key){
        this.measure = measure;
        this.quantity = quantity;
        this.ingredients = ingredients;
        this.key = key;
    }
    public void init(){
        list = new ArrayList<>();
        listHashMap = new HashMap<>();
        list.add(key);
        listValue = new ArrayList<>();
        for (int i=0;i<recipesData.recipesIngredientsData.length;i++){
            listValue.add(ingredients +" "+ recipesData.recipesIngredientsData[i].ingredient);
            listValue.add(quantity+" "+recipesData.recipesIngredientsData[i].quantity+" "+ recipesData.recipesIngredientsData[i].measure);
        }
        listHashMap.put(list.get(0),listValue);
    }


    @Override
    public void onClick(int position) {
        Bundle b = new Bundle();
        b.putInt(getResources().getString(R.string.adapter_position),position);
        b.putParcelableArray(getResources().getString(R.string.steps_detail),recipesData.recipesStepsData);
        Intent intent = new Intent(getContext(),InstructionsDetailActivity.class);

        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("INFO",recipesData);
        outState.putStringArrayList("TESTING",listValue);
        outState.putStringArrayList("TESTING1",list);
        outState.putSerializable("TESTING2",listHashMap);
    }
}
