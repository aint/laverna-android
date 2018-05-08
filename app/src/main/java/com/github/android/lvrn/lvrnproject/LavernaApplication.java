package com.github.android.lvrn.lvrnproject;

import android.app.Application;
import android.content.SharedPreferences;

import com.dropbox.core.android.Auth;
import com.github.android.lvrn.lvrnproject.dagger.components.AppComponent;
import com.github.android.lvrn.lvrnproject.dagger.components.DaggerAppComponent;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.valhallalabs.laverna.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.persistent.entity.Note;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

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
        Logger.addLogAdapter(new AndroidLogAdapter());
        DatabaseManager.initializeInstance(this);
        sAppComponent = DaggerAppComponent.create();
        sAppComponent.inject(this);

        profileService.openConnection();
        for (Profile profile : profileService.getAll()) {
            Logger.w("PROFILE %s", profile.getName());
            profileId = profile.getId();
        }
        profileService.closeConnection();

        notebookService.openConnection();
        Logger.w("INSIDE");
        for (Notebook notebook : notebookService.getByProfile(profileId, new PaginationArgs())) {
            noteService.openConnection();
            List<Note> notes = noteService.getByNotebook(notebook.getId(), new PaginationArgs());
            for (Note note : notes) {
                Logger.w("NOTE %s", note);
            }
            noteService.closeConnection();
        }
        notebookService.closeConnection();



        if (!hasToken()) {
            Logger.w("No Dropbox access token, start OAuth2 authentication");
            Auth.startOAuth2Authentication(LavernaApplication.this, getString(R.string.app_key));
        }
    }

    protected boolean hasToken() {
        SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
        String token = prefs.getString("access-token", null);
        Logger.w("Dropbox token = " + token);
        return token != null;
    }

    public static AppComponent getsAppComponent() {
        return sAppComponent;
    }

}
