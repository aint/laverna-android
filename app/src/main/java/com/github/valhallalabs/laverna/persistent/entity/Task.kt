package com.github.valhallalabs.laverna.persistent.entity

import android.os.Parcel
import android.os.Parcelable
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
data class Task(
        override val id: String,
        override val profileId: String,
        /**
         * An id of the note, which the task is belonged.
         */
        val noteId: String,

        val description: String,

        val isCompleted: Boolean
) : ProfileDependedEntity() {

    private constructor(parcel: Parcel) : this(
            id = parcel.readString(),
            profileId = parcel.readString(),
            noteId = parcel.readString(),
            description = parcel.readString(),
            isCompleted = parcel.readByte().toInt() != 0
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.profileId)
        dest.writeString(this.id)
        dest.writeString(this.noteId)
        dest.writeString(this.description)
        dest.writeByte((if (this.isCompleted) 1 else 0).toByte())
    }

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Task> {
            override fun createFromParcel(parcel: Parcel) = Task(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Task>(size)
        }
    }

    override fun toString(): String {
        return "Task{" + super.toString() +
                "noteId='" + noteId +
                ", description='" + description +
                ", isCompleted=" + isCompleted +
                '}'
    }

}
