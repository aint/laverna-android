package com.github.valhallalabs.laverna.persistent.entity.base

import android.os.Parcelable

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
