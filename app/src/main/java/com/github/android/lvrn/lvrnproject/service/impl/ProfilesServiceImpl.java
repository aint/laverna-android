package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.dagger.DaggerComponentsContainer;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfilesRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.core.impl.BasicServiceImpl;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfilesServiceImpl extends BasicServiceImpl<Profile> implements ProfilesService {

    @Inject
    ProfilesRepositoryImpl profilesRepository;

    public ProfilesServiceImpl() {
        DaggerComponentsContainer.getRepositoryComponent().injectProfilesService(this);
    }

    @Override
    public void create(String name) {
        checkName(name);
        //TODO: find out the way to generate ids.
        profilesRepository.add(new Profile("id" + System.currentTimeMillis(), name));
    }

    @Override
    public List<Profile> getAll() {
        return profilesRepository.getAllProfiles();
    }

    @Override
    public void update(Profile entity) {
        checkName(entity.getName());
        profilesRepository.update(entity);
    }
}
