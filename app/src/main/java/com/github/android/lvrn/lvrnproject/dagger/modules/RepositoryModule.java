package com.github.android.lvrn.lvrnproject.dagger.modules;

import com.github.android.lvrn.lvrnproject.persistent.repository.NotebooksRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.NotesRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfilesRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.TagsRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.TasksRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotebooksRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotesRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfilesRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.TagsRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.TasksRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Module
public class RepositoryModule {

    @Provides
    static NotebooksRepository provideNotebooksRepository() {
        return new NotebooksRepositoryImpl();
    }

    @Provides
    static NotesRepository provideNotesRepository() {
        return new NotesRepositoryImpl();
    }

    @Provides
    static ProfilesRepository provideProfilesRepository() {
        return new ProfilesRepositoryImpl();
    }

    @Provides
    static TagsRepository provideTagsRepository() {
        return new TagsRepositoryImpl();
    }

    @Provides
    static TasksRepository provideTasksRepository() {
        return new TasksRepositoryImpl();
    }
}
