package com.github.android.lvrn.lvrnproject.service.core.impl;

import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.persistent.repository.core.TagRepository;
import com.github.valhallalabs.laverna.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.core.TagService;
import com.github.android.lvrn.lvrnproject.service.form.TagForm;
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.google.common.base.Optional;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TagServiceImpl extends ProfileDependedServiceImpl<Tag, TagForm> implements TagService {

    private final TagRepository mTagRepository;

    @Inject
    public TagServiceImpl(@NonNull TagRepository tagRepository, @NonNull ProfileService profileService) {
        super(tagRepository, profileService);
        mTagRepository = tagRepository;
    }

    @Override
    public Optional<String> create(@NonNull TagForm tagForm) {
        String tagId = UUID.randomUUID().toString();
        if(validateForCreate(tagForm.getProfileId(),tagForm.getName()) && mTagRepository.add(tagForm.toEntity(tagId))) {
            return Optional.of(tagId);
        }
        return Optional.absent();
    }

    @Override
    @Deprecated
    public boolean update(@NonNull String id, @NonNull TagForm tagForm) {
        //TODO: will appear in future milestone if Laverna implements this method properly.
//        validateForUpdate(tagForm.getName());
//        mTagRepository.update(tagForm.toEntity(id));
        return false;
    }

    @NonNull
    @Override
    public List<Tag> getByName(@NonNull String profileId, @NonNull String name, @NonNull PaginationArgs paginationArgs) {
        return mTagRepository.getByName(profileId, name, paginationArgs);
    }

    @NonNull
    @Override
    public List<Tag> getByNote(@NonNull String noteId) {
        return mTagRepository.getByNote(noteId);
    }

    @Override
    public void save(@NonNull Tag tag) {
        mTagRepository.add(tag);
    }

    /**
     * A method which validates a form in the create method.
     * @param profileId and id of profile to validate.
     * @param name an id of the entity to validate.
     * @return a boolean result of validation.
     */
    private boolean validateForCreate(String profileId, String name) {
        return super.checkProfileExistence(profileId) && !TextUtils.isEmpty(name);
    }
}
