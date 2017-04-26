package com.github.android.lvrn.lvrnproject.dagger.modules;

import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotebooksRepositoryImp;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotesRepositoryImp;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfilesRepositoryImp;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.TagsRepositoryImp;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.TasksRepositoryImp;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Module
public class RepositoryModule {

    @Provides
    public ProfilesRepositoryImp provideProfilesRepository() {
        return new ProfilesRepositoryImp();
    }

    @Provides
    public NotesRepositoryImp provideNotesRepository() {
        return new NotesRepositoryImp();
    }

    @Provides
    public NotebooksRepositoryImp provideNotebooksRepository() {
        return new NotebooksRepositoryImp();
    }

    @Provides
    public TagsRepositoryImp provideTagsRepository() {
        return new TagsRepositoryImp();
    }

    @Provides
    public TasksRepositoryImp provideTaskRepository() {
        return new TasksRepositoryImp();
    }
}
