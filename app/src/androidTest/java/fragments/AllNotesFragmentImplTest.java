package fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.activities.MainActivityImpl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isRightOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Created by psihey on 27.05.17.
 */

@RunWith(AndroidJUnit4.class)
public class AllNotesFragmentImplTest {

    @Rule
    public ActivityTestRule<MainActivityImpl> mActivityRule =
            new ActivityTestRule<>(MainActivityImpl.class);

    @Test
    public void startNoteEditorActivityFromFloatingMenuButton() {
        onView(withId(R.id.floating_btn_start_note))
                .perform(click());

        onView(withId(R.id.floating_btn_start_note))
                .perform(click());

        onView(withId(R.id.edit_text_editor)).check(matches(isDisplayed()));
    }

    @Test
    public void floatingButtonMenuToRight() {
        onView(withId(R.id.floating_action_menu_all_notes))
                .check(isRightOf(withId(R.id.nav_view)));
        ;
    }

    @Test
    public void hideFloatingButtonMenuWhenSearchMode() {
        onView(withId(R.id.item_action_search))
                .perform(click());

        onView(withId(R.id.floating_action_menu_all_notes))
                .check(matches(not(isDisplayed())));
    }


}
