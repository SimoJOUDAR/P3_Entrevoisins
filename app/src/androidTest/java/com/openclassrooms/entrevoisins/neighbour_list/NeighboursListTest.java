package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
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
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;
    private static int ITEMS = 3;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mApiService;
    private List<Neighbour> mFavoriteNeighboursList = new ArrayList<>();

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

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
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mApiService = DI.getNewInstanceApiService();
        assertThat(mApiService, notNullValue());
        mFavoriteNeighboursList = mApiService.getFavoriteNeighbours();
    }

    /**
     * We check if our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }


    /**
     * We check if NeighbourDetailActivity is displayed
     * and with the correct TextView updates
     */
    @Test
    public void neighbourDetailsActivity_shouldDisplayCorrectly() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(ITEMS, click()));

        onView(withId(R.id.activity_neighbour_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_avatar_username)).check(matches(withText(mApiService.getNeighbours().get(ITEMS).getName())));
        onView(withId(R.id.detail_username)).check(matches(withText(mApiService.getNeighbours().get(ITEMS).getName())));
        onView(withId(R.id.detail_address)).check(matches(withText(mApiService.getNeighbours().get(ITEMS).getAddress())));
        onView(withId(R.id.detail_phone_number)).check(matches(withText(mApiService.getNeighbours().get(ITEMS).getPhoneNumber())));
        onView(withId(R.id.detail_about)).check(matches(withText(mApiService.getNeighbours().get(ITEMS).getAboutMe())));
    }

    /**
     * We check if all favorites are displayed
     */
    @Test
    public void favoriteNeighboursList_shouldContainOnlyFavoriteNeighbours() {
        onView(withContentDescription("Favorites")).perform(click());

        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS));

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