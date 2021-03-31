package com.github.android.lvrn.lvrnproject.dagger.modules;

import androidx.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.repository.core.NoteRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NotebookRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TagRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TaskRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NoteRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NotebookRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.TagRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.TaskRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Bei Anrii <bei.andrii.dev@gmail.com>
 */

@Module
public abstract class RepositoryModule {

    @NonNull
    @Binds
    abstract NotebookRepository bindNotebooksRepository(NotebookRepositoryImpl notebookRepository);

    @NonNull
    @Binds
    abstract NoteRepository bindNotesRepository(NoteRepositoryImpl noteRepository);

    @NonNull
    @Binds
    abstract ProfileRepository bindProfilesRepository(ProfileRepositoryImpl profileRepository);

    @NonNull
    @Binds
    abstract TagRepository bindTagsRepository(TagRepositoryImpl tagRepository);

    @NonNull
    @Binds
    abstract TaskRepository bindTasksRepository(TaskRepositoryImpl taskRepository);
}
