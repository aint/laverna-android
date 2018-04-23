package com.github.valhallalabs.laverna.persistent.entity.base

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
abstract class ProfileDependedEntity : Entity() {
    abstract val profileId: String

    override fun toString(): String {
        return "ProfileDependedEntity{" + super.toString() +
                "profileId=" + profileId +
                "}"
    }

}