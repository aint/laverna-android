package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
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

    private NotesRepository notesRepository;

    private Note note1;

    private Note note2;

    private Note note3;

    private List<Note> notes;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        notesRepository = new NotesRepository();

        note1 = new Note(
                "id_1",
                "profile_id_1",
                "notebook_id_1",
                "title_1",
                1111,
                2222,
                "content1",
                false
        );

        note2 = new Note(
                "id_2",
                "profile_id_1",
                "notebook_id_1",
                "title_2",
                1111,
                2222,
                "content2",
                false
        );

        note3 = new Note(
                "id_3",
                "profile_id_2",
                "notebook_id_2",
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

        notesRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldGetEntityById() {
        notesRepository.add(note1);
        Optional<Note> noteOptional = notesRepository.get(note1.getId());
        assertThat(noteOptional.get());
        assertThat(noteOptional.get()).isEqualToComparingFieldByField(note1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        notesRepository.add(notes);

        List<Note> noteEntities1 = notesRepository
                .getNotesByProfileId(note1.getProfileId(), 1, 3);

        assertThat(noteEntities1.size()).isNotEqualTo(notes.size());
        assertThat(noteEntities1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) noteEntities1).isEqualToComparingFieldByFieldRecursively(notes);
    }

    @Test
    public void repositoryShouldGetEntitiesByNotebookId() {
        notesRepository.add(notes);

        List<Note> noteEntities1 = notesRepository
                .getNotesByNotebookId(note1.getNotebookId(), 1, 3);

        assertThat(noteEntities1.size()).isNotEqualTo(notes.size());
        assertThat(noteEntities1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) noteEntities1).isEqualToComparingFieldByFieldRecursively(notes);
    }


    @Test
    public void repositoryShouldUpdateEntity() {
        notesRepository.add(note1);

        note1.setTitle("new title");

        notesRepository.update(note1);

        Optional<Note> noteOptional = notesRepository.get(note1.getId());
        assertThat(noteOptional.get()).isEqualToComparingFieldByField(note1);

    }

    @Test
    public void repositoryShouldRemoveEntity() {
        notesRepository.add(note1);

        notesRepository.remove(note1.getId());

        assertThat(notesRepository.get(note1.getId()).isPresent()).isFalse();
    }

    @After
    public void finish() {
        notesRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
