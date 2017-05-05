package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileDependedRepository<T extends ProfileDependedEntity> extends BasicRepository<T> {

    @NonNull
    List<T> getByProfile(String profileId, int from, int amount);

    void update(@NonNull T entity);
}
