package com.example.maikhoi.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maikhoi.bakingapp.models.RecipesData;
import com.example.maikhoi.bakingapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by MaiKhoi on 2/1/18.
 */

public class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.CardHolder> {

    public RecipesData[] data;
    private final CardViewOnClickHandler clickHandler;
    public Context context;

    public RecipeNameAdapter(Context context, CardViewOnClickHandler clickHandler){
        this.context = context;
        this.clickHandler = clickHandler;
    }

    public interface CardViewOnClickHandler{
        void onClick(RecipesData recipesData,int position);
    }
    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.recipe_name_recycler_view,parent,false);
        CardHolder holder = new CardHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        holder.recipeName.setText(data[position].name);

    }

    @Override
    public int getItemCount() {
        if(null == data) return 0;
        return data.length;
    }

    public class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView recipeName;


        public CardHolder(View itemView){
            super(itemView);
            recipeName = itemView.findViewById(R.id.text_view_display_recipe_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            RecipesData recipesData = data[adapterPosition];
            clickHandler.onClick(recipesData,adapterPosition);
        }
    }
    public void setRecipeData(RecipesData[] recipesData){
        if(recipesData != null){
        data = recipesData;
        notifyDataSetChanged();}
        else{
            Log.i("INFO","EMPTY");
        }
    }
    }


