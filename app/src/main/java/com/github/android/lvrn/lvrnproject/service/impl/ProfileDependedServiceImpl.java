package com.github.android.lvrn.lvrnproject.service.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.Form;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedServiceImpl<T1 extends ProfileDependedEntity, T2 extends Form>
        extends BasicServiceImpl<T1, T2> implements ProfileDependedService<T1, T2> {

    private final ProfileDependedRepository<T1> mProfileDependedRepository;

    private final ProfileService mProfileService;

    public ProfileDependedServiceImpl(@NonNull ProfileDependedRepository<T1> profileDependedRepository, @NonNull ProfileService profileService) {
        super(profileDependedRepository);
        mProfileDependedRepository = profileDependedRepository;
        mProfileService = profileService;
    }

    @NonNull
    @Override
    public List<T1> getByProfile(@NonNull String profileId, @Size(min = 1) int from, @Size(min = 2) int amount) {
        return mProfileDependedRepository.getByProfile(profileId, from, amount);
    }

    /**
     * A method which checks profile existence by an id.
     * @param id an id of profile.
     * @throws IllegalArgumentException in case if profile is not exist.
     */
    protected boolean checkProfileExistence(@Nullable String id) {
        mProfileService.openConnection();
        boolean result = !(id == null || !mProfileService.getById(id).isPresent());
        mProfileService.closeConnection();
        return result;
    }
}
