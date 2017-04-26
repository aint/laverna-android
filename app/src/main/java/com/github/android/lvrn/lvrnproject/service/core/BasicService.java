package com.github.android.lvrn.lvrnproject.service.core;

import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.google.common.base.Optional;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface BasicService<T extends Entity> {

    void openConnection();

    void closeConnection();

    void remove(T entity);

    Optional<T> getById(String id);
}
