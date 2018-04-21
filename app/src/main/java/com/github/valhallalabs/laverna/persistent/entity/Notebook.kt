package com.github.valhallalabs.laverna.persistent.entity

import android.os.Parcel
import android.os.Parcelable
import com.github.android.lvrn.lvrnproject.persistent.entity.TrashDependedEntity

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
data class Notebook(
        /**
         * An id of a notebook, which the notebook is belonged as a child. In case, if the note doesn't
         * belong to any parent notebook, then parentId equals to "0".
         */
        val parentId: String?,
        /**
         * Notebook's name.
         */
        val name: String,
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
) : TrashDependedEntity() {

    constructor(id: String,
                profileId: String,
                parentId: String?,
                name: String,
                creationTime: Long,
                updateTime: Long,
                count: Int,
                isTrash: Boolean) : this(parentId, name, creationTime, updateTime, count) {
        this.id = id
        this.profileId = profileId
        this.isTrash = isTrash
    }

    private constructor(parcel: Parcel) : this(
            parentId = parcel.readString(),
            name = parcel.readString(),
            creationTime = parcel.readLong(),
            updateTime = parcel.readLong(),
            count = parcel.readInt()
    ) {
        id = parcel.readString()
        profileId = parcel.readString()
        isTrash = parcel.readByte().toInt() != 0
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(profileId)
        dest.writeString(parentId)
        dest.writeString(name)
        dest.writeLong(creationTime)
        dest.writeLong(updateTime)
        dest.writeInt(count)
        dest.writeByte((if (isTrash) 1 else 0).toByte())
    }

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Notebook> {
            override fun createFromParcel(parcel: Parcel) = Notebook(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Notebook>(size)
        }
    }

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
