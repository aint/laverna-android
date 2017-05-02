package com.github.android.lvrn.lvrnproject.service.core;

import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.google.common.base.Optional;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface BasicService<T extends Entity> {

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
    void remove(T entity);

    /**
     * A method which updates an entity.
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    void update(T entity);

    /**
     * A mthod which returns an entity by an id.
     * @param id an id of a required entity
     * @return a required entity.
     */
    Optional<T> getById(String id);
}
