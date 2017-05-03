package com.github.android.lvrn.lvrnproject.service.extension;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.Form;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookService extends ProfileDependedService<Notebook, NotebookForm> {

    List<Notebook> getByName(String name, int from, int amount);
}
