package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // Instantiates the proper fragment for the given page.
    @Override
    public Fragment getItem(int position) {
        boolean showOnlyFavorites;
        if (position == 1){ showOnlyFavorites = true; }
        else showOnlyFavorites = false;
        return NeighbourFragment.newInstance(showOnlyFavorites);
    }

    // returns the sum of pager
    @Override
    public int getCount() {
        return 2;
    }
}