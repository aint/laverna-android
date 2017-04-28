package com.github.android.lvrn.lvrnproject.service;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.ProfileDependedService;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebooksService extends ProfileDependedService<Notebook> {

    void create(String  profileId, String parentNotebookId, String name) throws NullPointerException;
}
