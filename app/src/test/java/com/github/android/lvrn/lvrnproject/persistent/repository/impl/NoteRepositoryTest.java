package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.NoteEntity;
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
public class NoteRepositoryTest {

    private NotesRepository noteRepository;

    private NoteEntity note1;

    private NoteEntity note2;

    private NoteEntity note3;

    private List<NoteEntity> notes;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        noteRepository = new NotesRepository();

        note1 = new NoteEntity(
                "id_1",
                "profile_id_1",
                "notebook_id_1",
                "title_1",
                1111,
                2222,
                "content1",
                false
        );

        note2 = new NoteEntity(
                "id_2",
                "profile_id_2",
                "notebook_id_2",
                "title_2",
                1111,
                2222,
                "content2",
                false
        );

        note3 = new NoteEntity(
                "id_3",
                "profile_id_3",
                "notebook_id_3",
                "title_3",
                1111,
                2222,
                "content3",
                false
        );

        notes = new ArrayList<>();
        notes.add(note1);
        notes.add(note2);
        notes.add(note3);

        noteRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldAddAndGetEntityById() {
        noteRepository.add(note1);
        Optional<NoteEntity> noteOptional = noteRepository.get(note1.getId());
        assertThat(noteOptional.get());
        assertThat(noteOptional.get()).isEqualToComparingFieldByField(note1);
    }

    @Test
    public void repositoryShouldAddAndGetEntities() {
        noteRepository.add(notes);

        List<NoteEntity> notebookEntities1 = noteRepository.get(1, 3);

        assertThat(notes).hasSameSizeAs(notebookEntities1);
        assertThat((Object) notes)
                .isEqualToComparingFieldByFieldRecursively(notebookEntities1);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        noteRepository.add(note1);

        note1.setTitle("new title");

        noteRepository.update(note1);

        Optional<NoteEntity> noteOptional = noteRepository.get(note1.getId());
        assertThat(noteOptional.get()).isEqualToComparingFieldByField(note1);

    }

    @Test
    public void repositoryShouldRemoveEntity() {
        noteRepository.add(note1);

        noteRepository.remove(note1.getId());

        assertThat(noteRepository.get(note1.getId()).isPresent()).isFalse();
    }

    @After
    public void finish() {
        noteRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
