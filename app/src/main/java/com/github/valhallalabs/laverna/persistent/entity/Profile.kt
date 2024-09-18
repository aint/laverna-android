package com.github.valhallalabs.laverna.persistent.entity

import android.os.Parcelable
import com.github.valhallalabs.laverna.persistent.entity.base.Entity
import kotlinx.parcelize.Parcelize

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 * @author Bei Andrii <bei.andrii.dev@gmail.com?>
 */
@Parcelize
data class Profile(
    override var id: String,
    val name: String,
) : Entity(), Parcelable {

    override fun toString(): String {
        return "Profile{" + super.toString() +
                "name='" + name +
                '}'
    }

}
