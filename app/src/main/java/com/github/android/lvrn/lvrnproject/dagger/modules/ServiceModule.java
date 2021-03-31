package com.github.android.lvrn.lvrnproject.dagger.modules;

import androidx.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.repository.core.NotebookRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NoteRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TagRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TaskRepository;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.core.TagService;
import com.github.android.lvrn.lvrnproject.service.core.TaskService;
import com.github.android.lvrn.lvrnproject.service.core.impl.NotebookServiceImpl;
import com.github.android.lvrn.lvrnproject.service.core.impl.NoteServiceImpl;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileServiceImpl;
import com.github.android.lvrn.lvrnproject.service.core.impl.TagServiceImpl;
import com.github.android.lvrn.lvrnproject.service.core.impl.TaskServiceImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Bei Anrii <bei.andrii.dev@gmail.com>
 */

@Module(includes = RepositoryModule.class)
public abstract class ServiceModule {

    @NonNull
    @Binds
    abstract NotebookService bindNotebooksService(NotebookServiceImpl notebookService);

    @NonNull
    @Binds
    abstract NoteService bindNoteService(NoteServiceImpl noteService);

    @NonNull
    @Binds
    abstract ProfileService bindProfilesService(ProfileServiceImpl profileService);

    @NonNull
    @Binds
    abstract TagService bindTagsService(TagServiceImpl tagService);

    @NonNull
    @Binds
    abstract TaskService bindTasksService(TaskServiceImpl taskService);
}
