package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.TrashDependedEntity;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TrashDependedRepository<T extends TrashDependedEntity> extends ProfileDependedRepository<T> {

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
