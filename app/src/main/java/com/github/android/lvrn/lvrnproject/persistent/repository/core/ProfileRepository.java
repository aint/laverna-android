package com.github.android.lvrn.lvrnproject.persistent.repository.core;

import android.support.annotation.NonNull;

import com.github.valhallalabs.laverna.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.BasicRepository;
import com.google.common.base.Optional;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileRepository extends BasicRepository<Profile> {

    /**
     * A method which retrieves all profiles from a database.
     * @return a list of profiles.
     */
    @NonNull
    List<Profile> getAll();

    Optional<Profile> getByName(String name);
}
