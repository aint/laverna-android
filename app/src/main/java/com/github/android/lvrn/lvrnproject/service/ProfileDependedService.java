package com.github.android.lvrn.lvrnproject.service;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.service.form.Form;
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileDependedService<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm> extends BasicService<T1, T2>  {

    /**
     * A method which returns an amount of entities from a received position by a profile.
     * @param profile
     * @param from a start position.
     * @param amount a number of entities.
     * @return a list of entites.
     */
    List<T1> getByProfile(Profile profile, int from, int amount);
}
