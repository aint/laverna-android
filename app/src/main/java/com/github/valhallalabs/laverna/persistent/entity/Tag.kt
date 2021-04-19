package com.github.valhallalabs.laverna.persistent.entity

import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity
import kotlinx.android.parcel.Parcelize

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>\
 * @author Bei Andrii <bei.andrii.dev@gmail.com?>
 */
@Parcelize
data class Tag(
        override val id: String,
        override val profileId: String,
        var name: String,
        /**
         * A date of the model's creation in milliseconds.
         */
        val creationTime: Long,
        /**
         * A date of the model's update in milliseconds.
         */
        val updateTime: Long,
        //TODO: unknown field. Find out what to do with it.
        val count: Int
) : ProfileDependedEntity() {

    override fun toString(): String {
        return "Tag{" + super.toString() +
                "name='" + name +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                ", count=" + count +
                "}"
    }

}
