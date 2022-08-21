package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DetailNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class NeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;

    // Used to determine if to show the exhaustive neighbours list or the favorite neighbours list.
    private boolean mShowOnlyFavorites;


    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance(boolean showOnlyFavorites) {
        NeighbourFragment fragment = new NeighbourFragment();
        fragment.mShowOnlyFavorites = showOnlyFavorites;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view; // Binds the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context)); // Sets the RecyclerView's LayoutManager
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL)); // Adds dividers between items of the list
        return view;
    }

    // Init the List of neighbours
    private void initList() {
        if (mShowOnlyFavorites){
            mNeighbours = mApiService.getFavoriteNeighbours();
        }
        else{
            mNeighbours = mApiService.getNeighbours();
        }

        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours));
    }

    // We used initList() here to refresh the list everytime we display the fragment.
    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // Deletes a neighbour from the list. Fired when the user clicks on a delete button.
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }

    // To display the NeighbourDetailActivity of a neighbour. Fired when the user clicks on an item of the RecyclerView
    @Subscribe
    public void onOpenNeighbourDetail(DetailNeighbourEvent event) {
        Intent i = new Intent(getActivity(), NeighbourDetailActivity.class);
        i.putExtra(NeighbourDetailActivity.EXTRA_USER_ID, event.id);
        startActivity(i);
    }
}
