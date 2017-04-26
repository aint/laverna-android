package com.github.android.lvrn.lvrnproject.service;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebooksService extends ProfileDependedService<Notebook> {

    void create(Profile profile, Notebook parentNotebook, String name);
}
