package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourDetailActivity extends AppCompatActivity {

    public static final String EXTRA_USER_ID = "com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity.EXTRA_USER_ID";


    @BindView(R.id.detail_avatar) ImageView avatar;
    @BindView(R.id.detail_avatar_username) TextView userName1;
    @BindView(R.id.detail_username) TextView userName2;
    @BindView(R.id.detail_address) TextView address;
    @BindView(R.id.detail_phone_number) TextView phoneNumber;
    @BindView(R.id.detail_about) TextView about;
    @BindView(R.id.detail_favorite_button) ImageButton favoriteButton;

    private NeighbourApiService mApiService;
    private long id;
    private Neighbour mNeighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_neighbour_detail);
        ButterKnife.bind(this);

        mApiService = DI.getNeighbourApiService();
        id = getIntent().getLongExtra(EXTRA_USER_ID, -1);
        mNeighbour = mApiService.getNeighbour(id);
        this.updateUI();

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiService.changeIsFavorite(mApiService.getNeighbour(id));
                updateFavorite();
            }
        });
    }


    public void updateFavorite(){
        mNeighbour = mApiService.getNeighbour(id);
        if (mNeighbour.getIsFavorite()){
            favoriteButton.setImageResource(R.drawable.ic_star_gold_24dp);
        }
        else {
            favoriteButton.setImageResource(R.drawable.ic_star_border_gold_24dp);
        }
    }

    public void updateUI(){
        Glide.with(this).load(mNeighbour.getAvatarUrl()).into(avatar);
        this.userName1.setText(mNeighbour.getName());
        this.userName2.setText(mNeighbour.getName());
        this.address.setText(mNeighbour.getAddress());
        this.phoneNumber.setText(mNeighbour.getPhoneNumber());
        this.about.setText(mNeighbour.getAboutMe());
        updateFavorite();
    }

}