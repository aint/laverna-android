package com.github.android.lvrn.lvrnproject.persistent.repository;

import androidx.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileDependedRepository<T extends ProfileDependedEntity> extends BasicRepository<T> {

    /**
     * A method which retrieves an amount of entities from required position by a profile id.
     * @param profileId an id of required profile.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    @NonNull
    List<T> getByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which updates certain fields of entity in a database.
     * @param entity an entity with a new data to update.
     * @return a boolean result of an update.
     */
    boolean update(@NonNull T entity);
}
