package com.github.android.lvrn.lvrnproject.service.extension.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TagRepository;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.extension.TagService;
import com.github.android.lvrn.lvrnproject.service.form.TagForm;
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import static android.R.attr.name;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TagServiceImpl extends ProfileDependedServiceImpl<Tag, TagForm> implements TagService {

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
    public void create(TagForm tagForm) {
        validate(tagForm.getProfileId(),tagForm.getName());
        mTagRepository.add(new Tag(
                UUID.randomUUID().toString(),
                tagForm.getProfileId(),
                tagForm.getName(),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                0));
    }

    /**
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    @Override
    public void update(String id, TagForm tagForm) {
        //TODO: change date of update.
        //TODO: Write what fields to update in database(not to update creation time)
//        validate(entity.getProfileId(), entity.getName());
//        mTagRepository.update(entity);
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
