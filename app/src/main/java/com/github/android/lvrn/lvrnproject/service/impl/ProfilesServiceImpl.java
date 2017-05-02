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

    @Override
    public void create(String name) throws IllegalArgumentException {
        checkName(name);
        mProfilesRepository.add(new Profile(UUID.randomUUID().toString(), name));
    }

    @Override
    public List<Profile> getAll() {
        return mProfilesRepository.getAllProfiles();
    }

    @Override
    public void update(Profile entity) throws IllegalArgumentException {
        checkName(entity.getName());
        mProfilesRepository.update(entity);
    }
}
