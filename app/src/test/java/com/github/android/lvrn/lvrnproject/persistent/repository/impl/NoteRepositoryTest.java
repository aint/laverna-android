package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.NoteEntity;

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
public class NoteRepositoryTest {

    private NotesRepository noteRepository;

    private NoteEntity noteEntity1;

    private NoteEntity noteEntity2;

    private NoteEntity noteEntity3;

    private List<NoteEntity> noteEntities;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        noteRepository = new NotesRepository();

        noteEntity1 = new NoteEntity(
                "id_1",
                "profile_id_1",
                "notebook_id_1",
                "title_1",
                1111,
                2222,
                "content1",
                false
        );

        noteEntity2 = new NoteEntity(
                "id_2",
                "profile_id_2",
                "notebook_id_2",
                "title_2",
                1111,
                2222,
                "content2",
                false
        );

        noteEntity3 = new NoteEntity(
                "id_3",
                "profile_id_3",
                "notebook_id_3",
                "title_3",
                1111,
                2222,
                "content3",
                false
        );

        noteEntities = new ArrayList<>();
        noteEntities.add(noteEntity1);
        noteEntities.add(noteEntity2);
        noteEntities.add(noteEntity3);

        noteRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldAddAndGetEntityById() {
        noteRepository.add(noteEntity1);
        NoteEntity noteEntity11 = noteRepository.get(noteEntity1.getId());
        assertThat(noteEntity1).isEqualToComparingFieldByField(noteEntity11);

        noteRepository.add(noteEntity2);
        NoteEntity noteEntity22 = noteRepository.get(noteEntity2.getId());
        assertThat(noteEntity2).isEqualToComparingFieldByField(noteEntity22);

        noteRepository.add(noteEntity3);
        NoteEntity noteEntity33 = noteRepository.get(noteEntity3.getId());
        assertThat(noteEntity3).isEqualToComparingFieldByField(noteEntity33);
    }

    @Test
    public void repositoryShouldAddAndGetEntities() {
        noteRepository.add(noteEntities);

        List<NoteEntity> notebookEntities1 = noteRepository.get(1, 3);

        assertThat(noteEntities).hasSameSizeAs(notebookEntities1);
        assertThat((Object) noteEntities)
                .isEqualToComparingFieldByFieldRecursively(notebookEntities1);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        noteRepository.add(noteEntity1);

        noteEntity1.setTitle("new title");

        noteRepository.update(noteEntity1);

        NoteEntity noteEntity = noteRepository.get(noteEntity1.getId());
        assertThat(noteEntity).isEqualToComparingFieldByField(noteEntity1);

    }

    @Test(expected = NullPointerException.class)
    public void repositoryShouldRemoveEntity() {
        noteRepository.add(noteEntity1);

        noteRepository.remove(noteEntity1.getId());

        noteRepository.get(noteEntity1.getId());
    }

    @After
    public void finish() {
        noteRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
