package com.github.android.lvrn.lvrnproject.service.extension.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.ProfileRepository;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;
import com.github.android.lvrn.lvrnproject.service.impl.BasicServiceImpl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfileServiceImpl extends BasicServiceImpl<Profile, ProfileForm> implements ProfileService {

    private final ProfileRepository mProfileRepository;

    @Inject
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        super(profileRepository);
        mProfileRepository = profileRepository;
    }

    /**
     * @param name
     * @throws IllegalArgumentException
     */
    @Override
    public void create(ProfileForm profileForm) {
        checkName(profileForm.getName());
        mProfileRepository.add(new Profile(UUID.randomUUID().toString(), profileForm.getName()));
    }

    @Override
    public List<Profile> getAll() {
        return mProfileRepository.getAllProfiles();
    }

    /**
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    @Override
    public void update(String id, ProfileForm profileForm) {
        //TODO: change date of update.
        //TODO: Write what fields to update in database(not to update creation time)
//        checkName(entity.getName());
//        mProfileRepository.update(entity);
    }
}
