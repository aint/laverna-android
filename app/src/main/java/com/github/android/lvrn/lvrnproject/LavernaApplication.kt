package com.github.android.lvrn.lvrnproject

import android.app.Application
import com.github.android.lvrn.lvrnproject.dagger.components.AppComponent
import com.github.android.lvrn.lvrnproject.dagger.components.DaggerAppComponent
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager.Companion.initializeInstance
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm
import com.github.android.lvrn.lvrnproject.util.CurrentState
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
class LavernaApplication : Application() {
    @Inject
    lateinit var profileService: ProfileService

    @Inject
    lateinit var notebookService: NotebookService

    @Inject
    lateinit var noteService: NoteService

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        AndroidThreeTen.init(this)
        initializeInstance(this)
        sAppComponent = DaggerAppComponent.create()
        sAppComponent?.inject(this)
        //TODO: temporary, remove later.
        profileService.openConnection()
        profileService.create(ProfileForm("default"))
        val profiles = profileService.getAll()
        profileId = profiles[0].id
        profileService.closeConnection()

//        if (profileId != null){
//        noteService.openConnection()
//        notebookService.openConnection()
//        for (i in 1..3) {
//            val optionalStringID = notebookService.create(NotebookForm(profileId!!, false, null, "notebook$i"))
//            for (j in 1..20) {
//                val otherString = notebookService.create(NotebookForm(profileId!!, false, optionalStringID.get(), "inner notebook for $i num $j"))
//                for (k in 1..20) {
//                    notebookService.create(NotebookForm(profileId!!, false, otherString.get(), "inner notebook for $i for $j num $k"))
//                    noteService.create(NoteForm(profileId!!, false, otherString.get(), "note $i $j $k", "content", "content", false))
//                }
//                noteService.create(NoteForm(profileId!!, false, optionalStringID.get(), "note $i $j", "content", "content", false))
//            }
//        }
//        notebookService.closeConnection()
//        noteService.closeConnection()
//
//        noteService.openConnection()
//        for (i in 0 until 50) {
//            noteService.create(NoteForm(profileId!!, true, null, "note$i", "dfsdf", "dfsdf", true))
//            noteService.create(NoteForm(profileId!!, false, null, "lol note$i", "lol dfsdf", "lol dfsdf", false))
//            noteService.create(NoteForm(profileId!!, false, null, "kek note$i", "lol dfsdf", "lol dfsdf", true))
//        }
//        noteService.closeConnection()
//
//        noteService.openConnection()
//        noteService.create(NoteForm(
//            profileId!!, false, "0", "title 1", "content\n" +
//                "[] first task\n" +
//                "[] second task\n" +
//                "[X] completed task", "htmlContent", false))
//        noteService.closeConnection()
//        }
    }

    companion object {
        var sAppComponent: AppComponent? = null

        fun getsAppComponent(): AppComponent {
            return sAppComponent!!
        }
    }
}

