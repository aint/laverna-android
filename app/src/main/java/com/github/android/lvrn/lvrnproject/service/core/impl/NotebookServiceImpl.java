package com.github.android.lvrn.lvrnproject.service.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.persistent.repository.core.NotebookRepository;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.google.common.base.Optional;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;


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
    public Optional<String> create(@NonNull NotebookForm notebookForm) {
        String notebookId = UUID.randomUUID().toString();
        if (validateForCreate(notebookForm.getProfileId(), notebookForm.getParentNotebookId(), notebookForm.getName())
                && mNotebookRepository.add(notebookForm.toEntity(notebookId))) {
            return Optional.of(notebookId);
        }
        return Optional.absent();
    }

    @Override
    public void save(@NonNull Notebook notebook) {
        mNotebookRepository.add(notebook);
    }

    @NonNull
    @Override
    public List<Notebook> getByName(@NonNull String profileId, @NonNull String name, @NonNull PaginationArgs paginationArgs) {
        return mNotebookRepository.getByName(profileId, name, paginationArgs);
    }

    @NonNull
    @Override
    public List<Notebook> getChildren(@NonNull String notebookId, @NonNull PaginationArgs paginationArgs) {
        return mNotebookRepository.getChildren(notebookId, paginationArgs);
    }

    @NonNull
    @Override
    public List<Notebook> getRootParents(@NonNull String profileId, @NonNull PaginationArgs paginationArgs) {
        return mNotebookRepository.getRootParents(profileId, paginationArgs);
    }

    @Override
    public boolean update(@NonNull String id, @NonNull NotebookForm notebookForm) {
        return validateForUpdate(notebookForm.getParentNotebookId(), notebookForm.getName())
                && mNotebookRepository.update(notebookForm.toEntity(id));
    }

    /**
     * A method which validates a form in the create method.
     * @param profileId an profile id of the form for a validate.
     * @param parentNotebookId an parent notebook id.
     * @param name a name of an entity.
     * @return a boolean result of a validation.
     */
    private boolean validateForCreate(String profileId, String parentNotebookId, String name) {
        return super.checkProfileExistence(profileId) && checkNotebookExistence(parentNotebookId) && !TextUtils.isEmpty(name);
    }

    /**
     * A method which validates a form in the update method.
     * @param parentNotebookId an parent notebook id.
     * @param name a name of an entity.
     * @return a boolean result of a validation.
     */
    private boolean validateForUpdate(String parentNotebookId, String name) {
        return checkNotebookExistence(parentNotebookId) && !TextUtils.isEmpty(name);
    }

    /**
     * A method which checks an existence of notebook in a database.
     * @param notebookId an id of a required notebook.
     * @return a boolean result of a validation.
     */
    private boolean checkNotebookExistence(@Nullable String notebookId) {
        return notebookId == null || getById(notebookId).isPresent();
    }
}
