package com.github.android.lvrn.lvrnproject.service.extension.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TagRepository;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.extension.TagService;
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TagServiceImpl extends ProfileDependedServiceImpl<Tag> implements TagService {

    private final TagRepository mTagRepository;

    @Inject
    public TagServiceImpl(TagRepository tagRepository, ProfileService profileService) {
        super(tagRepository, profileService);
        mTagRepository = tagRepository;
    }

    /**
     * @param profileId
     * @param name
     * @throws IllegalArgumentException
     */
    @Override
    public void create(String profileId, String name) {
        validate(profileId, name);
        mTagRepository.add(new Tag(
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
        mTagRepository.update(entity);
    }

    @Override
    public List<Tag> getByName(String name, int from, int amount) {
        return mTagRepository.getByName(name, from, amount);
    }

    @Override
    public List<Tag> getByNote(Note note, int from, int amount) {
        return mTagRepository.getByNote(note, from, amount);
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
