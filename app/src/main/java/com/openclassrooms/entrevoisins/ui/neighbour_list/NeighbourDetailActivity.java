package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourDetailActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity.EXTRA_USER";
    public static final String EXTRA_USER_POSITION = "com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity.EXTRA_USER_POSITION";


    @BindView(R.id.detail_avatar) ImageView avatar;
    @BindView(R.id.detail_avatar_username) TextView userName1;
    @BindView(R.id.detail_username) TextView userName2;
    @BindView(R.id.detail_address) TextView address;
    @BindView(R.id.detail_phone_number) TextView phoneNumber;
    @BindView(R.id.detail_about) TextView about;
    @BindView(R.id.detail_favorite_button) ImageButton favoriteButton;

    private NeighbourApiService mApiService;
    private Neighbour mNeighbour;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_neighbour_detail);
        ButterKnife.bind(this);

        mApiService = DI.getNeighbourApiService();
        mNeighbour = getIntent().getParcelableExtra(EXTRA_USER);
        mPosition = getIntent().getIntExtra(EXTRA_USER_POSITION, -1);
        this.updateUI();

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isFavorite = mNeighbour.getIsFavorite();
               if (!isFavorite){
                   mApiService.setIsFavorite(mNeighbour, mPosition, true);
                   favoriteButton.setImageResource(R.drawable.ic_star_white_24dp);
               }
               else if (isFavorite){
                   mApiService.setIsFavorite(mNeighbour, mPosition, false);
                   favoriteButton.setImageResource(R.drawable.ic_star_border_white_24dp);
               }
            }
        });
    }


    public void updateUI(){
        Glide.with(this).load(mNeighbour.getAvatarUrl()).into(avatar);
        this.userName1.setText(mNeighbour.getName());
        this.userName2.setText(mNeighbour.getName());
        this.address.setText(mNeighbour.getAddress());
        this.phoneNumber.setText(mNeighbour.getPhoneNumber());
        this.about.setText(mNeighbour.getAboutMe());
        if (mNeighbour.getIsFavorite()){
            favoriteButton.setImageResource(R.drawable.ic_star_white_24dp);
        }
    }

}