package com.github.android.lvrn.lvrnproject.service;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.service.form.Form;
import com.google.common.base.Optional;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface BasicService<T1 extends Entity, T2 extends Form> {

    /**
     * A method which creates an entity by a received form.
     * @param form a form with a data.
     */
    void create(@NonNull T2 form);

    /**
     * A method which opens a connection to a repository.
     */
    void openConnection();

    /**
     * A method which closes a connection to a repository.
     */
    void closeConnection();

    /**
     * A method which removes an entity by an id.
     * @param id an id of an entity.
     */
    void remove(@NonNull String id);

    /**
     * A method which returns an entity by an id.
     * @param id an id of a required entity
     * @return a required entity.
     */
    @NonNull
    Optional<T1> getById(@NonNull String id);
}
