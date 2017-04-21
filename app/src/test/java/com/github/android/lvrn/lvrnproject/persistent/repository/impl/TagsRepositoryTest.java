package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.TagEntity;
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

    private TagEntity tag1;

    private TagEntity tag2;

    private TagEntity tag3;

    private List<TagEntity> tags;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        tagsRepository = new TagsRepository();

        tag1 = new TagEntity(
                "id_1",
                "profile_id_1",
                "name_1",
                1111,
                2222,
                0
        );

        tag2 = new TagEntity(
                "id_2",
                "profile_id_2",
                "name_2",
                1111,
                2222,
                0
        );

        tag3 = new TagEntity(
                "id_3",
                "profile_id_3",
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
    public void repositoryShouldAddAndGetEntityById() {
        tagsRepository.add(tag1);
        Optional<TagEntity> tagOptional = tagsRepository.get(tag1.getId());
        assertThat(tagOptional.isPresent()).isTrue();
        assertThat(tagOptional.get()).isEqualToComparingFieldByField(tag1);
    }

    @Test
    public void repositoryShouldAddAndGetEntities() {
        tagsRepository.add(tags);

        List<TagEntity> notebookEntities1 = tagsRepository.get(1, 3);

        assertThat(tags).hasSameSizeAs(notebookEntities1);
        assertThat((Object) tags)
                .isEqualToComparingFieldByFieldRecursively(notebookEntities1);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        tagsRepository.add(tag1);

        tag1.setName("new name");

        tagsRepository.update(tag1);

        Optional<TagEntity> tagOptional = tagsRepository.get(tag1.getId());
        assertThat(tagOptional.get()).isEqualToComparingFieldByField(tag1);
    }

    @Test
    public void repositoryShouldRemoveEntity() {
        tagsRepository.add(tag1);

        tagsRepository.remove(tag1.getId());

        assertThat(tagsRepository.get(tag1.getId()).isPresent()).isFalse();
    }

    @After
    public void finish() {
        tagsRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
