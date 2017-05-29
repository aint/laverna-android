package com.github.android.lvrn.lvrnproject.service;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.TrashDependedEntity;
import com.github.android.lvrn.lvrnproject.service.form.TrashDependedForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TrashDependedService <T1 extends TrashDependedEntity, T2 extends TrashDependedForm> extends ProfileDependedService<T1, T2> {

    @NonNull
    List<T1> getByProfile(@NonNull String profileId, boolean isTrash, @NonNull PaginationArgs paginationArgs);

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
