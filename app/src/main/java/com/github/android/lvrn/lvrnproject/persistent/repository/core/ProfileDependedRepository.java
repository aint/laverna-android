package com.github.android.lvrn.lvrnproject.persistent.repository.core;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileDependedRepository<T1 extends ProfileDependedEntity> extends BasicRepository<T1> {

    List<T1> getByProfile(Profile profile, int from, int amount);

}
