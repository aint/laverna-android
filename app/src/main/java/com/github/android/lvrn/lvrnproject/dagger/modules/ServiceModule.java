package com.github.android.lvrn.lvrnproject.dagger.modules;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.repository.extension.NotebookRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.NoteRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.ProfileRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TagRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TaskRepository;
import com.github.android.lvrn.lvrnproject.service.extension.NotebookService;
import com.github.android.lvrn.lvrnproject.service.extension.NoteService;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.extension.TagService;
import com.github.android.lvrn.lvrnproject.service.extension.TaskService;
import com.github.android.lvrn.lvrnproject.service.extension.impl.NotebookServiceImpl;
import com.github.android.lvrn.lvrnproject.service.extension.impl.NoteServiceImpl;
import com.github.android.lvrn.lvrnproject.service.extension.impl.ProfileServiceImpl;
import com.github.android.lvrn.lvrnproject.service.extension.impl.TagServiceImpl;
import com.github.android.lvrn.lvrnproject.service.extension.impl.TaskServiceImpl;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Module
public class ServiceModule {

    @NonNull
    @Provides
    static NotebookService provideNotebooksService(NotebookRepository notebookRepository, ProfileService profileService) {
        return new NotebookServiceImpl(notebookRepository, profileService);
    }

    @NonNull
    @Provides
    static NoteService provideNoteService(NoteRepository noteRepository,
                                          TaskService taskService,
                                          TagService tagService,
                                          ProfileService profileService,
                                          NotebookService notebookService) {
        return new NoteServiceImpl(noteRepository, taskService, tagService, profileService, notebookService);
    }

    @NonNull
    @Provides
    static ProfileService provideProfilesService(ProfileRepository profileRepository) {
        return new ProfileServiceImpl(profileRepository);
    }

    @NonNull
    @Provides
    static TagService provideTagsService(TagRepository tagRepository, ProfileService profileService) {
        return new TagServiceImpl(tagRepository, profileService);
    }

    @NonNull
    @Provides
    static TaskService provideTasksService(TaskRepository taskRepository, ProfileService profileService) {
        return new TaskServiceImpl(taskRepository, profileService);
    }
}
