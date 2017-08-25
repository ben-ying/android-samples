package com.yjh.espresso;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
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
        Context context = InstrumentationRegistry.getTargetContext();
        assertEquals("com.yjh.espresso", context.getPackageName());

        // click a menu item, text is R.string.save
        testClickActionBarItem(R.string.save);
        SystemClock.sleep(INTERVAL);
        // Scroll to the position 10 and click on it.
        // And check the item with text "Position: 10 is visible"
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(15, click()));
        onView(withText("Position: 10")).check(matches(isDisplayed()));
        SystemClock.sleep(INTERVAL);
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(0));
        SystemClock.sleep(INTERVAL);
        // swipe viewpager to next page
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        SystemClock.sleep(INTERVAL);
        // find a button, id is R.id.button and perform click
        onView(withId(R.id.button1)).perform(click());
        SystemClock.sleep(INTERVAL);
        // find an editText, id is R.id.edit_text and replace text and then click
        onView(withId(R.id.edit_text)).perform(clearText(),
                typeText(context.getString(R.string.edit_text_clicked)),
                click(), closeSoftKeyboard());
        SystemClock.sleep(INTERVAL);
        // find a textView, id is R.id.text and text is R.string.text and then click
        onView(allOf(withId(R.id.text_view), withText(
                context.getString(R.string.text_view)))).perform(click());
        SystemClock.sleep(INTERVAL);
        // scroll to button2 and click
        onView(withId(R.id.button2)).perform(scrollTo(), click());
        SystemClock.sleep(INTERVAL);
        // check button2's text is R.string.button_clicked
        onView(withId(R.id.button2)).check(matches(
                withText(context.getString(R.string.button_clicked))));
        SystemClock.sleep(INTERVAL);
        // scroll to button1
        onView(withId(R.id.button1)).perform(scrollTo());
        SystemClock.sleep(INTERVAL);
        // swipe viewpager to preview page
        onView(withId(R.id.view_pager)).perform(swipeRight());

        // remain 5s
        SystemClock.sleep(5 * INTERVAL);
    }

    // matching a view that is inside an action bar
    public void testClickActionBarItem(int textRes) {
        // We make sure the contextual action bar is hidden.
        // onView(withId(R.id.hide_contextual_action_bar)).perform(click());
        // Open the options menu OR open the overflow menu, depending on whether
        // the device has a hardware or software overflow menu button.
        // If the menu item is visible comment this line
         openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        // Open the overflow menu from contextual action mode.
        // If the menu item is visible comment this line
//         openContextualActionModeOverflowMenu();
        // Click on the icon, overflow menu cannot use withId
        onView(withText(textRes)).perform(click());
    }
}
