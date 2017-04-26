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

public abstract class ProfileDependedServiceImp<T extends ProfileDependedEntity>
        implements ProfileDependedService<T>{

    private ProfilesService profilesService;
    private ProfileDependedRepository profileDependedRepository;


    public ProfileDependedServiceImp(ProfileDependedRepository profileDependedRepository) {
        this.profileDependedRepository = profileDependedRepository;
        profilesService = new ProfilesServiceImpl();
    }

    public List<T> getByProfile(Profile profile, int from, int amount) {
        profileDependedRepository.getByProfile(profile, from, amount);
        return null;
    }

    public void checkProfileExistence(Profile profile) throws NullPointerException  {
        if (profile != null && profilesService.getById(profile.getId()).isPresent()) {
            return;
        }
        throw new NullPointerException();
    }
}
