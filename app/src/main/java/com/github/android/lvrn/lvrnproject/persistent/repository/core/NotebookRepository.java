package com.github.android.lvrn.lvrnproject.persistent.repository.core;

import androidx.annotation.NonNull;

import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookRepository extends ProfileDependedRepository<Notebook> {

    /**
     * A method which retrieves an amount of entities from a start position by a name.
     * @param profileId an id of profile.
     * @param name a required name.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Notebook> getByName(@NonNull String profileId, @NonNull String name, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of entities from a start position by a parent notebook's id.
     * @param notebookId an id of a parent notebook.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Notebook> getChildren(@NonNull String notebookId, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of entities from a start position which are root parents (has no parent).
     * @param profileId an id of profile.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Notebook> getRootParents(@NonNull String profileId, @NonNull PaginationArgs paginationArgs);
}
