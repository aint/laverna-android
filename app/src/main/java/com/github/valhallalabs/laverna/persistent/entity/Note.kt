package com.github.valhallalabs.laverna.persistent.entity

import com.github.valhallalabs.laverna.persistent.entity.base.TrashDependedEntity
import kotlinx.android.parcel.Parcelize

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 * @author Bei Andrii <bei.andrii.dev@gmail.com?>
 */
@Parcelize
data class Note(
        override val id: String,
        override val profileId: String,
        override val isTrash: Boolean,
        /**
         * An id of a notebook, to which the note belongs. If the note doesn't belong to any
         * notebook, then notebookId equals "0".
         */
        var notebookId: String,
        /**
         * A note's title.
         */
        var title: String,
        /**
         * A date of the model's creation in milliseconds.
         */
        var creationTime: Long,
        /**
         * A date of the model's creation in milliseconds.
         */
        var updateTime: Long = 0,
        /**
         * A plain text of a note's content.
         */
        var content: String,
        /**
         * A note's content in HTML.
         */
        var htmlContent: String,
        /**
         * Marks weather this note is favorite or not.
         */
        var isFavorite: Boolean
) : TrashDependedEntity() {

    override fun toString(): String {
        return "Note{" + super.toString() +
                "notebookId='" + notebookId +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                ", content='" + content +
                ", isFavorite=" + isFavorite +
                "}"
    }

}
