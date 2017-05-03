package com.github.android.lvrn.lvrnproject.persistent.repository.extension;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookRepository extends ProfileDependedRepository<Notebook> {

    List<Notebook> getByName(String name, int from, int amount);
}
