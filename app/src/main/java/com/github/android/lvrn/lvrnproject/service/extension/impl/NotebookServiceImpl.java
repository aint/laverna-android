package com.github.android.lvrn.lvrnproject.service.extension.impl;

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
    public NotebookServiceImpl(NotebookRepository notebookRepository, ProfileService profileService) {
        super(notebookRepository, profileService);
        mNotebookRepository = notebookRepository;
    }

    @Override
    public void create(NotebookForm notebookForm) {

        validate(notebookForm.getProfileId(), notebookForm.getParentNotebookId(), notebookForm.getName());

        mNotebookRepository.add(
                new Notebook(
                        UUID.randomUUID().toString(),
                        notebookForm.getProfileId(),
                        notebookForm.getParentNotebookId(),
                        notebookForm.getName(),
                        System.currentTimeMillis(),
                        System.currentTimeMillis(),
                        0
                )
        );
    }

    @Override
    public List<Notebook> getByName(String name, int from, int amount) {
        return mNotebookRepository.getByName(name, from, amount);
    }

    /**
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    @Override
    public void update(String id, NotebookForm notebookForm) {
        //TODO: change date of update.
        //TODO: Write what fields to update in database.
//        validate(entity.getProfileId(), entity.getParentId(), entity.getName());
//
//        mNotebookRepository.update();
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
