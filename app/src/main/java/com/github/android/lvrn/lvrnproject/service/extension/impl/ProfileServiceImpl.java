package com.github.android.lvrn.lvrnproject.service.extension.impl;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.ProfileRepository;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;
import com.github.android.lvrn.lvrnproject.service.impl.BasicServiceImpl;

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

    /**
     * @param name
     * @throws IllegalArgumentException
     */
    @Override
    public void create(@NonNull ProfileForm profileForm) {
        checkName(profileForm.getName());
        mProfileRepository.add(new Profile(UUID.randomUUID().toString(), profileForm.getName()));
    }

    @NonNull
    @Override
    public List<Profile> getAll() {
        return mProfileRepository.getAllProfiles();
    }

    /**
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    @Override
    public void update(@NonNull String id, @NonNull ProfileForm profileForm) {
        //TODO: change date of update.
        //TODO: Write what fields to update in database(not to update creation time)
//        checkName(entity.getName());
//        mProfileRepository.update(entity);
    }
}
