package com.github.android.lvrn.lvrnproject.service.extension.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.NotebookRepository;
import com.github.android.lvrn.lvrnproject.service.extension.NotebookService;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.Form;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import static android.R.attr.name;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookServiceImpl extends ProfileDependedServiceImpl<Notebook, NotebookForm> implements NotebookService {

    private final NotebookRepository mNotebookRepository;

    @Inject
    public NotebookServiceImpl(@NonNull NotebookRepository notebookRepository, @NonNull ProfileService profileService) {
        super(notebookRepository, profileService);
        mNotebookRepository = notebookRepository;
    }

    @Override
    public void create(@NonNull NotebookForm notebookForm) {
        validateForCreate(notebookForm.getProfileId(), notebookForm.getParentNotebookId(), notebookForm.getName());
        mNotebookRepository.add(notebookForm.toEntity(UUID.randomUUID().toString()));
    }

    @NonNull
    @Override
    public List<Notebook> getByName(@NonNull String name, int from, int amount) {
        return mNotebookRepository.getByName(name, from, amount);
    }

    /**
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    @Override
    public void update(@NonNull String id, @NonNull NotebookForm notebookForm) {
        validateForUpdate(notebookForm.getParentNotebookId(), notebookForm.getName());
        mNotebookRepository.update(notebookForm.toEntity(id));
    }

    /**
     * @param profileId
     * @param parentNotebookId
     * @param name
     * @throws IllegalArgumentException
     */
    private void validateForCreate(String profileId, String parentNotebookId, String name) {
        super.checkProfileExistence(profileId);
        checkNotebookExistence(parentNotebookId);
        super.checkName(name);
    }

    private void validateForUpdate(String parentNotebookId, String name) {
        checkNotebookExistence(parentNotebookId);
        super.checkName(name);
    }

    /**
     * @param notebookId
     * @throws IllegalArgumentException
     */
    private void checkNotebookExistence(@Nullable String notebookId) {
        if (notebookId != null && !getById(notebookId).isPresent()) {
            throw new IllegalArgumentException("The notebook is not found!");
        }
    }
}
