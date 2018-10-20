package com.github.valhallalabs.laverna.service

import com.dropbox.core.DbxException
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.Metadata
import com.dropbox.core.v2.files.WriteMode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.core.TagService
import com.github.valhallalabs.laverna.service.cloud.json.JsonEntity
import com.github.valhallalabs.laverna.service.cloud.json.NoteJson
import com.github.valhallalabs.laverna.service.cloud.json.NotebookJson
import com.github.valhallalabs.laverna.service.cloud.json.TagJson
import com.orhanobut.logger.Logger
import java.io.ByteArrayInputStream
import java.io.IOException

/**
 *
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
class DropboxService(
        private val noteService: NoteService,
        private val notebookService: NotebookService,
        private val tagService: TagService,
        private val profileService: ProfileService,
        private val objectMapper: ObjectMapper) : CloudService {

    companion object {
        const val NOTES_PATH      = "/notes"
        const val NOTEBOOKS_PATH  = "/notebooks"
        const val TAGS_PATH       = "/tags"
        const val ROOT_PATH       = ""
    }

    override fun pullProfiles(): Set<String> {
        return DropboxClientFactory.getClient()
                .files()
                .listFolder(ROOT_PATH)
                .entries
                .map { it.name }
                .toSet()
    }

    override fun pullNotebooks(profileName: String) = downloadEntities<NotebookJson>("/$profileName$NOTEBOOKS_PATH")

    override fun pullNotes(profileName: String) = downloadEntities<NoteJson>("/$profileName$NOTES_PATH")

    override fun pullTags(profileName: String) = downloadEntities<TagJson>("/$profileName$TAGS_PATH")


    override fun pushProfiles(profiles: Set<String>) {
        profiles.forEach { DropboxClientFactory.getClient().files().createFolderV2("/$it") }
    }

    override fun pushNotebooks(profileName: String, notebooks: List<NotebookJson>) = notebooks.forEach { uploadEntity(it, profileName + NOTEBOOKS_PATH) }

    override fun pushNotes(profileName: String, notes: List<NoteJson>) = notes.forEach { uploadEntity(it, profileName + NOTES_PATH) }

    override fun pushTags(profileName: String, tags: List<TagJson>) = tags.forEach { uploadEntity(it, profileName + TAGS_PATH) }

    @Throws(IOException::class)
    private fun uploadEntity(entity: JsonEntity, basePath: String) {
        val bytes = objectMapper.writeValueAsBytes(entity)

//      val progressListener = { l -> printProgress(l, localFile.length()) }
        ByteArrayInputStream(bytes).use {
            DropboxClientFactory.getClient().files()
                    .uploadBuilder("/$basePath/${entity.id}.json")
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(it)
        }
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
                .map{ CloudConverter.notebookJsonToEntity(it, profileId) }
                .filterNot { notebookService.getById(it.id).isPresent } // todo use exists and merge strategy
                .onEach { Logger.i("Importing Notebook name = %s, id = %s", it.name, it.id) }
                .forEach { notebookService.save(it) }
        notebookService.closeConnection()
    }

    fun importNotes(profileId: String, profileName: String) {
        noteService.openConnection()
        downloadEntities<NoteJson>("/$profileName$NOTES_PATH")
                .map { CloudConverter.noteJsonToEntity(it, profileId) }
                .filterNot { noteService.getById(it.id).isPresent } // todo use exists and merge strategy
                .onEach { Logger.i("Importing Note title = %s, id = %s", it.title, it.id) }
                .forEach { noteService.save(it) }
        noteService.closeConnection()
    }

    fun importTags(profileId: String, profileName: String) {
        tagService.openConnection()
        downloadEntities<TagJson>("/$profileName$TAGS_PATH")
                .map { CloudConverter.tagJsonToEntity(it, profileId) }
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

}
