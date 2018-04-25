package com.github.valhallalabs.laverna.service

import com.dropbox.core.DbxException
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.Metadata
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserImpl
import com.orhanobut.logger.Logger

import java.io.IOException
import java.lang.Exception
import com.github.valhallalabs.laverna.persistent.entity.Tag
import com.github.android.lvrn.lvrnproject.service.core.TagService
import com.github.valhallalabs.laverna.persistent.entity.Note

/**
 *
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
class DropboxService(
        private val noteService: NoteService,
        private val notebookService: NotebookService,
        private val tagService: TagService,
        private val profileService: ProfileService,
        private val objectMapper: ObjectMapper) {

    companion object {
        private const val NOTES_PATH      = "/notes"
        private const val NOTEBOOKS_PATH  = "/notebooks"
        private const val TAGS_PATH       = "/tags"
        private const val ROOT_PATH       = ""
    }

    fun importProfiles() {
        profileService.openConnection()
        DropboxClientFactory.getClient()
                .files()
                .listFolder(ROOT_PATH)
                .entries
                .filterNot { profileService.getByName(it.name).isPresent }
                .onEach { Logger.i("Importing Profile name = %s", it.name) }
                .forEach { profileService.create(ProfileForm(it.name)) }
        profileService.closeConnection()
    }

    fun importNotebooks(profileId: String, profileName: String) {
        notebookService.openConnection()
        downloadEntities<NotebookJson>("/$profileName$NOTEBOOKS_PATH")
                .map{ convertToNotebookEntity(it, profileId) }
                .filterNot { notebookService.getById(it.id).isPresent } // todo use exists and merge strategy
                .onEach { Logger.i("Importing Notebook name = %s, id = %s", it.name, it.id) }
                .forEach { notebookService.save(it) }
        notebookService.closeConnection()
    }

    fun importNotes(profileId: String, profileName: String) {
        noteService.openConnection()
        downloadEntities<NoteJson>("/$profileName$NOTES_PATH")
                .map { convertToNoteEntity(it, profileId) }
                .filterNot { noteService.getById(it.id).isPresent } // todo use exists and merge strategy
                .onEach { Logger.i("Importing Note title = %s, id = %s", it.title, it.id) }
                .forEach { noteService.save(it) }
        noteService.closeConnection()
    }

    fun importTags(profileId: String, profileName: String) {
        tagService.openConnection()
        downloadEntities<TagJson>("/$profileName$TAGS_PATH")
                .map { convertToTagEntity(it, profileId) }
                .filterNot { tagService.getById(it.id).isPresent } // todo use exists and merge strategy
                .onEach { Logger.i("Importing Tag name = %s, id = %s", it.name, it.id) }
                .forEach { tagService.save(it) }
        tagService.closeConnection()
    }

    private inline fun <reified T : JsonEntity> downloadEntities(path: String): List<T> {
        return DropboxClientFactory.getClient()
                .files()
                .listFolder(path)
                .entries
                .onEach { Logger.i( "%s metadata file: %s", T::class, it.name) }
                .mapNotNull{ parseEntity<T>(it) }
    }

    @Throws(DbxException::class, IOException::class)
    private inline fun <reified T : JsonEntity> parseEntity(metadata: Metadata): T? {
        try {
            val inputStream = DropboxClientFactory.getClient()
                    .files()
                    .download(metadata.pathLower, (metadata as FileMetadata).rev)
                    .inputStream

            inputStream.use {
                return objectMapper.readValue(it, T::class.java)
            }
        } catch (e: Exception) {
            Logger.w("Error while parsing " + T::class, e)
        }
        return null
    }

    private fun convertToNoteEntity(noteJson: NoteJson, profileId: String): Note {
        return Note(                                                // TODO use object mapper
                noteJson.id,
                profileId,
                noteJson.trash,
                noteJson.notebookId,
                noteJson.title,
                noteJson.created,
                noteJson.updated,
                noteJson.content!!,
                MarkdownParserImpl().getParsedHtml(noteJson.content), // TODO use static util
                noteJson.isFavorite
        )
    }

    private fun convertToNotebookEntity(notebookJson: NotebookJson, profileId: String): Notebook {
        return Notebook(                                                // TODO use object mapper
                notebookJson.id,
                profileId,
                notebookJson.trash,
                if (notebookJson.parentId == "0") null else notebookJson.parentId,
                notebookJson.name,
                notebookJson.created,
                notebookJson.updated,
                notebookJson.count
        )
    }

    private fun convertToTagEntity(tagJson: TagJson, profileId: String): Tag {
        return Tag(
                tagJson.id,
                profileId,
                tagJson.name,
                tagJson.created,
                tagJson.updated,
                tagJson.count.toIntOrNull() ?: 0
        )
    }


    abstract class JsonEntity {
        abstract val id: String
        abstract val type: String
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class NoteJson (
            override val type: String, // enum
            override val id: String,
            val title: String,
            val content: String?,
            val taskAll: Int?,
            val taskCompleted: Int?,
            val created: Long,
            val updated: Long,
            val notebookId: String,
            val isFavorite: Boolean,
            val trash: Boolean,
            val tags: List<Any>?,
            val files: List<Any>?
    ) : JsonEntity()

    data class NotebookJson (
            override val id: String,
            override val type: String,
            var parentId: String,
            var name: String,
            var count: Int,
            var trash: Boolean,
            var created: Long,
            var updated: Long
    ) : JsonEntity()

    data class TagJson (
            override val id: String,
            override val type: String,
            var name: String,
            var count: String,
            var trash: Boolean,
            var created: Long,
            var updated: Long
    ) : JsonEntity()

}
