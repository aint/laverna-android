package com.github.android.lvrn.lvrnproject.persistent.repository.core;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookRepository extends ProfileDependedRepository<Notebook> {

    /**
     * A method which retrieves an amount of entities from a start position by a name.
     * @param profileId an id of profile.
     * @param name a required name.
     * @param from a start position.
     * @param amount a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Notebook> getByName(@NonNull String profileId, @NonNull String name, @Size(min = 1) int from, @Size(min = 2) int amount);

    /**
     * A method which retrieves an amount of entities from a start position by a parent notebook's id.
     * @param notebookId an id of a parent notebook.
     * @param from a start position.
     * @param amount a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Notebook> getChildren(@NonNull String notebookId, @Size(min = 1) int from, @Size(min = 2) int amount);

    /**
     * A method which retrieves an amount of entities from a start position which are root parents (has no parent).
     * @param profileId an id of profile.
     * @param from a start position.
     * @param amount a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Notebook> getRootParents(@NonNull String profileId, @Size(min = 1) int from, @Size(min = 2) int amount);
}
