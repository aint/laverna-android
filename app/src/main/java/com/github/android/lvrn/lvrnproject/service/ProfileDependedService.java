package com.github.android.lvrn.lvrnproject.service;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.service.form.Form;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileDependedService<T1 extends ProfileDependedEntity, T2 extends Form> extends BasicService<T1, T2> {

    /**
     * A method which returns an amount of entities from a start position by a profile id.
     * @param profileId an id of a profile.
     * @param from a start position.
     * @param amount a number of entities.
     * @return a list of entities.
     */
    @NonNull
    List<T1> getByProfile(@NonNull String profileId, @Size(min = 1) int from, @Size(min = 2) int amount);

    /**
     * A method which updates an entity.
     * @param id an id of the entity.
     * @param form a form with a data.
     */
    boolean update(@NonNull String id, @NonNull T2 form);
}
