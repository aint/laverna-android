package com.github.android.lvrn.lvrnproject.service.impl;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.persistent.repository.BasicRepository;
import com.github.android.lvrn.lvrnproject.service.BasicService;
import com.github.android.lvrn.lvrnproject.service.form.Form;
import com.google.common.base.Optional;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class BasicServiceImpl<T1 extends Entity, T2 extends Form> implements BasicService<T1, T2> {

    private final BasicRepository<T1> basicRepository;

    public BasicServiceImpl(@NonNull BasicRepository<T1> basicRepository) {
        this.basicRepository = basicRepository;
    }

    @Override
    public boolean openConnection() {
        return basicRepository.openDatabaseConnection();
    }

    @Override
    public boolean isConnectionOpened() {
        return basicRepository.isConnectionOpened();
    }

    @Override
    public boolean closeConnection() {
        return basicRepository.closeDatabaseConnection();
    }

    @NonNull
    @Override
    public Optional<T1> getById(@NonNull String id) {
        return basicRepository.getById(id);
    }

    @Override
    public boolean remove(@NonNull String id) {
        return basicRepository.remove(id);
    }
}
