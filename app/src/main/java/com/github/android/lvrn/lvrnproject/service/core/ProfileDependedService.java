package com.github.android.lvrn.lvrnproject.service.core;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileDependedService<T extends ProfileDependedEntity> extends BasicService<T>  {

    /**
     * A method which returns an amount of entities from a received position by a profile.
     * @param profile
     * @param from a start position.
     * @param amount a number of entities.
     * @return a list of entites.
     */
    List<T> getByProfile(Profile profile, int from, int amount);
}
