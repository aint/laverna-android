package com.github.android.lvrn.lvrnproject.service.impl;

import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.persistent.repository.BasicRepository;
import com.github.android.lvrn.lvrnproject.service.BasicService;
import com.google.common.base.Optional;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class BasicServiceImpl<T extends Entity> implements BasicService<T> {

    private BasicRepository<T> basicRepository;

    public BasicServiceImpl(BasicRepository<T> basicRepository) {
        this.basicRepository = basicRepository;
    }

    @Override
    public void openConnection() {
        basicRepository.openDatabaseConnection();
    }

    @Override
    public void closeConnection() {
        basicRepository.closeDatabaseConnection();
    }

    @Override
    public Optional<T> getById(String id) {
        return basicRepository.getById(id);
    }

    @Override
    public void remove(T entity) {
        basicRepository.remove(entity);
    }

    /**
     * A method which checks received string on null or equality to empty string.
     * @param string a text to check.
     * @throws IllegalArgumentException
     */
    protected void checkName(String string) {
        if (TextUtils.isEmpty(string)) {
            throw new IllegalArgumentException("No name/title/description");
        }
    }
}
