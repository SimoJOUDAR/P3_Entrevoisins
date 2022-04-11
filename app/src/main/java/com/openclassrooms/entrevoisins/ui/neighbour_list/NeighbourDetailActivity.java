package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourDetailActivity extends AppCompatActivity {

    public static final String EXTRA_USER_ID = "com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity.EXTRA_USER_ID";
    public static final String EXTRA_USER_AVATAR = "com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity.EXTRA_USER_AVATAR";
    public static final String EXTRA_USER_NAME = "com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity.EXTRA_USER_NAME";
    public static final String EXTRA_USER_ADDRESS = "com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity.EXTRA_USER_ADDRESS";
    public static final String EXTRA_USER_PHONE_NUMBER = "com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity.EXTRA_USER_PHONE_NUMBER";
    public static final String EXTRA_USER_ABOUT = "com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity.EXTRA_USER_ABOUT";


    @BindView(R.id.detail_avatar) ImageView avatar;
    @BindView(R.id.detail_avatar_username) TextView userName1;
    @BindView(R.id.detail_username) TextView userName2;
    @BindView(R.id.detail_address) TextView address;
    @BindView(R.id.detail_phone_number) TextView phoneNumber;
    @BindView(R.id.detail_about) TextView about;
    @BindView(R.id.detail_favorite_button) ImageButton favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);
        Log.d("NeighbourDetailActivity", "in onCreate()");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        this.updateUI();

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addToFavorite(id);
            }
        });
    }


    public void updateUI(){
        Glide.with(this).load(getIntent().getStringExtra(EXTRA_USER_AVATAR)).into(avatar);
        this.userName1.setText(getIntent().getStringExtra(EXTRA_USER_NAME));
        this.userName2.setText(getIntent().getStringExtra(EXTRA_USER_NAME));
        this.address.setText(getIntent().getStringExtra(EXTRA_USER_ADDRESS));
        this.phoneNumber.setText(getIntent().getStringExtra(EXTRA_USER_PHONE_NUMBER));
        this.about.setText(getIntent().getStringExtra(EXTRA_USER_ABOUT));
    }

    private void addToFavorite(String id) {
        String userId = getIntent().getStringExtra(EXTRA_USER_ID);
    }

}