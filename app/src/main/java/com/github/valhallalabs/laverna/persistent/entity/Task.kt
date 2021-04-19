package com.github.valhallalabs.laverna.persistent.entity

import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity
import kotlinx.android.parcel.Parcelize

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 * @author Bei Andrii <bei.andrii.dev@gmail.com?>
 */
@Parcelize
data class Task(
        override val id: String,
        override val profileId: String,
        /**
         * An id of the note, which the task is belonged.
         */
        val noteId: String,

        var description: String,

        val isCompleted: Boolean
) : ProfileDependedEntity() {

    override fun toString(): String {
        return "Task{" + super.toString() +
                "noteId='" + noteId +
                ", description='" + description +
                ", isCompleted=" + isCompleted +
                '}'
    }

}
