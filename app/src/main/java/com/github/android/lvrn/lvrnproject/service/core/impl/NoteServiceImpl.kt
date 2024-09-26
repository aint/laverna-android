package com.github.android.lvrn.lvrnproject.service.core.impl

import android.text.TextUtils
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NoteRepository
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.core.TagService
import com.github.android.lvrn.lvrnproject.service.core.TaskService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.service.form.TagForm
import com.github.android.lvrn.lvrnproject.service.form.TaskForm
import com.github.android.lvrn.lvrnproject.service.impl.TrashDependedServiceImpl
import com.github.android.lvrn.lvrnproject.service.util.NoteTextParser
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Tag
import com.github.valhallalabs.laverna.persistent.entity.Task
import java.util.Optional
import java.util.UUID
import java.util.function.Consumer
import javax.inject.Inject

class NoteServiceImpl @Inject constructor(
    val mNoteRepository: NoteRepository,
    profileService: ProfileService,
    val mTaskService: TaskService,
    val mTagService: TagService,
    val mNotebookService: NotebookService,
) : TrashDependedServiceImpl<Note, NoteForm>(mNoteRepository, profileService), NoteService {

    override fun create(noteForm: NoteForm): Optional<String> {
        val noteId = UUID.randomUUID().toString()
        noteForm.content = NoteTextParser.parseSingleQuotes(noteForm.content)
        if (validateForCreate(
                noteForm.profileId,
                noteForm.notebookId,
                noteForm.title
            ) && mNoteRepository.add(noteForm.toEntity(noteId))
        ) {
            parseContent(noteForm.profileId, noteId, noteForm.content)
            return Optional.of(noteId)
        }
        return Optional.empty()
    }

    override fun save(note: Note) {
        mNoteRepository.add(note)
    }

    override fun getByTitle(
        profileId: String,
        title: String,
        paginationArgs: PaginationArgs,
    ): List<Note> {
        return mNoteRepository.getByTitle(profileId, title, paginationArgs)
    }

    override fun getTrashByTitle(
        profileId: String,
        title: String,
        paginationArgs: PaginationArgs,
    ): List<Note> {
        return mNoteRepository.getTrashByTitle(profileId, title, paginationArgs)
    }

    override fun getByNotebook(notebookId: String, paginationArgs: PaginationArgs): List<Note> {
        return mNoteRepository.getByNotebook(notebookId, paginationArgs)
    }

    override fun getFavourites(profileId: String, paginationArgs: PaginationArgs): List<Note> {
        return mNoteRepository.getFavourites(profileId, paginationArgs)
    }

    override fun getFavouritesByTitle(
        profileId: String,
        title: String,
        paginationArgs: PaginationArgs,
    ): List<Note> {
        return mNoteRepository.getFavouritesByTitle(profileId, title, paginationArgs)
    }

    override fun getByTag(tagId: String, paginationArgs: PaginationArgs): List<Note> {
        return mNoteRepository.getByTag(tagId, paginationArgs)
    }

    override fun setNoteFavourite(entityId: String): Boolean {
        return mNoteRepository.changeNoteFavouriteStatus(entityId, true)
    }

    override fun setNoteUnFavourite(entityId: String): Boolean {
        return mNoteRepository.changeNoteFavouriteStatus(entityId, false)
    }

    override fun update(id: String, noteForm: NoteForm): Boolean {
        noteForm.content = NoteTextParser.parseSingleQuotes(noteForm.content)
        if (validateForUpdate(noteForm.notebookId, noteForm.title) && mNoteRepository.update(
                noteForm.toEntity(id)
            )
        ) {
            parseContent(mNoteRepository.getById(id).get().profileId, id, noteForm.content)
            return true
        }
        return false
    }

    /**
     * A method which calls methods of parsing tasks and tags from a note content.
     * @param profileId an id of a profile.
     * @param noteId an id of a note.
     * @param content a text content from a note.
     */
    private fun parseContent(profileId: String, noteId: String, content: String) {
        parseTasks(profileId, noteId, content)
        parseTags(profileId, noteId, content)
    }

    /**
     * A method which parses tags from a note content and then creates new tags or removes relations
     * between a note and tags.
     * @param profileId an id of a profile.
     * @param noteId an id of note.
     * @param content a text content from a note.
     */
    private fun parseTags(profileId: String, noteId: String, content: String) {
        val tagNames = NoteTextParser.parseTags(content)
        mTagService.openConnection()
        mTagService.getByNote(noteId).forEach(Consumer<Tag> { tag: Tag ->
            removeTagsFromNote(
                tag,
                tagNames,
                noteId
            )
        })
        tagNames.forEach(Consumer { tagName: String ->
            createTagAndAddToNote(
                profileId,
                tagName,
                noteId
            )
        })
        mTagService.closeConnection()
    }

    /**
     * A method which removes a relation a note and tag, if the last one is not present in content
     * anymore.
     * @param tag a tag to check.
     * @param tagNames a set of tags parsed from a note.
     * @param noteId an id of a note.
     */
    private fun removeTagsFromNote(tag: Tag, tagNames: MutableSet<String>, noteId: String) {
        if (tagNames.contains(tag.name)) {
            tagNames.remove(tag.name)
        } else {
            mNoteRepository.removeTagFromNote(noteId, tag.id)
        }
    }

    /**
     * A method which creates a tag and a relation between the tag and a note.
     * @param profileId an id of a profile.
     * @param tagName a name of a tag.
     * @param noteId an id on a note.
     */
    private fun createTagAndAddToNote(profileId: String, tagName: String, noteId: String) {
        val tags: List<Tag> = mTagService.getByName(profileId, tagName, PaginationArgs(0, 1))
        if (tags.size != 0) {
            mNoteRepository.addTagToNote(noteId, tags[0].id)
            return
        }
        val tagIdOptional: Optional<String> = mTagService.create(TagForm(profileId, tagName))
        if (tagIdOptional.isPresent) {
            mNoteRepository.addTagToNote(noteId, tagIdOptional.get())
        }
    }

    /**
     * A method which parses tasks from a note content and then creates/updated/removes it in database.
     * @param profileId an id of a profile.
     * @param noteId an id of note.
     * @param content a text content from a note.
     */
    private fun parseTasks(profileId: String, noteId: String, content: String) {
        val tasksFromNote = NoteTextParser.parseTasks(content)
        mTaskService.openConnection()
        mTaskService.getByNote(noteId).forEach(Consumer<Task> { task: Task ->
            updateOrRemoveExistedTasks(
                task,
                tasksFromNote
            )
        })
        tasksFromNote.forEach { (description: String?, status: Boolean?) ->
            mTaskService.create(
                TaskForm(
                    profileId, noteId,
                    description!!,
                    status!!
                )
            )
        }
        mTaskService.closeConnection()
    }

    /**
     * A method which updates a received task if it presents in database or removes it in other case.
     * @param task a task to update or remove.
     * @param tasksFromNote a map of tasks parsed from a note content.
     */
    private fun updateOrRemoveExistedTasks(task: Task, tasksFromNote: MutableMap<String, Boolean>) {
        if (tasksFromNote.containsKey(task.description)) {
            mTaskService.update(
                task.id, TaskForm(
                    task.profileId,
                    task.noteId,
                    task.description,
                    tasksFromNote[task.description]!!
                )
            )

            tasksFromNote.remove(task.description)
        } else {
            mTaskService.remove(task.id)
        }
    }

    /**
     * A method which validates a form in the create method.
     * @param profileId an id of a profile.
     * @param notebookId an parent notebook id.
     * @param title a title of an entity.
     * @return a boolean result of a validation.
     */
    private fun validateForCreate(profileId: String, notebookId: String, title: String): Boolean {
        return super.checkProfileExistence(profileId) && checkNotebookExistence(notebookId) && !TextUtils.isEmpty(
            title
        )
    }

    /**
     * A method which validates a form in the update method.
     * @param notebookId an parent notebook id.
     * @param title a title of an entity.
     * @return a boolean result of a validation.
     */
    private fun validateForUpdate(notebookId: String, title: String): Boolean {
        return checkNotebookExistence(notebookId) && !TextUtils.isEmpty(title)
    }

    /**
     * A method which checks an existence of notebook in a database.
     * @param notebookId an id of a required notebook.
     * @return a boolean result of a validation.
     */
    private fun checkNotebookExistence(notebookId: String?): Boolean {
        mNotebookService.openConnection()
        val result = (notebookId == null) || mNotebookService.getById(notebookId).isPresent()
        mNotebookService.closeConnection()
        return result
    }
}