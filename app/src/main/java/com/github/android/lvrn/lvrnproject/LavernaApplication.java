package com.github.android.lvrn.lvrnproject;

import android.app.Application;

import com.github.android.lvrn.lvrnproject.dagger.components.AppComponent;
import com.github.android.lvrn.lvrnproject.dagger.components.DaggerAppComponent;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.service.NotebooksService;
import com.github.android.lvrn.lvrnproject.service.NotesService;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.TagsService;
import com.github.android.lvrn.lvrnproject.service.TasksService;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class LavernaApplication extends Application {

    @Inject NotebooksService notebooksService;
    @Inject
    NotesService notesService;
    @Inject ProfilesService profilesService;
    @Inject TagsService tagsService;
    @Inject
    TasksService tasksService;




    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.initializeInstance(this);
        AppComponent appComponent = DaggerAppComponent.create();
        appComponent.inject(this);

        profilesService.openConnection();
        profilesService.closeConnection();

        notebooksService.openConnection();
        notebooksService.closeConnection();
//
        notesService.openConnection();
        notesService.closeConnection();

        tagsService.openConnection();
        tagsService.closeConnection();

        tasksService.openConnection();
        tasksService.closeConnection();

        System.out.println("FINISH");
    }
}
