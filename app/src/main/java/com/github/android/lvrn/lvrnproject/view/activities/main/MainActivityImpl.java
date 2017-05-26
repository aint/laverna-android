package com.github.android.lvrn.lvrnproject.view.activities.main;


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
import com.github.android.lvrn.lvrnproject.view.fragments.NotebookFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragments.allnotes.impl.AllNotesFragmentImpl;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        if (getResources().getConfiguration().smallestScreenWidthDp < 600) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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
            NotebookFragmentImpl notebookFragment = new NotebookFragmentImpl();
            menuStartSelectFragment(notebookFragment, TagFragmentConst.TAG_NOTEBOOK_FRAGMENT);
        } else if(R.id.nav_item_all_notes == id){
            startAllNotesFragment();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startAllNotesFragment() {
        AllNotesFragmentImpl allNotesFragment = new AllNotesFragmentImpl();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.constraint_container, allNotesFragment, TagFragmentConst.TAG_ALL_NOTES_FRAGMENT)
                .commit();
    }

    private void menuStartSelectFragment(Fragment fragment, String tag) {
        if (mSavedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.constraint_container, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }

    }

}