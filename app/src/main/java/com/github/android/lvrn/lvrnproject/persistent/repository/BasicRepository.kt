package com.github.android.lvrn.lvrnproject.persistent.repository

import androidx.annotation.NonNull
import com.github.valhallalabs.laverna.persistent.entity.base.Entity
import java.util.Optional

interface BasicRepository<T : Entity> {

    /**
     * A method which adds an entity to a database.
     * @param entity an object which extends the Laverna's entity class.
     * @return a boolean result of an insertion.
     */
    fun add(entity: T): Boolean

    /**
     * A method which removes an entity from a database by its id.
     * @param id an id of the Laverna's entity class.
     * @return a boolean result of a removing
     */
    fun remove(id: String): Boolean

    /**
     * A method which retrieves an entity from a database by its id.
     * @param id an id of the required entity.
     * @return an {@code Optional} object which may contain the required entity.
     */
    fun getById( id: String): Optional<T>

    /**
     * A method which opens a database connection.
     * @return a status of the opening.
     */
    fun openDatabaseConnection(): Boolean

    /**
     * A method which closes a database connection.
     * @return a status of the closing.
     */
    fun closeDatabaseConnection(): Boolean

    /**
     * A method which returns a status of connection opening.
     * @return a status of the opening
     */
    fun isConnectionOpened(): Boolean

}