package com.github.android.lvrn.lvrnproject.service.form

import com.github.valhallalabs.laverna.persistent.entity.base.TrashDependedEntity

abstract class TrashDependedForm<T : TrashDependedEntity>
    (profileId: String, protected open val isTrash: Boolean) : ProfileDependedForm<T>(profileId) {
}