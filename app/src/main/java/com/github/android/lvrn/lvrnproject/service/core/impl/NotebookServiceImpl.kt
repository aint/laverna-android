package com.github.android.lvrn.lvrnproject.service.core.impl

import android.text.TextUtils
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NotebookRepository
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import java.util.Optional
import java.util.UUID
import javax.inject.Inject

class NotebookServiceImpl @Inject constructor(
    val mNotebookRepository: NotebookRepository,
    profileService: ProfileService,
) : ProfileDependedServiceImpl<Notebook, NotebookForm>(mNotebookRepository, profileService),
    NotebookService {


    override fun create(notebookForm: NotebookForm): Optional<String> {
        val notebookId = UUID.randomUUID().toString()
        if (validateForCreate(
                notebookForm.profileId,
                notebookForm.parentNotebookId,
                notebookForm.name
            )
            && mNotebookRepository.add(notebookForm.toEntity(notebookId))
        ) {
            return Optional.of(notebookId)
        }
        return Optional.empty()
    }

    override fun save(notebook: Notebook) {
        mNotebookRepository.add(notebook)
    }

    override fun getByName(
        profileId: String,
        name: String,
        paginationArgs: PaginationArgs,
    ): List<Notebook> {
        return mNotebookRepository.getByName(profileId, name, paginationArgs)
    }

    override fun getChildren(notebookId: String, paginationArgs: PaginationArgs): List<Notebook> {
        return mNotebookRepository.getChildren(notebookId, paginationArgs)
    }

    override fun getRootParents(profileId: String, paginationArgs: PaginationArgs): List<Notebook> {
        return mNotebookRepository.getRootParents(profileId, paginationArgs)
    }

    override fun update(id: String, notebookForm: NotebookForm): Boolean {
        return (validateForUpdate(notebookForm.parentNotebookId, notebookForm.name)
                && mNotebookRepository.update(notebookForm.toEntity(id)))
    }

    /**
     * A method which validates a form in the create method.
     * @param profileId an profile id of the form for a validate.
     * @param parentNotebookId an parent notebook id.
     * @param name a name of an entity.
     * @return a boolean result of a validation.
     */
    private fun validateForCreate(
        profileId: String,
        parentNotebookId: String?,
        name: String,
    ): Boolean {
        return super.checkProfileExistence(profileId) && checkNotebookExistence(parentNotebookId) && !TextUtils.isEmpty(
            name
        )
    }

    /**
     * A method which validates a form in the update method.
     * @param parentNotebookId an parent notebook id.
     * @param name a name of an entity.
     * @return a boolean result of a validation.
     */
    private fun validateForUpdate(parentNotebookId: String?, name: String): Boolean {
        return checkNotebookExistence(parentNotebookId) && !TextUtils.isEmpty(name)
    }

    /**
     * A method which checks an existence of notebook in a database.
     * @param notebookId an id of a required notebook.
     * @return a boolean result of a validation.
     */
    private fun checkNotebookExistence(notebookId: String?): Boolean {
        return notebookId == null || getById(notebookId).isPresent
    }
}