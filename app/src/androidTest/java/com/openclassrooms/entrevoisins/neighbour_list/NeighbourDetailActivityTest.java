package com.openclassrooms.entrevoisins.neighbour_list;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for NeighbourDetailActivity
 */
@RunWith(AndroidJUnit4.class)
public class NeighbourDetailActivityTest {

    // This is fixed
    private static int idNeighbourToTest = 3;
    private ListNeighbourActivity mActivity;
    private NeighbourApiService mApiService;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mApiService = DI.getNewInstanceApiService();
        assertThat(mApiService, notNullValue());
    }

    /**
     * We check if NeighbourDetailActivity is displayed
     */
    @Test
    public void neighbourDetailsActivity_isDisplayed() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(idNeighbourToTest, click()));

        onView(withId(R.id.activity_neighbour_detail)).check(matches(isDisplayed()));
    }

    /**
     * We check if all TextView updates have been made within NeighbourDetailActivity
     */
    @Test
    public void neighbourDetailsActivity_textViewsAreDisplayed() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(idNeighbourToTest, click()));

        onView(withId(R.id.activity_neighbour_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_avatar_username)).check(matches(withText(mApiService.getNeighbours().get(idNeighbourToTest).getName())));
        onView(withId(R.id.detail_username)).check(matches(withText(mApiService.getNeighbours().get(idNeighbourToTest).getName())));
        onView(withId(R.id.detail_address)).check(matches(withText(mApiService.getNeighbours().get(idNeighbourToTest).getAddress())));
        onView(withId(R.id.detail_phone_number)).check(matches(withText(mApiService.getNeighbours().get(idNeighbourToTest).getPhoneNumber())));
        onView(withId(R.id.detail_about)).check(matches(withText(mApiService.getNeighbours().get(idNeighbourToTest).getAboutMe())));
    }

}
