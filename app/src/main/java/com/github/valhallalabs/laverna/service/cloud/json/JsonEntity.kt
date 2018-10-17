package com.github.valhallalabs.laverna.service.cloud.json

import com.fasterxml.jackson.annotation.JsonValue

abstract class JsonEntity {
    abstract val id: String
    abstract val type: EntityType

    enum class EntityType(@JsonValue val value: String) {
        NOTE("notes"), NOTEBOOK("notebooks"), TAG("tags")
    }
}
