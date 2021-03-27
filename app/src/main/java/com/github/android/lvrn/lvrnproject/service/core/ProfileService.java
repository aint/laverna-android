package com.github.android.lvrn.lvrnproject.service.core;

import androidx.annotation.NonNull;

import com.github.valhallalabs.laverna.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.service.BasicService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;
import com.google.common.base.Optional;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileService extends BasicService<Profile, ProfileForm> {

    /**
     * A method which retrieves all profiles from a database.
     * @return a list of profiles.
     */
    @NonNull
    List<Profile> getAll();

    Optional<Profile> getByName(String name);
}
