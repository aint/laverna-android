package com.github.valhallalabs.laverna.service

import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.core.TagService
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.service.cloud.CloudConverter
import com.orhanobut.logger.Logger

class SyncService(
    private val cloudService: CloudService,
    private val profileService: ProfileService,
    private val notebookService: NotebookService,
    private val noteService: NoteService,
    private val tagService: TagService,
) {

    fun pullData() {
        Logger.w("IMPORT PROFILES")
        cloudService.pullProfiles().forEach { profileName ->

            profileService.openConnection()
            val profileId = profileService.getByName(profileName)
                .map { p -> p!!.id }
                ?.orElseGet { profileService.create(ProfileForm(profileName)).get() }

            Logger.w("PROFILE name = %s, id = %s", profileName, profileId)
            profileService.closeConnection()

            if (profileId == null) {
                throw IllegalStateException()
            }


            notebookService.openConnection()
            cloudService.pullNotebooks(profileName).forEach { notebookJson ->
                Logger.i(
                    "Importing Notebook name = %s, id = %s",
                    notebookJson.name,
                    notebookJson.id
                )

                if (!notebookService.getById(notebookJson.id).isPresent) {
                    //todo convert to form and then use notebookService.create(...) method
                    val notebookEntity =
                        CloudConverter.notebookJsonToEntity(notebookJson, profileId)
                    notebookService.save(notebookEntity)
                }
            }
            notebookService.closeConnection()


            noteService.openConnection()
            cloudService.pullNotes(profileName).forEach { noteJson ->
                Logger.i("Importing Note title = %s, id = %s", noteJson.title, noteJson.id)

                if (!noteService.getById(noteJson.id).isPresent) {
                    //todo convert to form and then use noteService.create(...) method
                    val noteEntity = CloudConverter.noteJsonToEntity(noteJson, profileId)
                    noteService.save(noteEntity)
                } else {
                    Logger.i("Note title = %s is already exists", noteJson.title)
                }
            }
            noteService.closeConnection()


            tagService.openConnection()
            cloudService.pullTags(profileName).forEach { tagJson ->
                Logger.i("Importing Tag name = %s, id = %s", tagJson.name, tagJson.id)

                if (!tagService.getById(tagJson.id).isPresent) {
                    //todo convert to form and then use tagService.create(...) method
                    val tagEntity = CloudConverter.tagJsonToEntity(tagJson, profileId)
                    tagService.save(tagEntity)
                }
            }
            tagService.closeConnection()
        }
    }

    fun pushData() {
        profileService.openConnection()
        profileService.getAll().forEach { profile ->
            val profileId = profile.id
            val profileName = profile.name

            //TODO implement pagination
            val offset = 0
            val limit = 100_000
            val pagination = PaginationArgs(offset, limit)

            notebookService.openConnection()
            val notebooks = notebookService.getByProfile(profileId, pagination)
                .map { CloudConverter.notebookEntityToJson(it) }
            notebookService.closeConnection()
            cloudService.pushNotebooks(profileName, notebooks)

            noteService.openConnection()
            val notes = noteService.getByProfile(profileId, pagination)
                .map { CloudConverter.noteEntityToJson(it) }
            noteService.closeConnection()
            cloudService.pushNotes(profileName, notes)

            tagService.openConnection()
            val tags = tagService.getByProfile(profileId, pagination)
                .map { CloudConverter.tagEntityToJson(it) }
            tagService.closeConnection()
            cloudService.pushTags(profileName, tags)
        }
        profileService.closeConnection()
    }

}
