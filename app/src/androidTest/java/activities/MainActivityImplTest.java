package activities;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.activities.MainActivityImpl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by psihey on 27.05.17.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityImplTest {

    @Rule
    public ActivityTestRule<MainActivityImpl> mActivityRule =
            new ActivityTestRule<>(MainActivityImpl.class);

    @Test
    public void checkToolbar() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(matches(isEnabled()));
        onView(withText("All Notes")).check(matches(isDisplayed()));
    }

    @Test
    public void openNotebookFromNavigationView() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(open());
        onView(withText(R.string.drawer_main_menu_notebooks))
                .perform(click());

    }


}
