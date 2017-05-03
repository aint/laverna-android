package com.github.android.lvrn.lvrnproject.service;

import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.service.form.Form;
import com.google.common.base.Optional;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface BasicService<T1 extends Entity, T2 extends Form> {

    /**
     * A method which create am entity by received form
     * @param form
     */
    void create(T2 form);

    /**
     * A method which opens a connection to a repository.
     */
    void openConnection();

    /**
     * A method which closes a connection to a repository.
     */
    void closeConnection();

    /**
     * A method which removes an entity.
     * @param entity to remove.
     */
    void remove(T1 entity);

    /**
     * A method which updates an entity.
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    void update(String id, T2 form);

    /**
     * A mthod which returns an entity by an id.
     * @param id an id of a required entity
     * @return a required entity.
     */
    Optional<T1> getById(String id);
}
