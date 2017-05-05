package com.github.android.lvrn.lvrnproject.service.extension;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.Form;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookService extends ProfileDependedService<Notebook, NotebookForm> {

    @NonNull
    List<Notebook> getByName(@NonNull String name, @Size(min = 1) int from, @Size(min = 2) int amount);
}
