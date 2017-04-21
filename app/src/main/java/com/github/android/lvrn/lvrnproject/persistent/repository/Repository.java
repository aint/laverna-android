package com.github.android.lvrn.lvrnproject.persistent.repository;


import com.google.common.base.Optional;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface Repository<T> {

    void add(T entity);

    void add(Iterable<T> entities);

    void update(T entities);

    void remove(String id);

    Optional<T> get(String id);

    List<T> get(int from, int amount);
}

