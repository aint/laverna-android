package com.github.android.lvrn.lvrnproject.service.core.impl

import android.text.TextUtils
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileRepository
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm
import com.github.android.lvrn.lvrnproject.service.impl.BasicServiceImpl
import com.github.valhallalabs.laverna.persistent.entity.Profile
import java.util.Optional
import java.util.UUID
import javax.inject.Inject

class ProfileServiceImpl @Inject constructor(val mProfileRepository : ProfileRepository) : BasicServiceImpl<Profile, ProfileForm>(mProfileRepository), ProfileService {

    override fun create(form: ProfileForm): Optional<String> {
        val profileId = UUID.randomUUID().toString()
        if (!TextUtils.isEmpty(form.name)
            && mProfileRepository.add(Profile(profileId, form.name))
        ) {
            return Optional.of(profileId)
        }
        return Optional.empty()
    }

    override fun getAll(): List<Profile> {
        return mProfileRepository.getAll()
    }

    override fun getByName(name: String): Optional<Profile> {
        return mProfileRepository.getByName(name)
    }
}