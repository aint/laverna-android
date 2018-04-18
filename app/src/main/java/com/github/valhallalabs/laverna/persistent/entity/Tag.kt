package com.github.valhallalabs.laverna.persistent.entity

import android.os.Parcel
import android.os.Parcelable
import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
data class Tag(
        val name: String,
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

    constructor(id: String,
                profileId: String,
                name: String,
                creationTime: Long,
                updateTime: Long,
                count: Int) : this(name, creationTime, updateTime, count) {
        this.id = id
        this.profileId = profileId
    }

    private constructor(parcel: Parcel) : this (
            name = parcel.readString(),
            creationTime = parcel.readLong(),
            updateTime = parcel.readLong(),
            count = parcel.readInt()
    ) {
        this.id = parcel.readString()
        this.profileId = parcel.readString()

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.id)
        dest.writeString(this.profileId)
        dest.writeString(this.name)
        dest.writeLong(this.creationTime)
        dest.writeLong(this.updateTime)
        dest.writeInt(this.count)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Tag> {
            override fun createFromParcel(parcel: Parcel) = Tag(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Tag>(size)
        }
    }

    override fun toString(): String {
        return "Tag{" + super.toString() +
                "name='" + name +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                ", count=" + count +
                "}"
    }

}
