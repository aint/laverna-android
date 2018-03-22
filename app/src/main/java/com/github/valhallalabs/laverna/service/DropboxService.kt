package com.github.valhallalabs.laverna.service

import com.dropbox.core.DbxException
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.Metadata
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.android.lvrn.lvrnproject.DropboxClientFactory
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
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
        private val profileService: ProfileService,
        private val objectMapper: ObjectMapper) {

    companion object {
        private const val DEFAULT_PROFILE = "default"
        private const val NOTES_PATH      = "/notes"
    }

    private var defaultProfileId = getDefaultProfileId()

    fun syncNotesWithDb() {
        downloadNotes()
                .map(this::convertToNoteForm)
                .onEach { Logger.w("NoteForm title = %s, is fav = %s", it.title, it.isFavorite) }
    }

    private fun getDefaultProfileId(): String {
        profileService.openConnection()
        val profiles = profileService.all
        profileService.closeConnection()

        if (profiles.isNotEmpty()) {
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

    private fun convertToNoteForm(noteJson: NoteJson): NoteForm {
        return NoteForm(
                defaultProfileId,
                noteJson.trash,
                noteJson.notebookId,
                noteJson.title!!,
                noteJson.content!!,
                MarkdownParserImpl().getParsedHtml(noteJson.content), // TODO use static util
                noteJson.isFavorite
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
