package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.NotebooksRepository;
import com.github.android.lvrn.lvrnproject.service.NotebooksService;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileDependedServiceImpl;

import java.util.UUID;

import javax.inject.Inject;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebooksServiceImpl extends ProfileDependedServiceImpl<Notebook> implements NotebooksService {

    private final NotebooksRepository mNotebooksRepository;

    @Inject
    public NotebooksServiceImpl(NotebooksRepository notebooksRepository, ProfilesService profilesService) {
        super(notebooksRepository, profilesService);
        mNotebooksRepository = notebooksRepository;
    }

    /**
     * @param profileId
     * @param parentNotebookId
     * @param name
     * @throws IllegalArgumentException
     */
    @Override
    public void create(String profileId, String parentNotebookId, String name) {
        validate(profileId, parentNotebookId, name);
        mNotebooksRepository.add(new Notebook(
                UUID.randomUUID().toString(),
                profileId,
                parentNotebookId,
                name,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                0));
    }

    /**
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    @Override
    public void update(Notebook entity) {
        validate(entity.getProfileId(), entity.getParentId(), entity.getName());
        mNotebooksRepository.update(entity);
    }

    /**
     * @param profileId
     * @param parentNotebookId
     * @param name
     * @throws IllegalArgumentException
     */
    private void validate(String profileId, String parentNotebookId, String name) {
        super.checkProfileExistence(profileId);
        checkNotebookExistence(parentNotebookId);
        super.checkName(name);
    }

    /**
     * @param notebookId
     * @throws IllegalArgumentException
     */
    private void checkNotebookExistence(String notebookId) {
        if (notebookId != null && !getById(notebookId).isPresent()) {
            throw new IllegalArgumentException("The notebook is not found!");
        }
    }
}
