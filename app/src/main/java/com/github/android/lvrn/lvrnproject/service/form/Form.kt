package com.github.android.lvrn.lvrnproject.service.form

import com.github.valhallalabs.laverna.persistent.entity.base.Entity

interface Form<T : Entity> {
    fun toEntity(id: String): T
}