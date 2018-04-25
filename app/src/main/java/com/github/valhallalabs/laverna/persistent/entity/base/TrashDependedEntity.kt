package com.github.valhallalabs.laverna.persistent.entity.base

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
abstract class TrashDependedEntity : ProfileDependedEntity() {
    /**
     * A field which shows trash status of an entity.
     */
    abstract val isTrash: Boolean

    override fun toString(): String {
        return "TrashDependedEntity{" + super.toString() +
                "isTrash=" + isTrash +
                "}"
    }
}