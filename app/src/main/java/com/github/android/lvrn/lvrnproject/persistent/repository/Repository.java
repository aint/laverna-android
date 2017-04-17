package com.github.android.lvrn.lvrnproject.persistent.repository;


import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface Repository<T> {

    void add(T item);

    void add(Iterable<T> items);

    void update(T item);

    void remove(T item);

    T get(String id);

    List<T> get(int from, int amount);
}

