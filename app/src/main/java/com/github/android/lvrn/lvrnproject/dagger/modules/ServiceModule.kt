package com.github.android.lvrn.lvrnproject.dagger.modules

import com.github.android.lvrn.lvrnproject.service.core.*
import com.github.android.lvrn.lvrnproject.service.core.impl.*
import dagger.Binds
import dagger.Module

@Module
abstract class ServiceModule {

    @Binds
    abstract fun bindNotebooksService(notebookService: NotebookServiceImpl?): NotebookService

    @Binds
    abstract fun bindNoteService(noteService: NoteServiceImpl?): NoteService

    @Binds
    abstract fun bindProfilesService(profileService: ProfileServiceImpl?): ProfileService

    @Binds
    abstract fun bindTagsService(tagService: TagServiceImpl?): TagService

    @Binds
    abstract fun bindTasksService(taskService: TaskServiceImpl?): TaskService
}