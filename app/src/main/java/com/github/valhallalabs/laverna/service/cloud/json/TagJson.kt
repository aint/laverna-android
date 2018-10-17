package com.github.valhallalabs.laverna.service.cloud.json

data class TagJson (
        override val id: String,
        override val type: EntityType,
        var name: String,
        var count: String,
        var trash: Boolean,
        var created: Long,
        var updated: Long
) : JsonEntity()
