package com.example.maikhoi.bakingapp;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by MaiKhoi on 2/8/18.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeInstructionTesting {


        @Rule
        public ActivityTestRule<RecipeListActivity> activityTestRule
                = new ActivityTestRule<>(RecipeListActivity.class);

        @Before
        public void init(){
            activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
            SystemClock.sleep(1000);

        }
        @Test
        public void clickRecyclerViewItem_OpenInstructionActivity_NextButton() {
            onView(withId(R.id.recycler_view_for_recipe_name_activity)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withId(R.id.recycler_view_for_recipe_short_description))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withId(R.id.next_step)).perform(scrollTo()).perform(click());
        }

        @Test
        public void checkTextDescriptionInVideoFragment(){
            onView(withId(R.id.recycler_view_for_recipe_name_activity)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
            onView(withId(R.id.recycler_view_for_recipe_short_description))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withId(R.id.text_view_for_description)).check(matches(withText("Recipe Introduction")));
        }


}
