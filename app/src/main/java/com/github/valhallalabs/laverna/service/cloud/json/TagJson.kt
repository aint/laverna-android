package com.github.valhallalabs.laverna.service.cloud.json

data class TagJson (
        override val id: String,
        override val type: EntityType,
        val name: String,
        val count: String,
        val trash: Boolean,
        val created: Long,
        val updated: Long
) : JsonEntity()
