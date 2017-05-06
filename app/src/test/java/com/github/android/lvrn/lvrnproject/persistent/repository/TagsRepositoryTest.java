package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TagRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.NoteRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.TagRepositoryImpl;
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

    private TagRepository tagRepository;

    private Tag tag1;

    private Tag tag2;

    private Tag tag3;

    private Profile profile;

    private List<Tag> tags;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        ProfileRepositoryImpl profilesRepository = new ProfileRepositoryImpl();
        profilesRepository.openDatabaseConnection();
        profile = new Profile("profile_id_1", "first profile");
        profilesRepository.add(profile);
        profilesRepository.add(new Profile("profile_id_2", "second profile"));
        profilesRepository.closeDatabaseConnection();


        tagRepository = new TagRepositoryImpl();

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

        tagRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldGetEntityById() {
        tagRepository.add(tag1);
        Optional<Tag> tagOptional = tagRepository.getById(tag1.getId());
        assertThat(tagOptional.isPresent()).isTrue();
        assertThat(tagOptional.get()).isEqualToComparingFieldByField(tag1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        tagRepository.add(tag1);
        tagRepository.add(tag2);
        tagRepository.add(tag3);

        List<Tag> tagEntities1 = tagRepository
                .getByProfile(profile.getId(), 1, 3);

        assertThat(tagEntities1.size()).isNotEqualTo(tags.size());
        assertThat(tagEntities1.size()).isEqualTo(tags.size() - 1);

        tags.remove(tag3);
        assertThat((Object) tagEntities1).isEqualToComparingFieldByFieldRecursively(tags);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        tagRepository.add(tag1);

        tag1.setName("new name");

        tagRepository.update(tag1);

        Optional<Tag> tagOptional = tagRepository.getById(tag1.getId());
        assertThat(tagOptional.get()).isEqualToComparingFieldByField(tag1);
    }

    @Test
    public void repositoryShoudGetTagsByNote() {
        tagRepository.add(tag1);
        tagRepository.add(tag2);
        tagRepository.add(tag3);

        NoteRepositoryImpl notesRepository = new NoteRepositoryImpl();
        notesRepository.openDatabaseConnection();
        Note note1 = new Note("note_id_1","profile_id_1", null, "title", 1111, 2222, "dfdf", true);
        notesRepository.add(note1);
        notesRepository.addTagToNote(note1.getId(), tag1.getId());
        notesRepository.addTagToNote(note1.getId(), tag2.getId());
        notesRepository.closeDatabaseConnection();

        List<Tag> tags1 = tagRepository.getByNote(note1.getId());

        assertThat(tags1.size()).isNotEqualTo(tags.size());
        assertThat(tags1.size()).isEqualTo(tags.size() - 1);

        tags.remove(tag3);
        assertThat((Object) tags1).isEqualToComparingFieldByFieldRecursively(tags);
    }


    @Test
    public void repositoryShouldRemoveEntity() {
        tagRepository.add(tag1);

        tagRepository.remove(tag1.getId());

        assertThat(tagRepository.getById(tag1.getId()).isPresent()).isFalse();
    }

    @Test
    public void repositoryShouldGetTagByName() {
        tagRepository.add(tag1);
        tag2.setName("name2");
        tagRepository.add(tag2);

        List<Tag> result1 = tagRepository.getByName("name", 1, 5);

        assertThat(result1).hasSize(2);

        List<Tag> result2 = tagRepository.getByName("name_1", 1, 5);

        assertThat(result2).hasSize(1);
    }

    @After
    public void finish() {
        tagRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
