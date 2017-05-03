package com.github.android.lvrn.lvrnproject.service.extension.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.ProfileRepository;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.impl.BasicServiceImpl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfileServiceImpl extends BasicServiceImpl<Profile> implements ProfileService {

    private final ProfileRepository mProfileRepository;

    @Inject
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        super(profileRepository);
        mProfileRepository = profileRepository;
    }

    /**
     * @param name
     * @throws IllegalArgumentException
     */
    @Override
    public void create(String name) {
        checkName(name);
        mProfileRepository.add(new Profile(UUID.randomUUID().toString(), name));
    }

    @Override
    public List<Profile> getAll() {
        return mProfileRepository.getAllProfiles();
    }

    /**
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    @Override
    public void update(Profile entity) {
        checkName(entity.getName());
        mProfileRepository.update(entity);
    }
}
