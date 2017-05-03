package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedServiceImpl<T extends ProfileDependedEntity>
        extends BasicServiceImpl<T> implements ProfileDependedService<T> {

    private final ProfileDependedRepository<T> mProfileDependedRepository;

    private final ProfileService mProfileService;

    public ProfileDependedServiceImpl(ProfileDependedRepository<T> profileDependedRepository, ProfileService profileService) {
        super(profileDependedRepository);
        mProfileDependedRepository = profileDependedRepository;
        mProfileService = profileService;
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
    protected void checkProfileExistence(String id) {
        if (id == null || !mProfileService.getById(id).isPresent()) {
            throw new IllegalArgumentException("Profile not found");
        }
    }
}
