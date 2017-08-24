package com.yjh.espresso;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.EnumSet.allOf;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.yjh.espresso", appContext.getPackageName());

//        onView(withText(appContext.getString(R.string.text))).check(matches(isDisplayed()));
//        onView(withId(R.id.button)).check(matches(isDisplayed()));
//        onView(withId(R.id.et)).perform(clearText(),
//                typeText(appContext.getString(R.string.edit_text)), click());

//        onView(allOf(withId(R.id.button), isDescendantOfA(firstChildOf(withId(R.id.viewpager)))))
//                .perform(click());
//        onView(withId(R.id.button)).perform(click());
//        onView(allOf(withId(R.id.button), isDescendantOfA(
//                getChildAtPosition(withId(R.id.view_pager), 1)))).perform(click());
        onView(withId(R.id.view_pager)).perform(swipeLeft());
//        onView(withId(R.id.text)).perform(click());
        onView(withId(R.id.button)).perform(click());
//        onView(withId(R.id.button)).check(doesNotExist());
        SystemClock.sleep(5000);
    }

    public static Matcher<View> getChildAtPosition(
            final Matcher<View> parentMatcher, final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matchesSafely(View view) {

                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }
                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(position).equals(view);

            }
        };
    }
}
