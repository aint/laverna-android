package com.github.android.lvrn.lvrnproject.view.activity.main;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dropbox.core.android.Auth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
//import com.github.android.lvrn.lvrnproject.DropboxService;
import com.github.valhallalabs.laverna.persistent.entity.Profile;
import com.github.valhallalabs.laverna.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.core.TagService;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.service.DropboxClientFactory;
import com.github.valhallalabs.laverna.service.DropboxService;
import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivityImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl.NotebookCreationDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.impl.FavouritesListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl.NotebooksListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl.NotesListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.impl.TasksListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl.TrashListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConst;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivityImpl extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject NoteService noteService;
    @Inject TagService tagService;
    @Inject NotebookService notebookService;
    @Inject ProfileService profileService;

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar) Toolbar mToolBar;

    @BindView(R.id.floating_action_menu_all_notes) public FloatingActionsMenu floatingActionsMenu;


    private Bundle mSavedInstanceState;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;

        LavernaApplication.getsAppComponent().inject(this);

        setContentView(R.layout.activity_main);
        setOrientationByUserDeviceConfiguration();
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);

        NotesListFragmentImpl notesListFragment = new NotesListFragmentImpl();
        menuStartSelectFragment(notesListFragment, FragmentConst.TAG_NOTES_LIST_FRAGMENT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        floatingActionsMenu.collapse();
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (R.id.nav_item_notebooks == id) {
            NotebooksListFragmentImpl notebooksListFragment = new NotebooksListFragmentImpl();
            menuStartSelectFragment(notebooksListFragment, FragmentConst.TAG_NOTEBOOK_FRAGMENT);
        } else if (R.id.nav_item_all_notes == id) {
            NotesListFragmentImpl notesListFragment = new NotesListFragmentImpl();
            menuStartSelectFragment(notesListFragment, FragmentConst.TAG_NOTES_LIST_FRAGMENT);
        } else if (R.id.nav_item_open_tasks == id) {
            TasksListFragmentImpl taskFragment = new TasksListFragmentImpl();
            menuStartSelectFragment(taskFragment, FragmentConst.TAG_TASK_FRAGMENT);
        } else if (R.id.nav_item_trash == id) {
            TrashListFragmentImpl trashFragment = new TrashListFragmentImpl();
            menuStartSelectFragment(trashFragment, FragmentConst.TAG_TRASH_FRAGMENT);
        } else if (R.id.nav_item_favorites == id) {
            FavouritesListFragmentImpl favouritesListFragment = new FavouritesListFragmentImpl();
            menuStartSelectFragment(favouritesListFragment, FragmentConst.TAG_FAVOURITES_FRAGMENT);
        } else if (R.id.nav_item_sync == id) {

            SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
            String accessToken = prefs.getString("access-token", null);
            if (accessToken == null) {
                Logger.w("accessToken is null");
                accessToken = Auth.getOAuth2Token();
                getSharedPreferences("dropbox-sample", MODE_PRIVATE)
                        .edit()
                        .putString("access-token", accessToken)
                        .apply();
                Logger.w("accessToken " + accessToken + " saved to pref");
            }
            final String t = accessToken;
            Thread thread = new Thread(() -> {
                try  {
                    initAndLoadData(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            thread.start();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initAndLoadData(String accessToken) {
        DropboxClientFactory.init(accessToken);

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new KotlinModule());

        DropboxService dropboxService = new DropboxService(noteService, notebookService, tagService, profileService, objectMapper);

        Logger.e("IMPORT PROFILES");
        dropboxService.importProfiles();
        Logger.e("END IMPORT PROFILES");

        Logger.e("START IMPORT NOTEBOOKS & NOTES & TAGS");
        profileService.openConnection();
        for (Profile profile : profileService.getAll()) {
            CurrentState.profileId = profile.getId();
            String profileId = profile.getId();
            String profileName = profile.getName();
            Logger.w("CURRENT PROFILE %s", profileName);
            dropboxService.importNotebooks(profileId, profileName);

            dropboxService.importNotes(profileId, profileName);

            dropboxService.importTags(profileId, profileName);
        }
        profileService.closeConnection();
        Logger.e("END IMPORT NOTEBOOKS & NOTES & TAGS");

        tagService.openConnection();
        for (Tag tag : tagService.getByProfile(CurrentState.profileId, new PaginationArgs())) {
            Logger.e("TAG %s", tag.getName());
        }
        tagService.closeConnection();

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
//                    .addToBackStack(tag)
                    .commit();
        }

    }

    @OnClick(R.id.floating_btn_start_note)
    public void startNoteEditorActivity() {
        startActivity(new Intent(this, NoteEditorActivityImpl.class));
        finish();
    }

    @OnClick(R.id.floating_btn_start_notebook)
    public void openNotebooksCreationDialog() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        DialogFragment dialogFragment = NotebookCreationDialogFragmentImpl.newInstance(FragmentConst.DIALOG_OPEN_FROM_MAIN_ACTIVITY);
        dialogFragment.show(fragmentTransaction, FragmentConst.TAG_NOTEBOOK_CREATE_FRAGMENT);
        floatingActionsMenu.collapse();
    }
}