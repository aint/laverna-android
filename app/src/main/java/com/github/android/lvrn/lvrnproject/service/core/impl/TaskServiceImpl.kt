package com.github.android.lvrn.lvrnproject.service.core.impl

import android.text.TextUtils
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TaskRepository
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.core.TaskService
import com.github.android.lvrn.lvrnproject.service.form.TaskForm
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Task
import java.util.Optional
import java.util.UUID
import javax.inject.Inject

class TaskServiceImpl @Inject constructor(
    val mTaskRepository: TaskRepository,
    profileService: ProfileService,
) : ProfileDependedServiceImpl<Task, TaskForm>(mTaskRepository, profileService),
    TaskService {
    override fun create(taskForm: TaskForm): Optional<String> {
        val taskId = UUID.randomUUID().toString()
        if (validateForCreate(taskForm.profileId, taskForm.description) && mTaskRepository.add(
                taskForm.toEntity(taskId)
            )
        ) {
            return Optional.of(taskId)
        }
        return Optional.empty()
    }

    override fun update(id: String, taskForm: TaskForm): Boolean {
        return !TextUtils.isEmpty(taskForm.description) && mTaskRepository.update(
            taskForm.toEntity(
                id
            )
        )
    }

    override fun getUncompletedByProfile(
        profileId: String,
        paginationArgs: PaginationArgs,
    ): List<Task> {
        return mTaskRepository.getUncompletedByProfile(profileId, paginationArgs)
    }

    override fun getUncompletedByProfileAndDescription(
        profileId: String,
        description: String,
        paginationArgs: PaginationArgs,
    ): List<Task> {
        return mTaskRepository.getUncompletedByProfileAndDescription(
            profileId,
            description,
            paginationArgs
        )
    }

    override fun getByNote(noteId: String): List<Task> {
        return mTaskRepository.getByNote(noteId)
    }

    /**
     * A method which validates a form in the create method.
     * @param profileId and id of profile to validate.
     * @param description a text description of the entity to validate.
     */
    private fun validateForCreate(profileId: String, description: String): Boolean {
        return super.checkProfileExistence(profileId) && !TextUtils.isEmpty(description)
    }
}