package com.github.android.lvrn.lvrnproject.persistent.repository.core

import com.github.android.lvrn.lvrnproject.persistent.repository.BasicRepository
import com.github.valhallalabs.laverna.persistent.entity.Profile
import java.util.Optional

interface ProfileRepository : BasicRepository<Profile> {

    fun getAll(): MutableList<Profile>

    fun getByName(name: String): Optional<Profile>
}