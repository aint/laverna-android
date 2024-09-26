package com.github.android.lvrn.lvrnproject.service

import com.github.android.lvrn.lvrnproject.service.form.Form
import com.github.valhallalabs.laverna.persistent.entity.base.Entity
import java.util.Optional

interface BasicService<T1 : Entity?, T2 : Form<*>?> {

    /**
     * A method which creates an entity by a received form.
     * @param form a form with a data.
     * @return an `Optional` object which may contain an id of the entity in case of
     * successful creation.
     */
    fun create(form: T2): Optional<String>

    /**
     * A method which opens a connection to a repository.
     */
    fun openConnection(): Boolean

    /**
     * A method which closes a connection to a repository.
     */
    fun closeConnection(): Boolean

    fun isConnectionOpened(): Boolean

    /**
     * A method which removes an entity by an id.
     * @param id an id of an entity.
     */
    fun remove(id: String): Boolean

    /**
     * A method which returns an entity by an id.
     * @param id an id of a required entity
     * @return a required entity.
     */
    fun getById(id: String): Optional<T1>
}