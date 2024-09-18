package com.github.valhallalabs.laverna.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.dropbox.core.android.Auth
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.ActivityMainBinding
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.core.TagService
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivityImpl
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl.NotebookCreationDialogFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.impl.FavouritesListFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl.NotebooksListFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl.NotesListFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.impl.TasksListFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl.TrashListFragmentImpl
import com.github.android.lvrn.lvrnproject.view.util.consts.DIALOG_OPEN_FROM_MAIN_ACTIVITY
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_FAVOURITES_FRAGMENT
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_NOTEBOOK_CREATE_FRAGMENT
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_NOTEBOOK_FRAGMENT
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_NOTES_LIST_FRAGMENT
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_TASK_FRAGMENT
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_TRASH_FRAGMENT
import com.github.valhallalabs.laverna.service.DropboxClientFactory
import com.github.valhallalabs.laverna.service.DropboxService
import com.github.valhallalabs.laverna.service.SyncService
import com.google.android.material.navigation.NavigationView
import com.orhanobut.logger.Logger
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mToolBar: Toolbar
    lateinit var floatingActionsMenu: FloatingActionsMenu

    @Inject
    lateinit var noteService: NoteService

    @Inject
    lateinit var tagService: TagService

    @Inject
    lateinit var notebookService: NotebookService

    @Inject
    lateinit var profileService: ProfileService

    internal var fragmentManager = supportFragmentManager

    private var mSavedInstanceState: Bundle? = null

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mSavedInstanceState = savedInstanceState
        setContentView(binding.root)
        LavernaApplication.getsAppComponent().inject(this)

        setOrientationByUserDeviceConfiguration()


        mDrawerLayout = binding.drawerLayout
        mToolBar = binding.toolbar
        floatingActionsMenu = binding.floatingActionMenuAllNotes

        setSupportActionBar(mToolBar)
        val mToggle = ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            mToolBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        (findViewById<View>(R.id.nav_view) as NavigationView).setNavigationItemSelectedListener(this)

        menuStartSelectFragment(NotesListFragmentImpl(), TAG_NOTES_LIST_FRAGMENT)
        binding.floatingBtnStartNote.setOnClickListener { view -> startNoteEditorActivity() }
        binding.floatingBtnStartNotebook.setOnClickListener { view -> openNotebooksCreationDialog() }
    }

    override fun onPause() {
        super.onPause()
        floatingActionsMenu.collapse()
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
            return
        }

        super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (R.id.nav_item_notebooks == id) {
            val notebooksListFragment = NotebooksListFragmentImpl()
            menuStartSelectFragment(notebooksListFragment, TAG_NOTEBOOK_FRAGMENT)
        } else if (R.id.nav_item_all_notes == id) {
            val notesListFragment = NotesListFragmentImpl()
            menuStartSelectFragment(notesListFragment, TAG_NOTES_LIST_FRAGMENT)
        } else if (R.id.nav_item_open_tasks == id) {
            val taskFragment = TasksListFragmentImpl()
            menuStartSelectFragment(taskFragment, TAG_TASK_FRAGMENT)
        } else if (R.id.nav_item_trash == id) {
            val trashFragment = TrashListFragmentImpl()
            menuStartSelectFragment(trashFragment, TAG_TRASH_FRAGMENT)
        } else if (R.id.nav_item_favorites == id) {
            val favouritesListFragment = FavouritesListFragmentImpl()
            menuStartSelectFragment(favouritesListFragment, TAG_FAVOURITES_FRAGMENT)
        } else if (R.id.nav_item_sync == id) {
            var accessToken = getSharedPreferences(
                "dropbox-sample",
                Context.MODE_PRIVATE
            ).getString("access-token", null)
            if (accessToken == null) {
                Logger.w("accessToken is null")
                accessToken = Auth.getOAuth2Token()
                getSharedPreferences("dropbox-sample", Context.MODE_PRIVATE)
                    .edit()
                    .putString("access-token", accessToken)
                    .apply()
                Logger.w("accessToken %s saved to pref", accessToken)
            }

            Observable.defer { accessToken?.let { syncData(it).toObservable<Any>() } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        }
        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    //todo complete menu

    private fun syncData(accessToken: String): Completable {
        DropboxClientFactory.init(accessToken)
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        val dropboxService =
            DropboxService(noteService, notebookService, tagService, profileService, objectMapper)

        val syncService =
            SyncService(dropboxService, profileService, notebookService, noteService, tagService)
        syncService.pullData()
//        syncService.pushData()

        return Completable.complete()
    }

    /**
     * A method which check user device screen orientation at start app,
     * and set needed screen orientation for app work
     */
    private fun setOrientationByUserDeviceConfiguration() {
        if (resources.configuration.smallestScreenWidthDp < 600) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    /**
     * A method which create and replace new fragment in current container for fragment , with
     * adding to back stack
     *
     * @param fragment a fragment what we create
     * @param tag      a tag name of fragment
     */
    private fun menuStartSelectFragment(fragment: Fragment, tag: String) {
        if (mSavedInstanceState == null) {
            fragmentManager.commit {
                setCustomAnimations(
                    R.anim.enter_from_top,
                    R.anim.exit_to_bottom,
                    R.anim.enter_from_top,
                    R.anim.exit_to_bottom
                )
                replace(R.id.constraint_container, fragment, tag)
            }
        }
    }

    fun startNoteEditorActivity() {
        startActivity(Intent(this, NoteEditorActivityImpl::class.java))
        finish()
    }

    fun openNotebooksCreationDialog() {
        val fragmentTransaction = supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)

        NotebookCreationDialogFragmentImpl
            .newInstance(DIALOG_OPEN_FROM_MAIN_ACTIVITY)
            .show(fragmentTransaction, TAG_NOTEBOOK_CREATE_FRAGMENT)

        floatingActionsMenu.collapse()
    }
}
