package com.github.android.lvrn.lvrnproject.service.core.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileDependedRepository;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileDependedService;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedServiceImpl<T extends ProfileDependedEntity>
        extends BasicServiceImpl<T> implements ProfileDependedService<T> {

    private final ProfileDependedRepository<T> mProfileDependedRepository;

    private final ProfilesService mProfilesService;

    public ProfileDependedServiceImpl(ProfileDependedRepository<T> profileDependedRepository, ProfilesService profilesService) {
        super(profileDependedRepository);
        mProfileDependedRepository = profileDependedRepository;
        mProfilesService = profilesService;
    }

    @Override
    public List<T> getByProfile(Profile profile, int from, int amount) {
        mProfileDependedRepository.getByProfile(profile, from, amount);
        return null;
    }

    /**
     * A method which checks profile existence by an id.
     * @param id an id of profile.
     * @throws IllegalArgumentException
     */
    protected void checkProfileExistence(String id) throws IllegalArgumentException {
        if (id == null || !mProfilesService.getById(id).isPresent()) {
            throw new IllegalArgumentException("Profile not found");
        }
    }
}
