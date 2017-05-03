package com.github.android.lvrn.lvrnproject.service.extension;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.service.BasicService;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileService extends BasicService<Profile> {

    /**
     * @param name
     * @throws IllegalArgumentException
     */
    void create(String name);

    List<Profile> getAll();
}
