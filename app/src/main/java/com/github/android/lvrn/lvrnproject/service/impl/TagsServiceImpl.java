package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.TagsRepository;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.TagsService;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileDependedServiceImpl;

import java.util.List;

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

    @Override
    public void create(String profileId, String name) throws IllegalArgumentException {
        validate(profileId, name);
        //TODO:generate id
        mTagsRepository.add(new Tag("id",
                profileId,
                name,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                0));
    }

    @Override
    public void update(Tag entity) throws IllegalArgumentException {
        validate(entity.getProfileId(), entity.getName());
        mTagsRepository.update(entity);
    }

    @Override
    public List<Tag> getByNote(Note note, int from, int amount) {
        return mTagsRepository.getByNote(note, from, amount);
    }

    private void validate(String profileId, String name) throws NullPointerException {
        checkProfileExistence(profileId);
        checkName(name);
    }
}
