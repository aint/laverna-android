package com.github.valhallalabs.laverna.persistent.entity

import android.os.Parcel
import android.os.Parcelable
import com.github.android.lvrn.lvrnproject.persistent.entity.Entity

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
data class Profile(val name: String) : Entity() {

    constructor(id: String, name: String) : this(name) {
        this.id = id
    }

    private constructor(parcel: Parcel) : this(name = parcel.readString()) {
        id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(this.id)
        parcel.writeString(this.name)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Profile> {
            override fun createFromParcel(parcel: Parcel) = Profile(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Profile>(size)
        }
    }

    override fun toString(): String {
        return "Profile{" + super.toString() +
                "name='" + name +
                '}'
    }

}
