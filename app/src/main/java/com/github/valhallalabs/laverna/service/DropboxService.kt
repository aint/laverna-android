package com.github.valhallalabs.laverna.service

import com.dropbox.core.DbxException
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.Metadata
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.android.lvrn.lvrnproject.persistent.entity.Note
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserImpl
import com.orhanobut.logger.Logger

import java.io.IOException
import java.lang.Exception

/**
 *
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
class DropboxService(
        private val noteService: NoteService,
        private val notebookService: NotebookService,
        private val profileService: ProfileService,
        private val objectMapper: ObjectMapper) {

    companion object {
        private const val DEFAULT_PROFILE = "default"
        private const val NOTES_PATH      = "/notes"
        private const val NOTEBOOKS_PATH  = "/notebooks"
        private const val ROOT_PATH  = ""
    }

    fun importProfiles() {
        profileService.openConnection()
        DropboxClientFactory.getClient()
                .files()
                .listFolder(ROOT_PATH)
                .entries
                .onEach { Logger.w("Profile name = %s", it.name) }
                .filterNot { profileService.getByName(it.name).isPresent }
                .onEach { Logger.w("Profile %s will be saved", it.name) }
                .forEach { profileService.create(ProfileForm(it.name)) }
        profileService.closeConnection()
    }

    fun importNotebooks(profileId: String, profileName: String) {
        notebookService.openConnection()
        downloadEntities<NotebookJson>("/$profileName$NOTEBOOKS_PATH")
                .map{ convertToNotebookEntity(it, profileId) }
                .onEach { Logger.w("NotebookEntity title = %s, id = %s", it.name, it.id) }
                .filterNot { notebookService.getById(it.id).isPresent } // todo use exists and merge strategy
                .onEach { Logger.w("NotebookEntity will be saved \n %s", it.toString()) }
                .forEach { notebookService.save(it) }
        notebookService.closeConnection()
    }

//    private var defaultProfileId = getDefaultProfileId()

    fun importNotes(profileId: String, profileName: String) {
        noteService.openConnection()
        downloadEntities<NoteJson>("/$profileName$NOTES_PATH")
                .map { convertToNoteEntity(it, profileId) }
                .onEach { Logger.w("NoteEntity title = %s, id = %s", it.title, it.id) }
                .filterNot { noteService.getById(it.id).isPresent } // todo use exists and merge strategy
                .onEach { Logger.w("NoteEntity will be saved \n %s", it.toString()) }
                .forEach { noteService.save(it) }
        noteService.closeConnection()
    }

    private fun getDefaultProfileId(): String {
        profileService.openConnection()
        val profiles = profileService.all
        profileService.closeConnection()

        if (profiles.isNotEmpty()) {
            Logger.w("Profile id = %s", profiles[0].id)
            return profiles[0].id
        }

        return profileService.create(ProfileForm(DEFAULT_PROFILE)).orNull()!!
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
                noteJson.notebookId!!,
                noteJson.title!!,
                noteJson.created,
                noteJson.updated!!,
                noteJson.content!!,
                MarkdownParserImpl().getParsedHtml(noteJson.content), // TODO use static util
                noteJson.isFavorite,
                noteJson.trash
        )
    }

    private fun convertToNotebookEntity(notebookJson: NotebookJson, profileId: String): Notebook {
        return Notebook(                                                // TODO use object mapper
                notebookJson.id,
                profileId,
                if (notebookJson.parentId == "0") null else notebookJson.parentId,
                notebookJson.name,
                notebookJson.created!!,
                notebookJson.updated,
                notebookJson.count,
                notebookJson.trash
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
            val title: String?,
            val content: String?,
            val taskAll: Int?,
            val taskCompleted: Int?,
            val created: Long,
            val updated: Long?,
            val notebookId: String?,
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
            var created: Long?,
            var updated: Long
    ) : JsonEntity()

}
