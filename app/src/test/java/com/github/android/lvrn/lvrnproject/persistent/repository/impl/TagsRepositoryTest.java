package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Tag;
import com.google.common.base.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TagsRepositoryTest {

    private TagsRepository tagsRepository;

    private Tag tag1;

    private Tag tag2;

    private Tag tag3;

    private List<Tag> tags;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        tagsRepository = new TagsRepository();

        tag1 = new Tag(
                "id_1",
                "profile_id_1",
                "name_1",
                1111,
                2222,
                0
        );

        tag2 = new Tag(
                "id_2",
                "profile_id_1",
                "name_2",
                1111,
                2222,
                0
        );

        tag3 = new Tag(
                "id_3",
                "profile_id_2",
                "name_3",
                1111,
                2222,
                0
        );

        tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        tagsRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldGetEntityById() {
        tagsRepository.add(tag1);
        Optional<Tag> tagOptional = tagsRepository.get(tag1.getId());
        assertThat(tagOptional.isPresent()).isTrue();
        assertThat(tagOptional.get()).isEqualToComparingFieldByField(tag1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        tagsRepository.add(tags);

        List<Tag> tagEntities1 = tagsRepository
                .getByProfileId(tag1.getProfileId(), 1, 3);

        assertThat(tagEntities1.size()).isNotEqualTo(tags.size());
        assertThat(tagEntities1.size()).isEqualTo(tags.size() - 1);

        tags.remove(tag3);
        assertThat((Object) tagEntities1).isEqualToComparingFieldByFieldRecursively(tags);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        tagsRepository.add(tag1);

        tag1.setName("new name");

        tagsRepository.update(tag1);

        Optional<Tag> tagOptional = tagsRepository.get(tag1.getId());
        assertThat(tagOptional.get()).isEqualToComparingFieldByField(tag1);
    }

    @Test
    public void repositoryShoudGetTagsByNote() {
        NotesRepository notesRepository = new NotesRepository();
        notesRepository.openDatabaseConnection();
        Note note1 = new Note("id_1","profile_id_1", null, "title", 1111, 2222, "dfdf", true);
        notesRepository.add(note1);
        notesRepository.addTagsToNote(note1, tags);
        notesRepository.closeDatabaseConnection();

        tagsRepository.add(tags);

        List<Tag> tags1 = tagsRepository.getByNote(note1);

        assertThat(tags1).hasSameSizeAs(tags);

        assertThat((Object) tags1).isEqualToComparingFieldByFieldRecursively(tags);
    }


    @Test
    public void repositoryShouldRemoveEntity() {
        tagsRepository.add(tag1);

        tagsRepository.remove(tag1);

        assertThat(tagsRepository.get(tag1.getId()).isPresent()).isFalse();
    }

    @After
    public void finish() {
        tagsRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
