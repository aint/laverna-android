package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.TagsRepository;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.TagsService;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileDependedServiceImpl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TagsServiceImpl extends ProfileDependedServiceImpl<Tag> implements TagsService {

    private final TagsRepository mTagsRepository;

    @Inject
    public TagsServiceImpl(TagsRepository tagsRepository, ProfilesService profilesService) {
        super(tagsRepository, profilesService);
        mTagsRepository = tagsRepository;
    }

    /**
     * @param profileId
     * @param name
     * @throws IllegalArgumentException
     */
    @Override
    public void create(String profileId, String name) {
        validate(profileId, name);
        mTagsRepository.add(new Tag(
                UUID.randomUUID().toString(),
                profileId,
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
    public void update(Tag entity) {
        validate(entity.getProfileId(), entity.getName());
        mTagsRepository.update(entity);
    }

    @Override
    public List<Tag> getByNote(Note note, int from, int amount) {
        return mTagsRepository.getByNote(note, from, amount);
    }

    /**
     * @param profileId
     * @param name
     * @throws NullPointerException
     */
    private void validate(String profileId, String name) {
        checkProfileExistence(profileId);
        checkName(name);
    }
}
