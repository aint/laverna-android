package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.dagger.DaggerComponentsContainer;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotebooksRepositoryImp;
import com.github.android.lvrn.lvrnproject.service.NotebooksService;
import com.google.common.base.Optional;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebooksServiceImpl implements NotebooksService {

    @Inject
    NotebooksRepositoryImp notebooksRepository;

    public NotebooksServiceImpl() {
        DaggerComponentsContainer.getRepositoryComponent().injectNotebooksService(this);
    }

    @Override
    public void openConnection() {
        notebooksRepository.openDatabaseConnection();
    }

    @Override
    public void closeConnection() {
        notebooksRepository.closeDatabaseConnection();
    }

    @Override
    public void remove(Notebook entity) {
        notebooksRepository.remove(entity);
    }

    @Override
    public Optional<Notebook> getById(String id) {
        return notebooksRepository.getById(id);
    }

    @Override
    public void create(Profile profile, Notebook parentNotebook, String name) {
        //TODO: throw exception if profile is null, or not exist.
        //TODO: throw exception if parentbook is not exist.
        //TODO: throw exception if name is null or equals "".
    }

    @Override
    public List<Notebook> getByProfile(Profile profile, int from, int amount) {
        return null;
    }
}
