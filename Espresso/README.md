# Espresso Sample Tutorial


## Set up your test environment
1. Open your app’s build.gradle file. This is usually not the top-level build.gradle file but app/build.gradle.
2. Add the following lines inside dependencies:
```java
androidTestCompile 'com.android.support.test.espresso:espresso-core:3.0.0'
androidTestCompile 'com.android.support.test:runner:1.0.0'
```
3. Add to the same build.gradle file the following line in android.defaultConfig:
```java
testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
```
### Example Gradle build file
```java
apply plugin: 'com.android.application'

android {
    compileSdkVersion 26
    buildToolsVersion "26.0.1"

    defaultConfig {
        applicationId "com.my.awesome.app"
        minSdkVersion 15
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    // App's dependencies, including test
    compile 'com.android.support:support-annotations:22.2.0'

    // Testing-only dependencies
    androidTestCompile 'com.android.support.test:runner:1.0.0'
    androidTestCompile 'com.android.support.test.espresso:espresso-core:3.0.0'
}
```
### To create a test configuration in Android Studio, complete the following steps:
1. Open Run > Edit Configurations.
2. Add a new Android Instrumented Tests.
3. Choose a module.
4. Click OK and run.


## Usage (Make sure every step the view is visible)

### Finding a view by its R.id is as simple as calling onView():
```java
onView(withId(R.id.my_view))
```
### You can use this to narrow down your search by using combination matchers:
```java
// A textView id is my_view and the text is "Hello!"
onView(allOf(withId(R.id.my_view), withText("Hello!")))
```
### You can also choose not to reverse any of the matchers:
```java
# A textView id is my_view and text is not "Unwanted" 
onView(allOf(withId(R.id.my_view), not(withText("Unwanted"))))
```
### Performing an action on a view
Click on the view:
```java
onView(withId(R.id.button)).perform(click());
```
If onView() does not find the target view, a NoMatchingViewException is thrown. You can examine the view hierarchy in the exception string to analyze why the matcher did not match any views.

If onView() finds multiple views that match the given matcher, an AmbiguousViewMatcherException is thrown.

You can execute more than one action with one perform call:
```java
onView(withId(R.id.edit_text)).perform(typeText("Hello"), click());
```
If the view you are working with is located inside a ScrollView (vertical or horizontal), consider preceding actions that require the view to be displayed—such as click() and typeText()—with scrollTo(). This ensures that the view is displayed before proceeding to the other action:
```java
onView(withId(R.id.button)).perform(scrollTo(), click());
```
### Checking view assertions
Check that a view has the text "Hello!":
```java
onView(withId(R.id.text_view)).check(matches(withText("Hello!")));
```

## Espresso recipes

### Matching a view next to another view

A layout could contain certain views that are not unique by themselves. For example, a repeating call button in a table of contacts could have the same R.id, contain the same text, and have the same properties as other call buttons within the view hierarchy.

Often, the non-unique view will be paired with some unique label that’s located next to it, such as a name of the contact next to the call button. In this case, you can use the hasSibling() matcher to narrow down your selection:
```java
onView(allOf(withText("7"), hasSibling(withText("item: 0")))).perform(click());
```
### Matching a view that is inside an action bar
```java
public void testClickActionBarItem() {
    // We make sure the contextual action bar is hidden.
    onView(withId(R.id.hide_contextual_action_bar))
        .perform(click());

    // Click on the icon - we can find it by the r.Id.
    onView(withId(R.id.action_save))
        .perform(click());

    // Verify that we have really clicked on the icon
    // by checking the TextView content.
    onView(withId(R.id.text_action_bar_result))
        .check(matches(withText("Save")));
}
```
### Clicking on items in the overflow menu
```java
public void testActionBarOverflow() {
    // Make sure we hide the contextual action bar.
    onView(withId(R.id.hide_contextual_action_bar))
        .perform(click());

    // Open the options menu OR open the overflow menu, depending on whether
    // the device has a hardware or software overflow menu button.
    openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
    // Open the overflow menu from contextual action mode.
    // openContextualActionModeOverflowMenu();

    // Click the item, overflow menu cannot use withId.
    onView(withText("World"))
        .perform(click());

    // Verify that we have really clicked on the icon by checking
    // the TextView content.
    onView(withId(R.id.text_action_bar_result))
        .check(matches(withText("World")));
}
```
### Asserting that a data item is not in an adapter
```java
private static Matcher<View> withAdaptedData(final Matcher<Object> dataMatcher) {
    return new TypeSafeMatcher<View>() {

        @Override
        public void describeTo(Description description) {
            description.appendText("with class name: ");
            dataMatcher.describeTo(description);
        }

        @Override
        public boolean matchesSafely(View view) {
            if (!(view instanceof AdapterView)) {
                return false;
            }

            @SuppressWarnings("rawtypes")
            Adapter adapter = ((AdapterView) view).getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                if (dataMatcher.matches(adapter.getItem(i))) {
                    return true;
                }
            }

            return false;
        }
    };
}

@SuppressWarnings("unchecked")
public void testDataItemNotInAdapter(){
    onView(withId(R.id.list))
          .check(matches(not(withAdaptedData(withItemContent("item: 168")))));
    }
}
```
### Matching a header or footer in a list view

