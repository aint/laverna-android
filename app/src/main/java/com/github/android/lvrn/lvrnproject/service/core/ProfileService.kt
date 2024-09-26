package com.github.android.lvrn.lvrnproject.service.core

import com.github.android.lvrn.lvrnproject.service.BasicService
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm
import com.github.valhallalabs.laverna.persistent.entity.Profile
import java.util.Optional

interface ProfileService : BasicService<Profile, ProfileForm> {

    /**
     * A method which retrieves all profiles from a database.
     * @return a list of profiles.
     */
    fun getAll(): List<Profile>

    fun getByName(name: String): Optional<Profile>
}