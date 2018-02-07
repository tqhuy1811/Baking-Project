package com.example.maikhoi.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maikhoi.bakingapp.Objects.RecipesStepsData;
import com.example.maikhoi.bakingapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by MaiKhoi on 2/2/18.
 */

public class RecipeDetailShortDescriptionAdapter extends RecyclerView.Adapter<RecipeDetailShortDescriptionAdapter.StepHolder> {
    private RecipesStepsData[] recipesStepsData;
    private final OnCardClickHandler onCardClickHandler;
    private Context context;

    public RecipeDetailShortDescriptionAdapter(OnCardClickHandler onCardClickHandler,Context context){
        this.onCardClickHandler = onCardClickHandler;
        this.context = context;
    }
    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.recipe_steps_recycler_view,parent,false);
        StepHolder stepHolder = new StepHolder(layout);
        return stepHolder;
    }
    public interface OnCardClickHandler{
     void onClick(int position);
    }
    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        holder.textView.setText(recipesStepsData[position].shortDescription);
        Picasso.with(context).load(R.drawable.baking).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return recipesStepsData.length;
    }

    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public ImageView imageView;
        public StepHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recipe_short_description);
            imageView = itemView.findViewById(R.id.steps_image_background);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            onCardClickHandler.onClick(position);

        }
    }
    public void setRecipeData(RecipesStepsData[] recipesStepsData){
        if(recipesStepsData!=null){
            this.recipesStepsData = recipesStepsData;
            notifyDataSetChanged();
        }
    }

}
