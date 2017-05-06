package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileDependedRepository<T extends ProfileDependedEntity> extends BasicRepository<T> {

    /**
     * A method which retrieves an amount of entities from required position by a profile id.
     * @param profileId an id of required profile.
     * @param from a start position of selection.
     * @param amount a number of entities to select.
     * @return a list of entities.
     */
    @NonNull
    List<T> getByProfile(@NonNull String profileId, int from, int amount);

    /**
     * A method which updates certain fields of entity in a database.
     * @param entity an entity with a new data to update.
     */
    void update(@NonNull T entity);
}
