package com.github.valhallalabs.laverna.persistent.entity.base

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
abstract class Entity : Parcelable {
    abstract val id: String

    override fun toString(): String {
        return "Entity{" +
                "id='" + id +
                '}'
    }

}
