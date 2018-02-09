package com.example.maikhoi.bakingapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.maikhoi.bakingapp.models.RecipesStepsData;
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
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;


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
    private RelativeLayout relativeLayout;
    private CardView cardViewForDescription;
    private long position = 0;
    private boolean mTwoPane;
    private ImageView imageView;
    private String url;
    private boolean check = false;
    private static final String SHARED_PREFERENCE_NAME = "pref2";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        if(savedInstanceState!=null){
            Parcelable[] array = savedInstanceState.getParcelableArray("testing");
            recipesStepsData  =  Arrays.copyOf(array, array.length, RecipesStepsData[].class);
            adapterPosition = savedInstanceState.getInt("testing1");
            position = savedInstanceState.getLong("testing3");
            check = savedInstanceState.getBoolean("testing2");
        }
        mTwoPane = getBoolean(getContext());
        final View view = inflater.inflate(R.layout.fragment_layout_recipe_instructions,container,false);
        textView = view.findViewById(R.id.text_view_for_description);
        exoPlayerView = view.findViewById(R.id.exo_player);
        relativeLayout = view.findViewById(R.id.layout_buttons);
        buttonNext = view.findViewById(R.id.next_step);
        cardViewForDescription = view.findViewById(R.id.card_view_for_description);
        imageView = view.findViewById(R.id.imageView_thumbnail_url);
        buttonPrevious = view.findViewById(R.id.previous_step);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position=0;
                if(player!=null){
                    player.setPlayWhenReady(false);
                }
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
                position=0;
                adapterPosition++;
                if(player!=null){
                    player.setPlayWhenReady(false);
                }
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

    public boolean getBoolean(Context context) {
        SharedPreferences SharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        boolean twoPane = SharedPrefs.getBoolean("testing2",false);
        return twoPane;
    }

    private void initializePlayer(String url) {
        if(!"".equals(url)) {
            if(url==recipesStepsData[adapterPosition].videoURL){
            check = true;
            exoPlayerView.setVisibility(View.VISIBLE);
            player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector(), new DefaultLoadControl());
            exoPlayerView.setPlayer(player);
            Uri uri = Uri.parse(url);
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
            if(position>0){
                player.seekTo(position);
                player.setPlayWhenReady(true);
            }
            }
            else if(url==recipesStepsData[adapterPosition].thumbnailURL) {
                Log.i("HAHAHHAHAHAHAHA","IMAGE EXIST");
                check = false;
                Log.i("INFO",url);
                Uri uri = Uri.parse(url);
                Picasso.with(getContext()).load(uri).into(imageView);
                exoPlayerView.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }

        }
        else{
            exoPlayerView.setVisibility(View.INVISIBLE);
            check = false;
        }
        if(!mTwoPane&&check) {
                makeFullScreenWhenRotate(getResources().getConfiguration().orientation);
        }

    }

    private void makeFullScreenWhenRotate(int orientation) {
      if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                relativeLayout.setVisibility(View.GONE);
                cardViewForDescription.setVisibility(View.GONE);
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(check) {
            position = player.getCurrentPosition();
            outState.putLong("testing3", position);
        }

        outState.putBoolean("testing2",check );
        outState.putParcelableArray("testing",recipesStepsData);
        outState.putInt("testing1",adapterPosition);
    }


    @Override
    public void onResume() {
        super.onResume();
        if(player==null){
            initializePlayer(handlingVideoURLandThumbnailURL());
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(player!=null){
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("INFO", "ONSTOP");
        if(player!=null){
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player!=null){
            player.stop();
            player.release();
            player=null;
        }
    }

    public void setData(RecipesStepsData[] recipesStepsData, int adapterPosition){
        this.recipesStepsData = recipesStepsData;
        this.adapterPosition = adapterPosition;
    }
}
