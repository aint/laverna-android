package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.BasicRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfilesRepository extends BasicRepository<Profile> {

    List<Profile> getAllProfiles();
}
