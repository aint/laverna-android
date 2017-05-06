package com.github.android.lvrn.lvrnproject.service.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

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
    public void openConnection() {
        basicRepository.openDatabaseConnection();
    }

    @Override
    public void closeConnection() {
        basicRepository.closeDatabaseConnection();
    }

    @NonNull
    @Override
    public Optional<T1> getById(@NonNull String id) {
        return basicRepository.getById(id);
    }

    @Override
    public void remove(@NonNull String id) {
        basicRepository.remove(id);
    }

    /**
     * A method which checks received string on null or equality to empty string.
     * @param string a text to check.
     * @throws IllegalArgumentException in case if name is empty.
     */
    protected void checkName(@Nullable String string) {
        if (TextUtils.isEmpty(string)) {
            throw new IllegalArgumentException("No name/title/description");
        }
    }
}
