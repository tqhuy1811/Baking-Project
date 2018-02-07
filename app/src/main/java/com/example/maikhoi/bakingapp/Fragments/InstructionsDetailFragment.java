package com.example.maikhoi.bakingapp.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.maikhoi.bakingapp.Objects.RecipesStepsData;
import com.example.maikhoi.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by MaiKhoi on 2/2/18.
 */

public class InstructionsDetailFragment extends Fragment   {
    private TextView textView;
    private int adapterPosition;
    private RecipesStepsData[] recipesStepsData;
    private SimpleExoPlayerView exoPlayerView;
    private Button buttonNext;
    private Button buttonPrevious;
    private SimpleExoPlayer player;
    private String url;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        final View view = inflater.inflate(R.layout.fragment_layout_recipe_instructions,container,false);
        textView = view.findViewById(R.id.text_view_for_description);
        exoPlayerView = view.findViewById(R.id.exo_player);
        buttonNext = view.findViewById(R.id.next_step);
        buttonPrevious = view.findViewById(R.id.previous_step);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapterPosition == 0){
                    adapterPosition = recipesStepsData.length;
                }
                adapterPosition--;
                initializePlayer(handlingVideoURLandThumbnailURL());
                textView.setText(recipesStepsData[adapterPosition].description);
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterPosition++;
                if(adapterPosition == recipesStepsData.length){
                    adapterPosition = 0;
                }

                initializePlayer(handlingVideoURLandThumbnailURL());
                textView.setText(recipesStepsData[adapterPosition].description);
            }
        });
        initializePlayer(handlingVideoURLandThumbnailURL());
        textView.setText(recipesStepsData[adapterPosition].description);



        return  view;
    }
    public InstructionsDetailFragment(){

    }


    private String  handlingVideoURLandThumbnailURL() {
        if("".equals(recipesStepsData[adapterPosition].videoURL)){
            return url = recipesStepsData[adapterPosition].thumbnailURL;
        }
        return url = recipesStepsData[adapterPosition].videoURL;
    }



    private void initializePlayer(String url) {
        if(!"".equals(url)) {
            exoPlayerView.setVisibility(View.VISIBLE);
            player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector(), new DefaultLoadControl());
            exoPlayerView.setPlayer(player);
            Uri uri = Uri.parse(url);
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
        else{
            exoPlayerView.setVisibility(View.INVISIBLE);
        }
    }
    public void setData(RecipesStepsData[] recipesStepsData,int adapterPosition){
        this.recipesStepsData = recipesStepsData;
        this.adapterPosition = adapterPosition;
    }
}
