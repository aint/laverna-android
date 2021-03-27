package com.github.android.lvrn.lvrnproject.persistent.repository;

import androidx.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.persistent.entity.base.TrashDependedEntity;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TrashDependedRepository<T extends TrashDependedEntity> extends ProfileDependedRepository<T> {

    /**
     * A method which retrieves an amount of entities from required position by a profile id which
     * is not a trash.
     * @param profileId an id of required profile.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    @NonNull
    @Override
    List<T> getByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of entities from required position by a profile id which
     * is a trash.
     * @param profileId an id of required profile.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    @NonNull
    List<T> getTrashByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which changes entity's trash status on true.
     * @param entityId an id of entity to move to trash.
     * @return a boolean result of an operation.
     */
    boolean moveToTrash(@NonNull String entityId);

    /**
     * A method which changes entity's trash status on false.
     * @param entityId an id of entity to restore from trash.
     * @return a boolean result of an operation.
     */
    boolean restoreFromTrash(@NonNull String entityId);
}
