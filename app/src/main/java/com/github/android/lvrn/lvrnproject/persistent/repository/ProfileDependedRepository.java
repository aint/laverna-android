package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileDependedRepository<T extends ProfileDependedEntity> extends BasicRepository<T> {

    /**
     * A method which retrieves an amount of entities from required position by a profile id.
     * @param profileId an id of required profile.
     * @param offset a start position of selection.
     * @param limit a number of entities to select.
     * @return a list of entities.
     */
    @NonNull
    List<T> getByProfile(@NonNull String profileId, @Size(min = 0) int offset, @Size(min = 1) int limit);

    /**
     * A method which updates certain fields of entity in a database.
     * @param entity an entity with a new data to update.
     * @return a boolean result of an update.
     */
    boolean update(@NonNull T entity);
}
