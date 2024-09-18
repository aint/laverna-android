package com.github.valhallalabs.laverna.persistent.entity

import android.os.Parcelable
import com.github.valhallalabs.laverna.persistent.entity.base.TrashDependedEntity
import kotlinx.parcelize.Parcelize

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 * @author Bei Andrii <bei.andrii.dev@gmail.com?>
 */
@Parcelize
data class Notebook(
        override val id: String,
        override var profileId: String,
        override val isTrash: Boolean = false,
        /**
         * An id of a notebook, which the notebook is belonged as a child. In case, if the note doesn't
         * belong to any parent notebook, then parentId equals to "0".
         */
        var parentId: String?,
        /**
         * Notebook's name.
         */
        var name: String,
        /**
         * A date of the model's creation in milliseconds.
         */
        val creationTime: Long,
        /**
         * A date of the model's update in milliseconds.
         */
        val updateTime: Long,
        //TODO: unknown field. Find out what to do with it
        val count: Int = 0
) : TrashDependedEntity(), Parcelable {

    override fun toString(): String {
        return "Notebook{" + super.toString() +
                "parentId=" + parentId +
                ", name=" + name +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                ", count=" + count +
                "}"
    }

}
