package com.openclassrooms.entrevoisins.neighbour_list;


import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test class for favorite neighbours list display
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class FavoriteNeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 3;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityTestRule = new ActivityTestRule<>(ListNeighbourActivity.class);
    private ListNeighbourActivity mActivity;
    private NeighbourApiService mApiService;
    private List<Neighbour> mFavoriteNeighboursList = new ArrayList<>();

    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());

        mApiService = DI.getNewInstanceApiService();
        assertThat(mApiService, notNullValue());
        mFavoriteNeighboursList = mApiService.getFavoriteNeighbours();
    }

    /**
     * We check if
     */
    @Test
    public void favoriteNeighboursList_shouldContainOnlyFavoriteNeighbours() {
        onView(withContentDescription("Favorites")).perform(click());

        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT));

        onView(allOf(withId(R.id.item_list_name),
                childAtPosition(childAtPosition(withId(R.id.list_neighbours), 0), 1),
                isDisplayed()))
                .check(matches(withText(mFavoriteNeighboursList.get(0).getName())));

        onView(allOf(withId(R.id.item_list_name),
                childAtPosition(childAtPosition(withId(R.id.list_neighbours), 1), 1),
                isDisplayed()))
                .check(matches(withText(mFavoriteNeighboursList.get(1).getName())));

        onView(allOf(withId(R.id.item_list_name),
                childAtPosition(childAtPosition(withId(R.id.list_neighbours), 2), 1),
                isDisplayed()))
                .check(matches(withText(mFavoriteNeighboursList.get(2).getName())));



    }
}
