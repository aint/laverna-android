package com.github.android.lvrn.lvrnproject.service.form

import com.github.valhallalabs.laverna.persistent.entity.Tag

class TagForm(
    public override val profileId: String, val name: String,
) : ProfileDependedForm<Tag>(profileId) {

    override fun toEntity(id: String): Tag {
        return Tag(id, profileId, name, System.currentTimeMillis(), System.currentTimeMillis(), 0)
    }
}