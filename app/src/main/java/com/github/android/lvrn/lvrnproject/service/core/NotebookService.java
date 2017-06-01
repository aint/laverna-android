package com.github.android.lvrn.lvrnproject.service.core;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookService extends ProfileDependedService<Notebook, NotebookForm> {

    /**
     * A method which retrieves an amount of entities from a start position by a name.
     * @param profileId an id of a profile.
     * @param name a required name.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    @NonNull
    List<Notebook> getByName(@NonNull String profileId, @NonNull String name, @NonNull PaginationArgs paginationArgs);
}
