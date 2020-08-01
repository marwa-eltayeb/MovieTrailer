package com.marwaeltayeb.movietrailer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.marwaeltayeb.movietrailer.activities.SettingActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4ClassRunner.class)
public class SettingActivityTest {

    @Rule
    public ActivityTestRule<SettingActivity> mActivityTestRule = new ActivityTestRule<>(SettingActivity.class);

    @Test
    public void sortByTest(){
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(R.string.sort_label)),
                        click()));

        onView(withText("Popular")).perform(click());

        Context appContext = InstrumentationRegistry.getTargetContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        String sortByCategory = preferences.getString(appContext.getString(R.string.sort_key), "");
        assertThat(sortByCategory, equalTo("popular"));
    }

}
