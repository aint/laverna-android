package com.github.android.lvrn.lvrnproject.persistent.repository;


import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.google.common.base.Optional;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface BasicRepository<T extends Entity> {

    /**
     * A method which adds an entity to a database.
     * @param entity an object which extends the Laverna's entity class.
     * @return a boolean result of an insertion.
     */
    boolean add(@NonNull T entity);

    /**
     * A method which adds a collection of entities to a database.
     * @param entities a collection of objects which extends the Laverna's entity class.
     */
//    void add(@NonNull Collection<T> entities);

    /**
     * A method which removes an entity from a database by its id.
     * @param id an id of the Laverna's entity class.
     * @return a boolean result of a removing
     */
    boolean remove(@NonNull String id);

    /**
     * A method which retrieves an entity from a database by its id.
     * @param id an id of the required entity.
     * @return an {@code Optional} object which may contain the required entity.
     */
    @NonNull
    Optional<T> getById(@NonNull String id);

    /**
     * A method which opens a database connection.
     * @return a status of the opening.
     */
    boolean openDatabaseConnection();

    /**
     * A method which closes a database connection.
     * @return a status of the closing.
     */
    boolean closeDatabaseConnection();
}

