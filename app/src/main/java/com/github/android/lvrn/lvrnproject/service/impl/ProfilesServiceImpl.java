package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfilesRepository;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.core.impl.BasicServiceImpl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfilesServiceImpl extends BasicServiceImpl<Profile> implements ProfilesService {

    private final ProfilesRepository mProfilesRepository;

    @Inject
    public ProfilesServiceImpl(ProfilesRepository profilesRepository) {
        super(profilesRepository);
        mProfilesRepository = profilesRepository;
    }

    /**
     * @param name
     * @throws IllegalArgumentException
     */
    @Override
    public void create(String name) {
        checkName(name);
        mProfilesRepository.add(new Profile(UUID.randomUUID().toString(), name));
    }

    @Override
    public List<Profile> getAll() {
        return mProfilesRepository.getAllProfiles();
    }

    /**
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    @Override
    public void update(Profile entity) {
        checkName(entity.getName());
        mProfilesRepository.update(entity);
    }
}
