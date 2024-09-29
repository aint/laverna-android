package com.github.android.lvrn.lvrnproject.service.form

import com.github.valhallalabs.laverna.persistent.entity.Profile

class ProfileForm(val name: String) : Form<Profile> {
    override fun toEntity(id: String): Profile {
        return Profile(id, name)
    }
}