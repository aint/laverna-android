package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedServiceImpl<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm>
        extends BasicServiceImpl<T1, T2> implements ProfileDependedService<T1, T2> {

    private final ProfileDependedRepository<T1> mProfileDependedRepository;

    private final ProfileService mProfileService;

    public ProfileDependedServiceImpl(ProfileDependedRepository<T1> profileDependedRepository, ProfileService profileService) {
        super(profileDependedRepository);
        mProfileDependedRepository = profileDependedRepository;
        mProfileService = profileService;
    }

    @Override
    public List<T1> getByProfile(Profile profile, int from, int amount) {
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
