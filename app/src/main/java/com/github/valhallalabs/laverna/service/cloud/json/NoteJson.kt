package com.github.valhallalabs.laverna.service.cloud.json

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class NoteJson (
        override val id: String,
        override val type: EntityType,
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
