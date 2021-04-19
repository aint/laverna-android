package com.github.valhallalabs.laverna.persistent.entity

import com.github.valhallalabs.laverna.persistent.entity.base.Entity
import kotlinx.android.parcel.Parcelize

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 * @author Bei Andrii <bei.andrii.dev@gmail.com?>
 */
@Parcelize
data class Profile(
        override var id: String,
        val name: String
) : Entity() {

    override fun toString(): String {
        return "Profile{" + super.toString() +
                "name='" + name +
                '}'
    }

}
