package com.github.android.lvrn.lvrnproject.persistent.repository;


import java.util.List;
import java.util.Optional;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface Repository<T> {

    void add(T entity);

    void add(Iterable<T> entities);

    void update(T entities);

    void remove(String id);

    T get(String id);

    List<T> get(int from, int amount);
}

