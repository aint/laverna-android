package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileDependedRepository<T extends ProfileDependedEntity> extends BasicRepository<T> {

    List<T> getByProfile(Profile profile, int from, int amount);
}
