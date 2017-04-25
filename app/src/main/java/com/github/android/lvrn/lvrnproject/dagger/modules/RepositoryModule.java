package com.github.android.lvrn.lvrnproject.dagger.modules;

import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotebooksRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotesRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfilesRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.TagsRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.TasksRepository;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Module
public class RepositoryModule {

    @Provides
    public ProfilesRepository provideProfilesRepository() {
        return new ProfilesRepository();
    }

    @Provides
    public NotesRepository provideNotesRepository() {
        return new NotesRepository();
    }

    @Provides
    public NotebooksRepository provideNotebooksRepository() {
        return new NotebooksRepository();
    }

    @Provides
    public TagsRepository provideTagsRepository() {
        return new TagsRepository();
    }

    @Provides
    public TasksRepository provideTaskRepository() {
        return new TasksRepository();
    }
}
