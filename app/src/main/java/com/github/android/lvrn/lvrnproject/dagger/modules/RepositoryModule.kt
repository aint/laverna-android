package com.github.android.lvrn.lvrnproject.dagger.modules

import com.github.android.lvrn.lvrnproject.persistent.repository.core.*
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.*
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindNotebooksRepository(notebookRepository: NotebookRepositoryImpl?): NotebookRepository

    @Binds
    abstract fun bindNotesRepository(noteRepository: NoteRepositoryImpl?): NoteRepository

    @Binds
    abstract fun bindProfilesRepository(profileRepository: ProfileRepositoryImpl?): ProfileRepository

    @Binds
    abstract fun bindTagsRepository(tagRepository: TagRepositoryImpl?): TagRepository

    @Binds
    abstract fun bindTasksRepository(taskRepository: TaskRepositoryImpl?): TaskRepository

}