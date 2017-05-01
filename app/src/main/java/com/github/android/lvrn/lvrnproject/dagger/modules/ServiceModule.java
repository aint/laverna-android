package com.github.android.lvrn.lvrnproject.dagger.modules;

import com.github.android.lvrn.lvrnproject.persistent.repository.NotebooksRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.NotesRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfilesRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.TagsRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.TasksRepository;
import com.github.android.lvrn.lvrnproject.service.NotebooksService;
import com.github.android.lvrn.lvrnproject.service.NotesService;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.TagsService;
import com.github.android.lvrn.lvrnproject.service.TasksService;
import com.github.android.lvrn.lvrnproject.service.impl.NotebooksServiceImpl;
import com.github.android.lvrn.lvrnproject.service.impl.NotesServiceImpl;
import com.github.android.lvrn.lvrnproject.service.impl.ProfilesServiceImpl;
import com.github.android.lvrn.lvrnproject.service.impl.TagsServiceImpl;
import com.github.android.lvrn.lvrnproject.service.impl.TasksServiceImpl;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Module
public class ServiceModule {

    @Provides
    static NotebooksService provideNotebooksService(NotebooksRepository notebooksRepository, ProfilesService profilesService) {
        return new NotebooksServiceImpl(notebooksRepository, profilesService);
    }

    @Provides
    static NotesService provideNoteService(NotesRepository notesRepository,
                                           TasksService tasksService,
                                           TagsService tagsService,
                                           ProfilesService profilesService,
                                           NotebooksService notebooksService) {
        return new NotesServiceImpl(notesRepository, tasksService, tagsService, profilesService, notebooksService);
    }

    @Provides
    static ProfilesService provideProfilesService(ProfilesRepository profilesRepository) {
        return new ProfilesServiceImpl(profilesRepository);
    }

    @Provides
    static TagsService provideTagsService(TagsRepository tagsRepository, ProfilesService profilesService) {
        return new TagsServiceImpl(tagsRepository, profilesService);
    }

    @Provides
    static TasksService provideTasksService(TasksRepository tasksRepository, ProfilesService profilesService) {
        return new TasksServiceImpl(tasksRepository, profilesService);
    }
}
