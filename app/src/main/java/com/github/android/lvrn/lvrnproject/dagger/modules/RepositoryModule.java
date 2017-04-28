package com.github.android.lvrn.lvrnproject.dagger.modules;

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
    public ProfilesRepositoryImpl provideProfilesRepository() {
        return new ProfilesRepositoryImpl();
    }

    @Provides
    public NotesRepositoryImpl provideNotesRepository() {
        return new NotesRepositoryImpl();
    }

    @Provides
    public NotebooksRepositoryImpl provideNotebooksRepository() {
        return new NotebooksRepositoryImpl();
    }

    @Provides
    public TagsRepositoryImpl provideTagsRepository() {
        return new TagsRepositoryImpl();
    }

    @Provides
    public TasksRepositoryImpl provideTaskRepository() {
        return new TasksRepositoryImpl();
    }
}
