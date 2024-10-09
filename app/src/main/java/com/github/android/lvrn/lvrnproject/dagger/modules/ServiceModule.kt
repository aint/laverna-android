package com.github.android.lvrn.lvrnproject.dagger.modules


import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.core.TagService
import com.github.android.lvrn.lvrnproject.service.core.TaskService
import com.github.android.lvrn.lvrnproject.service.core.impl.NoteServiceImpl
import com.github.android.lvrn.lvrnproject.service.core.impl.NotebookServiceImpl
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileServiceImpl
import com.github.android.lvrn.lvrnproject.service.core.impl.TagServiceImpl
import com.github.android.lvrn.lvrnproject.service.core.impl.TaskServiceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ServiceModule {

    @Binds
    abstract fun bindNotebooksService(notebookService: NotebookServiceImpl): NotebookService

    @Binds
    abstract fun bindNoteService(noteService: NoteServiceImpl): NoteService

    @Binds
    abstract fun bindProfilesService(profileService: ProfileServiceImpl): ProfileService

    @Binds
    abstract fun bindTagsService(tagService: TagServiceImpl): TagService

    @Binds
    abstract fun bindTasksService(taskService: TaskServiceImpl): TaskService
}