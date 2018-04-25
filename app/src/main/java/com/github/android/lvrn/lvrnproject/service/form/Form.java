package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

import com.github.valhallalabs.laverna.persistent.entity.base.Entity;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface Form<T extends Entity> {

    /**
     * Converts the form to an entity.
     * @return an converted entity.
     */
    @NonNull
    T toEntity(@NonNull String id);
}
