package com.github.android.lvrn.lvrnproject;

import android.app.Application;

import com.github.android.lvrn.lvrnproject.dagger.components.AppComponent;
import com.github.android.lvrn.lvrnproject.dagger.components.DaggerAppComponent;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;

import java.util.List;

import javax.inject.Inject;

import static com.github.android.lvrn.lvrnproject.util.CurrentState.profileId;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */


public class LavernaApplication extends Application {
    private static AppComponent sAppComponent;

    @Inject ProfileService profileService;
    @Inject NotebookService notebookService;
    @Inject NoteService noteService;

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.initializeInstance(this);
        sAppComponent = DaggerAppComponent.create();
        sAppComponent.inject(this);
        //TODO: temporary, remove later.
        profileService.openConnection();
        profileService.create(new ProfileForm("default"));
        List<Profile> profiles = profileService.getAll();
        profileId = profiles.get(0).getId();
        profileService.closeConnection();
        notebookService.openConnection();


        for(int i = 1; i <30; i++) {
            notebookService.create(new NotebookForm(profileId, false, null, "notebook" + i));
        }
        notebookService.closeConnection();


        noteService.openConnection();
        for (int i = 0; i < 150; i++) {
            noteService.create(new NoteForm(profileId, true, null, "note" + i, "dfsdf", "dfsdf", false));
            noteService.create(new NoteForm(profileId, false, null, "lol note" + i, "lol dfsdf", "lol dfsdf", false));
        }
        noteService.closeConnection();






    }

    public static AppComponent getsAppComponent() {
        return sAppComponent;
    }

}
