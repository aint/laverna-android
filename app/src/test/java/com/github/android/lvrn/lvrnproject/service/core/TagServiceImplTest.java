package com.github.android.lvrn.lvrnproject.service.core;

import com.github.valhallalabs.laverna.persistent.entity.Profile;
import com.github.valhallalabs.laverna.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TagRepository;
import com.github.android.lvrn.lvrnproject.service.core.impl.TagServiceImpl;
import com.github.android.lvrn.lvrnproject.service.form.TagForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.google.common.base.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(MockitoJUnitRunner.class)
public class TagServiceImplTest {
    private final String profileId = "profileId";
    private final String tagName = "tagName";
    private final String notebookId = "notebookId";
    private final String noteId = "noteId";
    private final String profileName = "profileName";
    private final String tagId ="tagId";
    private final int count = 30 ;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ProfileService profileService;
    private TagService tagService;
    private TagForm tagForm;
    private Tag tag;
    private PaginationArgs paginationArgs;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tagService = new TagServiceImpl(tagRepository,profileService);
        tagForm = new TagForm(profileId,tagName);
        tag = new Tag(tagId,profileId,tagName,System.currentTimeMillis(),System.currentTimeMillis(),count);
        paginationArgs = new PaginationArgs();
    }

    @Test
    public void create(){
        when(tagRepository.add(any())).thenReturn(true);
        when(profileService.getById(profileId)).thenReturn(Optional.of(new Profile(profileId, profileName)));

        Optional<String> result = tagService.create(tagForm);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void getByName(){
        List<Tag> expected = new ArrayList<>();
        tagRepository.getByName(profileId,tagName,paginationArgs);

        List<Tag> result = tagService.getByName(profileId,tagName,paginationArgs);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void getByNote(){
        List<Tag> expected = new ArrayList<>();
        tagRepository.getByNote(noteId);

        List<Tag> result = tagService.getByNote(noteId);
        assertThat(result).isEqualTo(expected);
    }

}
