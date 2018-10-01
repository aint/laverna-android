package com.github.valhallalabs.laverna.persistent.entity

import android.os.Parcel
import android.os.Parcelable
import com.github.valhallalabs.laverna.persistent.entity.base.TrashDependedEntity

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
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

    private constructor(parcel: Parcel) : this(
            id = parcel.readString(),
            profileId = parcel.readString(),
            isTrash = parcel.readByte().toInt() != 0,
            notebookId = parcel.readString(),
            title = parcel.readString(),
            creationTime = parcel.readLong(),
            updateTime = parcel.readLong(),
            content = parcel.readString(),
            htmlContent = parcel.readString(),
            isFavorite = parcel.readByte().toInt() != 0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(profileId)
        parcel.writeString(notebookId)
        parcel.writeString(title)
        parcel.writeLong(creationTime)
        parcel.writeValue(updateTime)
        parcel.writeString(content)
        parcel.writeString(htmlContent)
        parcel.writeByte((if (isFavorite) 1 else 0).toByte())
        parcel.writeByte((if (isTrash) 1 else 0).toByte())
    }

    override fun describeContents() = 0

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Note> {
            override fun createFromParcel(parcel: Parcel) = Note(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Note>(size)
        }
    }

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
