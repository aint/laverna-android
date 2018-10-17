package com.github.valhallalabs.laverna.service.cloud.json

data class NotebookJson (
        override val id: String,
        override val type: EntityType,
        var parentId: String,
        var name: String,
        var count: Int,
        var trash: Boolean,
        var created: Long,
        var updated: Long
) : JsonEntity()
