package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.NotebooksRepository;
import com.github.android.lvrn.lvrnproject.service.NotebooksService;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileDependedServiceImpl;

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

    @Override
    public void create(String profileId, String parentNotebookId, String name) throws IllegalArgumentException {
        validate(profileId, parentNotebookId, name);

        //TODO: create way to generate id
        mNotebooksRepository.add(new Notebook(
                "id" + System.currentTimeMillis(),
                profileId,
                parentNotebookId,
                name,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                0));
    }

    @Override
    public void update(Notebook entity) throws IllegalArgumentException {
        validate(entity.getProfileId(), entity.getParentId(), entity.getName());
        mNotebooksRepository.update(entity);
    }

    private void validate(String profileId, String parentNotebookId, String name) throws IllegalArgumentException {
        checkProfileExistence(profileId);
        checkNotebookExistence(parentNotebookId);
        checkName(name);
    }

    private void checkNotebookExistence(String notebookId) throws IllegalArgumentException {
        if (notebookId != null && !getById(notebookId).isPresent()) {
            throw new IllegalArgumentException("The notebook is not found!");
        }
    }
}