For the LongListActivity:
```java
public static final String FOOTER = "FOOTER";

View footerView = layoutInflater.inflate(R.layout.list_item, listView, false);
((TextView) footerView.findViewById(R.id.item_content)).setText("count:");
((TextView) footerView.findViewById(R.id.item_size)).setText(String.valueOf(data.size()));
listView.addFooterView(footerView, FOOTER, true);
```
Then, you can write a matcher for the footer:
```java
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("unchecked")
public static Matcher<Object> isFooter() {
    return allOf(is(instanceOf(String.class)), is(LongListActivity.FOOTER));
}
```
And loading the view in a test is trivial:
```java
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.sample.LongListMatchers.isFooter;

public void testClickFooter() {
    onData(isFooter())
        .perform(click());
}
```
### ListView match a specific child view
```java
// click the view with id R.id.item_size in item 10
onData(withItemContent("item: 10"))
    .onChildView(withId(R.id.item_size))
    .perform(click());
```
### Interacting with recycler view list items

1. Open your app’s build.gradle file. This is usually not the top-level build.gradle file but app/build.gradle.
2. Add the following lines inside dependencies:
```java
androidTestImplementation 'com.android.support.test.espresso:espresso-contrib:3.0.0'
```
RecyclerView objects work differently than AdapterView objects, so onData() cannot be used to interact with them.

To interact with RecyclerViews using Espresso, you can use the espresso-contrib package, which has a collection of RecyclerViewActions that can be used to scroll to positions or to perform actions on items:
1. scrollTo() - Scrolls to the matched View.
2. scrollToHolder() - Scrolls to the matched View Holder.
3. scrollToPosition() - Scrolls to a specific position.
4. actionOnHolderItem() - Performs a View Action on a matched View Holder.
5. actionOnItem() - Performs a View Action on a matched View.
6. actionOnItemAtPosition() - Performs a ViewAction on a view at a specific position.
```java
@Test
public void scrollToItemBelowFoldCheckItsText() {
    // First, scroll to the position that needs to be matched and click on it.
    onView(ViewMatchers.withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_BELOW_THE_FOLD,
            click()));

    // Match the text in an item below the fold and check that it's displayed.
    String itemElementText = mActivityRule.getActivity().getResources()
            .getString(R.string.item_element_text)
            + String.valueOf(ITEM_BELOW_THE_FOLD);
    onView(withText(itemElementText)).check(matches(isDisplayed()));
}
```
### Using a custom failure handler

This failure handler throws a MySpecialException instead of a NoMatchingViewException and delegates all other failures to the DefaultFailureHandler.
```java
private static class CustomFailureHandler implements FailureHandler {
    private final FailureHandler delegate;

    public CustomFailureHandler(Context targetContext) {
        delegate = new DefaultFailureHandler(targetContext);
    }

    @Override
    public void handle(Throwable error, Matcher<View> viewMatcher) {
        try {
            delegate.handle(error, viewMatcher);
        } catch (NoMatchingViewException e) {
            throw new MySpecialException(e);
        }
    }
}

@Override
public void setUp() throws Exception {
    super.setUp();
    getActivity();
    setFailureHandler(new CustomFailureHandler(getInstrumentation()
                                              .getTargetContext()));
}
```
