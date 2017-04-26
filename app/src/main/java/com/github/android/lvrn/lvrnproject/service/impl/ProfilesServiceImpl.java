package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.dagger.DaggerComponentsContainer;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfilesRepositoryImp;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.google.common.base.Optional;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfilesServiceImpl implements ProfilesService {

    @Inject ProfilesRepositoryImp profilesRepository;

    public ProfilesServiceImpl() {
        DaggerComponentsContainer.getRepositoryComponent().injectProfilesService(this);
    }

    @Override
    public void openConnection() {
        profilesRepository.openDatabaseConnection();
    }

    @Override
    public void closeConnection() {
        profilesRepository.closeDatabaseConnection();
    }

    @Override
    public void create(String name) {
        //TODO: find out the way to generate ids.
        profilesRepository.add(new Profile("id" + System.currentTimeMillis(), name));
    }

    @Override
    public void remove(Profile profile) {
        profilesRepository.remove(profile);
    }

    @Override
    public Optional<Profile> getById(String id) {
        return profilesRepository.getById(id);
    }

    @Override
    public List<Profile> getAll() {
        return profilesRepository.getAllProfiles();
    }
}
