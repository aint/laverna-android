package com.github.valhallalabs.laverna.service

import com.dropbox.core.DbxException
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.Metadata
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.android.lvrn.lvrnproject.DropboxClientFactory
import com.github.android.lvrn.lvrnproject.persistent.entity.Note
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm
import com.github.android.lvrn.lvrnproject.util.CurrentState.profileId
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
        private val profileService: ProfileService,
        private val objectMapper: ObjectMapper) {

    companion object {
        private const val DEFAULT_PROFILE = "default"
        private const val NOTES_PATH      = "/notes"
    }

    private var defaultProfileId = getDefaultProfileId()

    fun importNotes() {
        noteService.openConnection()
        downloadNotes()
                .map(this::convertToNoteEntity)
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

    private fun downloadNotes(): List<NoteJson> {
        return DropboxClientFactory.getClient()
                .files()
                .listFolder(NOTES_PATH)
                .entries
                .onEach { Logger.i("Note metadata file: %s", it.name) }
                .mapNotNull(this::parseNotes)
    }

    @Throws(DbxException::class, IOException::class)
    private fun parseNotes(metadata: Metadata): NoteJson? {
        try {
            val inputStream = DropboxClientFactory.getClient()
                    .files()
                    .download(metadata.pathLower, (metadata as FileMetadata).rev)
                    .inputStream

            inputStream.use {
                return objectMapper.readValue(it, NoteJson::class.java)
            }
        } catch (e: Exception) {
            Logger.w("Error while parsing note", e)
        }
        return null
    }

    private fun convertToNoteEntity(noteJson: NoteJson): Note {
        return Note(                                                // TODO use object mapper
                noteJson.id,
                profileId,
//                noteJson.notebookId!!,
                "5baafa9e-c055-4c6c-895c-93ba7c105919",  // TODO jus a stub
                noteJson.title!!,
                noteJson.created,
                noteJson.updated!!,
                noteJson.content!!,
                MarkdownParserImpl().getParsedHtml(noteJson.content), // TODO use static util
                noteJson.isFavorite,
                noteJson.trash
        )
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    data class NoteJson (
        val type: String, // enum
        val id: String,
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
    )

}
