package com.github.android.lvrn.lvrnproject.service.core.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileRepository;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;
import com.github.android.lvrn.lvrnproject.service.impl.BasicServiceImpl;
import com.google.common.base.Optional;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfileServiceImpl extends BasicServiceImpl<Profile, ProfileForm> implements ProfileService {

    private final ProfileRepository mProfileRepository;

    @Inject
    public ProfileServiceImpl(@NonNull ProfileRepository profileRepository) {
        super(profileRepository);
        mProfileRepository = profileRepository;
    }

    @Override
    public Optional<String> create(@NonNull ProfileForm profileForm) {
        String profileId = UUID.randomUUID().toString();
        if (!TextUtils.isEmpty(profileForm.getName())
                && mProfileRepository.add(new Profile(profileId, profileForm.getName()))) {
            return Optional.of(profileId);
        }
        return Optional.absent();
    }

    @NonNull
    @Override
    public List<Profile> getAll() {
        return mProfileRepository.getAll();
    }

    @Override
    public Optional<Profile> getByName(String name) {
        return mProfileRepository.getByName(name);
    }
}
