package com.github.android.lvrn.lvrnproject.persistent.repository;


import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.google.common.base.Optional;

import java.util.Collection;
import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface Repository<T extends Entity> {

    void add(T entity);

    void add(Collection<T> entities);

    void update(T entities);

    void remove(String id);

    Optional<T> get(String id);

//    List<T> get(int from, int amount);

    List<T> getByRawQuery(String query);
}

