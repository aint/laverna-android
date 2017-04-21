package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.TagEntity;

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

    private TagsRepository TagsRepository;

    private TagEntity tagEntity1;

    private TagEntity tagEntity2;

    private TagEntity tagEntity3;

    private List<TagEntity> tagEntities;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        TagsRepository = new TagsRepository();

        tagEntity1 = new TagEntity(
                "id_1",
                "profile_id_1",
                "name_1",
                1111,
                2222,
                0
        );

        tagEntity2 = new TagEntity(
                "id_2",
                "profile_id_2",
                "name_2",
                1111,
                2222,
                0
        );

        tagEntity3 = new TagEntity(
                "id_3",
                "profile_id_3",
                "name_3",
                1111,
                2222,
                0
        );

        tagEntities = new ArrayList<>();
        tagEntities.add(tagEntity1);
        tagEntities.add(tagEntity2);
        tagEntities.add(tagEntity3);

        TagsRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldAddAndGetEntityById() {
        TagsRepository.add(tagEntity1);
        TagEntity TagEntity11 = TagsRepository.get(tagEntity1.getId());
        assertThat(tagEntity1).isEqualToComparingFieldByField(TagEntity11);

        TagsRepository.add(tagEntity2);
        TagEntity TagEntity22 = TagsRepository.get(tagEntity2.getId());
        assertThat(tagEntity2).isEqualToComparingFieldByField(TagEntity22);

        TagsRepository.add(tagEntity3);
        TagEntity TagEntity33 = TagsRepository.get(tagEntity3.getId());
        assertThat(tagEntity3).isEqualToComparingFieldByField(TagEntity33);
    }

    @Test
    public void repositoryShouldAddAndGetEntities() {
        TagsRepository.add(tagEntities);

        List<TagEntity> notebookEntities1 = TagsRepository.get(1, 3);

        assertThat(tagEntities).hasSameSizeAs(notebookEntities1);
        assertThat((Object) tagEntities)
                .isEqualToComparingFieldByFieldRecursively(notebookEntities1);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        TagsRepository.add(tagEntity1);

        tagEntity1.setName("new name");

        TagsRepository.update(tagEntity1);

        TagEntity TagEntity = TagsRepository.get(tagEntity1.getId());
        assertThat(TagEntity).isEqualToComparingFieldByField(tagEntity1);
    }

    @Test(expected = NullPointerException.class)
    public void repositoryShouldRemoveEntity() {
        TagsRepository.add(tagEntity1);

        TagsRepository.remove(tagEntity1.getId());

        TagsRepository.get(tagEntity1.getId());
    }

    @After
    public void finish() {
        TagsRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
