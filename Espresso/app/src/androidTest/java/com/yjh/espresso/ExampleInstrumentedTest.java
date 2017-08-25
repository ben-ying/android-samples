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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    private static final int INTERVAL = 1000;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.yjh.espresso", appContext.getPackageName());

        // swipe viewpager to next page
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        SystemClock.sleep(INTERVAL);
        // find a button, id is R.id.button and perform click
        onView(withId(R.id.button1)).perform(click());
        SystemClock.sleep(INTERVAL);
        // find an editText, id is R.id.edit_text and replace text and then click
        onView(withId(R.id.edit_text)).perform(clearText(),
                typeText(appContext.getString(R.string.edit_text_clicked)),
                click(), closeSoftKeyboard());
        SystemClock.sleep(INTERVAL);
        // find a textView, id is R.id.text and text is R.string.text and then click
        onView(allOf(withId(R.id.text_view), withText(
                appContext.getString(R.string.text_view)))).perform(click());
        SystemClock.sleep(INTERVAL);
        // scroll to button2 and click
        onView(withId(R.id.button2)).perform(scrollTo(), click());
        SystemClock.sleep(INTERVAL);
        // check button2's text is R.string.button_clicked
        onView(withId(R.id.button2)).check(matches(
                withText(appContext.getString(R.string.button_clicked))));
        SystemClock.sleep(INTERVAL);
        onView(withId(R.id.button1)).perform(scrollTo());
        SystemClock.sleep(INTERVAL);

        // remain 5s
        SystemClock.sleep(5 * INTERVAL);
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
