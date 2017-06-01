package com.github.android.lvrn.lvrnproject.view.activity.main;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.fragment.allnotebooks.impl.AllNotebooksFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl.NotesListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.alltasks.impl.AllTasksFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl.TrashListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.TagFragmentConst;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityImpl extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    private Bundle mSavedInstanceState;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        setOrientationByUserDeviceConfiguration();
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        startAllNotesFragment();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (R.id.nav_item_notebooks == id) {
            AllNotebooksFragmentImpl notebookFragment = new AllNotebooksFragmentImpl();
            menuStartSelectFragment(notebookFragment, TagFragmentConst.TAG_NOTEBOOK_FRAGMENT);
        } else if (R.id.nav_item_all_notes == id) {
            startAllNotesFragment();
        } else if (R.id.nav_item_open_tasks == id) {
            AllTasksFragmentImpl taskFragment = new AllTasksFragmentImpl();
            menuStartSelectFragment(taskFragment, TagFragmentConst.TAG_TASK_FRAGMENT);
        } else if (R.id.nav_item_trash == id) {
            TrashListFragmentImpl trashFragment = new TrashListFragmentImpl();
            menuStartSelectFragment(trashFragment,TagFragmentConst.TAG_TRASH_FRAGMENT);
        }
            mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A method which check user device screen orientation at start app,
     * and set needed screen orientation for app work
     */
    private void setOrientationByUserDeviceConfiguration() {
        if (getResources().getConfiguration().smallestScreenWidthDp < 600) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * A method which create and replace new fragment in current container for fragment, without
     * adding to back stack
     */
    private void startAllNotesFragment() {
        if (mSavedInstanceState == null) {
            NotesListFragmentImpl allNotesFragment = new NotesListFragmentImpl();
            fragmentManager.beginTransaction()
                    .add(R.id.constraint_container, allNotesFragment, TagFragmentConst.TAG_ALL_NOTES_FRAGMENT)
                    .commit();
        }
    }

    /**
     * A method which create and replace new fragment in current container for fragment , with
     * adding to back stack
     *
     * @param fragment a fragment what we create
     * @param tag      a tag name of fragment
     */
    private void menuStartSelectFragment(Fragment fragment, String tag) {
        if (mSavedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.constraint_container, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }

    }

}