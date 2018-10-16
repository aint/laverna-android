package com.github.valhallalabs.laverna.service.cloud

import com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserImpl
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import com.github.valhallalabs.laverna.persistent.entity.Tag
import com.github.valhallalabs.laverna.service.CloudService

/**
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
object CloudConverter {
    // TODO use MapStruct, ModelMapper, Selma or something

    fun noteJsonToEntity(noteJson: CloudService.NoteJson, profileId: String): Note {
        return Note(
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

    fun noteEntityToJson(note: Note): CloudService.NoteJson {
        return CloudService.NoteJson(
                "notes", //TODO extract this to const
                note.id,
                note.title,
                note.content,
                null,
                null,
                note.creationTime,
                note.updateTime,
                note.notebookId,
                note.isFavorite,
                note.isTrash,
                null,
                null
        )
    }

    fun notebookJsonToEntity(notebookJson: CloudService.NotebookJson, profileId: String): Notebook {
        return Notebook(
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

    fun notebookEntityToJson(notebook: Notebook): CloudService.NotebookJson {
        return CloudService.NotebookJson(
                notebook.id,
                "notebooks", //TODO extract this to const
                notebook.parentId ?: "0",
                notebook.name,
                notebook.count,
                notebook.isTrash,
                notebook.creationTime,
                notebook.updateTime
        )
    }

    fun tagJsonToEntity(tagJson: CloudService.TagJson, profileId: String): Tag {
        return Tag(
                tagJson.id,
                profileId,
                tagJson.name,
                tagJson.created,
                tagJson.updated,
                tagJson.count.toIntOrNull() ?: 0
        )
    }

    fun tagEntityToJson(tag: Tag): CloudService.TagJson {
        return CloudService.TagJson(
                tag.id,
                "tags", //TODO extract this to const
                tag.name,
                tag.count.toString(),
                false, // TODO add this field to entity
                tag.creationTime,
                tag.updateTime
        )
    }


}
