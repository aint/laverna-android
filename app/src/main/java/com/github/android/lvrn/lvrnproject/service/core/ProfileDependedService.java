package com.github.android.lvrn.lvrnproject.service.core;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileDependedService<T extends ProfileDependedEntity> extends BasicService<T>  {

    List<T> getByProfile(Profile profile, int from, int amount);

    void checkProfileExistence(Profile profile) throws NullPointerException;
}
