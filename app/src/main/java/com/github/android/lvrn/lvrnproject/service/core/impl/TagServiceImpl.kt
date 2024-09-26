package com.github.android.lvrn.lvrnproject.service.core.impl

import android.text.TextUtils
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TagRepository
import com.github.android.lvrn.lvrnproject.service.core.ProfileService
import com.github.android.lvrn.lvrnproject.service.core.TagService
import com.github.android.lvrn.lvrnproject.service.form.TagForm
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Tag
import java.util.Optional
import java.util.UUID
import javax.inject.Inject

class TagServiceImpl @Inject constructor(val mTagRepository : TagRepository, profileService: ProfileService )
    : ProfileDependedServiceImpl<Tag, TagForm>(mTagRepository, profileService),
    TagService {

    override fun create(tagForm: TagForm): Optional<String> {
        val tagId = UUID.randomUUID().toString()
        if (validateForCreate(
                tagForm.profileId,
                tagForm.name
            ) && mTagRepository.add(tagForm.toEntity(tagId))
        ) {
            return Optional.of(tagId)
        }
        return Optional.empty()
    }

    @Deprecated("")
    override fun update(id: String, tagForm: TagForm): Boolean {
        //TODO: will appear in future milestone if Laverna implements this method properly.
//        validateForUpdate(tagForm.getName());
//        mTagRepository.update(tagForm.toEntity(id));
        return false
    }

    override fun getByName(
        profileId: String,
        name: String,
        paginationArgs: PaginationArgs,
    ): List<Tag> {
        return mTagRepository.getByName(profileId, name, paginationArgs)
    }

    override fun getByNote(noteId: String): List<Tag> {
        return mTagRepository.getByNote(noteId)
    }

    override fun save(tag: Tag) {
        mTagRepository.add(tag)
    }

    /**
     * A method which validates a form in the create method.
     * @param profileId and id of profile to validate.
     * @param name an id of the entity to validate.
     * @return a boolean result of validation.
     */
    private fun validateForCreate(profileId: String, name: String): Boolean {
        return super.checkProfileExistence(profileId) && !TextUtils.isEmpty(name)
    }
}