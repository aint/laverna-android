package com.github.android.lvrn.lvrnproject.persistent.repository;


import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.google.common.base.Optional;

import java.util.Collection;
import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface BasicRepository<T extends Entity> {

    void add(T entity);

    void add(Collection<T> entities);

    void update(T entity);

    void remove(T entity);

    Optional<T> getById(String id);

//    List<T> getByRawQuery(String query);

    boolean openDatabaseConnection();

    boolean closeDatabaseConnection();
}

