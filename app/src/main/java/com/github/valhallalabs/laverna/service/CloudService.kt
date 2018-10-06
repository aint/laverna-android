package com.github.valhallalabs.laverna.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserImpl
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import com.github.valhallalabs.laverna.persistent.entity.Tag

interface CloudService {

    fun pullProfiles()
    fun pullNotebooks()
    fun pullNotes()
    fun pullTags()

    fun pushProfiles()
    fun pushNotebooks()
    fun pushNotes()
    fun pushTags()

    fun convertToNoteEntity(noteJson: NoteJson, profileId: String): Note {
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

    fun convertToNotebookEntity(notebookJson: NotebookJson, profileId: String): Notebook {
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

    fun convertToTagEntity(tagJson: TagJson, profileId: String): Tag {
        return Tag(
                tagJson.id,
                profileId,
                tagJson.name,
                tagJson.created,
                tagJson.updated,
                tagJson.count.toIntOrNull() ?: 0
        )
    }

    companion object {
        const val NOTES_PATH      = "/notes"
        const val NOTEBOOKS_PATH  = "/notebooks"
        const val TAGS_PATH       = "/tags"
        const val ROOT_PATH       = ""
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