package com.github.valhallalabs.laverna.service

import com.github.valhallalabs.laverna.service.cloud.json.NoteJson
import com.github.valhallalabs.laverna.service.cloud.json.NotebookJson
import com.github.valhallalabs.laverna.service.cloud.json.TagJson

interface CloudService {

    fun pullProfiles(): Set<String>
    fun pullNotebooks(profileName: String): List<NotebookJson>
    fun pullNotes(profileName: String): List<NoteJson>
    fun pullTags(profileName: String): List<TagJson>

    fun pushProfiles(profiles: Set<String>)
    fun pushNotebooks(profileName: String, notebooks: List<NotebookJson>)
    fun pushNotes(profileName: String, notes: List<NoteJson>)
    fun pushTags(profileName: String, tags: List<TagJson>)

}
