package com.github.android.lvrn.lvrnproject;

import android.app.Application;

import com.github.android.lvrn.lvrnproject.dagger.components.AppComponent;
import com.github.android.lvrn.lvrnproject.dagger.components.DaggerAppComponent;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.valhallalabs.laverna.persistent.entity.Profile;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */


public class LavernaApplication extends Application {
    private static AppComponent sAppComponent;

    @Inject
    ProfileService profileService;
    @Inject
    NotebookService notebookService;
    @Inject
    NoteService noteService;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        AndroidThreeTen.init(this);
        DatabaseManager.Companion.initializeInstance(this);
        sAppComponent = DaggerAppComponent.create();
        sAppComponent.inject(this);
        //TODO: temporary, remove later.
        profileService.openConnection();
        profileService.create(new ProfileForm("default"));
        List<Profile> profiles = profileService.getAll();
        CurrentState.Companion.setProfileId(profiles.get(0).getId());
        profileService.closeConnection();


//        noteService.openConnection();
//        notebookService.openConnection();
//        for(int i = 1; i <= 3; i++) {
//
//            Optional<String> optionalStringID = notebookService.create(new NotebookForm(CurrentState.Companion.getProfileId(), false, null, "notebook" + i));
//            for(int j = 1; j <=20; j++) {
//                Optional<String> otherString = notebookService.create(new NotebookForm(CurrentState.Companion.getProfileId(), false, optionalStringID.get(), "inner noteboook for " + i + " num " + j));
//                for(int k = 1; k <= 20; k++) {
//                    notebookService.create(new NotebookForm(CurrentState.Companion.getProfileId(), false, otherString.get(), "inner noteboook for " + i +" for " + j + " num " + k));
//                    noteService.create(new NoteForm(CurrentState.Companion.getProfileId(), false, otherString.get(), "note " + i + " " + j + " " + k, "content", "content", false));
//                }
//                noteService.create(new NoteForm(CurrentState.Companion.getProfileId(), false, optionalStringID.get(), "note " + i + " " + j, "content", "content", false));
//            }
//
//
//        }
//        notebookService.closeConnection();
//        noteService.closeConnection();
//
//
//        noteService.openConnection();
//        for (int i = 0; i < 50; i++) {
//            noteService.create(new NoteForm(CurrentState.Companion.getProfileId(), true, null, "note" + i, "dfsdf", "dfsdf", true));
//            noteService.create(new NoteForm(CurrentState.Companion.getProfileId(), false, null, "lol note" + i, "lol dfsdf", "lol dfsdf", false));
//            noteService.create(new NoteForm(CurrentState.Companion.getProfileId(), false, null, "kek note" + i, "lol dfsdf", "lol dfsdf", true));
//        }
//        noteService.closeConnection();
//        noteService.openConnection();
//        noteService.create(new NoteForm(CurrentState.Companion.getProfileId(), false, "0", "title 1", "content\n" +
//                "[] first task\n" +
//                "[] second task\n" +
//                "[X] completed task", "htmlContent",false));
//
//        noteService.closeConnection();


    }

    public static AppComponent getsAppComponent() {
        return sAppComponent;
    }

}
