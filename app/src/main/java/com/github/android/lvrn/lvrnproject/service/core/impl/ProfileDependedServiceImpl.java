package com.github.android.lvrn.lvrnproject.service.core.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileDependedRepository;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.impl.ProfilesServiceImpl;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedServiceImpl<T extends ProfileDependedEntity>
        extends BasicServiceImpl<T> implements ProfileDependedService<T> {

    @Override
    public List<T> getByProfile(Profile profile, int from, int amount) {
        ((ProfileDependedRepository) basicRepository).getByProfile(profile, from, amount);
        return null;
    }

    /**
     * A method which checks profile existence by an id.
     * @param id an id of profile.
     * @throws NullPointerException
     */
    protected void checkProfileExistence(String id) throws NullPointerException {
        ProfilesService profilesService = new ProfilesServiceImpl();
        if (id == null || !profilesService.getById(id).isPresent()) {
            throw new NullPointerException("Profile not found.");
        }
    }
}
