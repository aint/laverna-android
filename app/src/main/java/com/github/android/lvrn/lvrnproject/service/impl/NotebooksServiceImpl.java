package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.dagger.DaggerComponentsContainer;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.NotebooksRepository;
import com.github.android.lvrn.lvrnproject.service.NotebooksService;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileDependedServiceImpl;

import javax.inject.Inject;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebooksServiceImpl extends ProfileDependedServiceImpl<Notebook> implements NotebooksService {

    @Inject NotebooksRepository mNotebooksRepository;

    public NotebooksServiceImpl() {
        DaggerComponentsContainer.getRepositoryComponent().injectNotebooksService(this);
        basicRepository = mNotebooksRepository;
    }

    @Override
    public void create(String profileId, String parentNotebookId, String name) throws NullPointerException {
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
    public void update(Notebook entity) {
        validate(entity.getProfileId(), entity.getParentId(), entity.getName());
        mNotebooksRepository.update(entity);
    }

    private void validate(String profileId, String parentNotebookId, String name) throws NullPointerException{
        checkProfileExistence(profileId);
        checkNotebookExistence(parentNotebookId);
        checkName(name);
    }

    private void checkNotebookExistence(String notebookId) throws NullPointerException {
        if (notebookId != null && !getById(notebookId).isPresent()) {
            throw new NullPointerException("The notebook is not found!");
        }
    }
}
