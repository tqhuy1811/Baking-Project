package com.example.maikhoi.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maikhoi.bakingapp.R;
import com.example.maikhoi.bakingapp.models.RecipesIngredientsData;

import java.util.ArrayList;


/**
 * Created by MaiKhoi on 2/9/18.
 */

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientsHolder> {
    private RecipesIngredientsData[] ingredientsName;
    private ArrayList<String> arrayList;

    @Override
    public IngredientsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.recipe_ingredients_recycler_view,parent,false);
        IngredientsHolder holder = new IngredientsHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(IngredientsHolder holder, int position) {
        holder.textViewName.setText(ingredientsName[position].ingredient);
        holder.textViewQuantityMeasure.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientsName.length;
    }

    public class IngredientsHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public TextView textViewQuantityMeasure;
        public IngredientsHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.ingredients_name);
            textViewQuantityMeasure = itemView.findViewById(R.id.ingredients_quantity_measure);
        }
    }
    public void setData(RecipesIngredientsData[] ingredientsName,ArrayList<String> arrayList){
        this.ingredientsName = ingredientsName;
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
