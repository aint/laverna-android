package com.github.android.lvrn.lvrnproject.service.form

import com.github.valhallalabs.laverna.persistent.entity.Task

class TaskForm(
    public override val profileId: String, val noteId: String, val description: String,
    val isCompleted: Boolean,
) : ProfileDependedForm<Task>(profileId) {

    override fun toEntity(id: String): Task {
        return Task(id, profileId, noteId, description, isCompleted)
    }
}