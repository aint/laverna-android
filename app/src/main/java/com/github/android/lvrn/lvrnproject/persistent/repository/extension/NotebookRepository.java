package com.github.android.lvrn.lvrnproject.persistent.repository.extension;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookRepository extends ProfileDependedRepository<Notebook> {

    @NonNull
    List<Notebook> getByName(@NonNull String name, @Size(min = 1) int from, @Size(min = 2) int amount);
}
