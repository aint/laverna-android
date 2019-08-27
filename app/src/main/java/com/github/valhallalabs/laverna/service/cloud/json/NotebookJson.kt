package com.github.valhallalabs.laverna.service.cloud.json

data class NotebookJson (
        override val id: String,
        override val type: EntityType,
        val parentId: String,
        val name: String,
        val count: Int,
        val trash: Boolean,
        val created: Long,
        val updated: Long
) : JsonEntity()
