package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NotesRepositoryTest {

    private NotesRepository notesRepository;

    private Note note1;

    private Note note2;

    private Note note3;

    private Notebook notebook;

    private List<Note> notes;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        ProfilesRepository profilesRepository = new ProfilesRepository();
        profilesRepository.openDatabaseConnection();
        profilesRepository.add(new Profile("profile_id_1", "first profile"));
        profilesRepository.add(new Profile("profile_id_2", "second profile"));
        profilesRepository.closeDatabaseConnection();


        notesRepository = new NotesRepository();

        notebook = new Notebook("notebook_id_1", "profile_id_1", null, "notebook1", 1111, 2222, 0);
        NotebooksRepository notebooksRepository = new NotebooksRepository();
        notebooksRepository.openDatabaseConnection();
        notebooksRepository.add(notebook);
        notebooksRepository.closeDatabaseConnection();

        note1 = new Note(
                "note_id_1",
                "profile_id_1",
                "notebook_id_1",
                "title_1",
                1111,
                2222,
                "content1",
                false
        );

        note2 = new Note(
                "note_id_2",
                "profile_id_1",
                "notebook_id_1",
                "title_2",
                1111,
                2222,
                "content2",
                false
        );

        note3 = new Note(
                "note_id_3",
                "profile_id_2",
                "notebook_id_2",
                "title_3",
                1111,
                2222,
                "content3",
                false
        );

        notes = new ArrayList<>();
        Arrays.asList(note1, note2, note3).forEach(note -> notes.add(note));

        notesRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldGetEntityById() {
        notesRepository.add(note1);
        Optional<Note> noteOptional = notesRepository.get(note1.getId());
        assertThat(noteOptional.isPresent()).isTrue();
        assertThat(noteOptional.get()).isEqualToComparingFieldByField(note1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        notesRepository.add(notes);

        List<Note> noteEntities1 = notesRepository
                .getByProfileId(note1.getProfileId(), 1, 3);

        assertThat(noteEntities1.size()).isNotEqualTo(notes.size());
        assertThat(noteEntities1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) noteEntities1).isEqualToComparingFieldByFieldRecursively(notes);
    }

    @Test
    public void repositoryShouldGetEntitiesByNotebookId() {
        notesRepository.add(notes);

        List<Note> noteEntities1 = notesRepository
                .getByNotebook(notebook, 1, 3);

        assertThat(noteEntities1.size()).isNotEqualTo(notes.size());
        assertThat(noteEntities1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) noteEntities1).isEqualToComparingFieldByFieldRecursively(notes);
    }

    @Test
    public void repositoryShouldGetNotesByTagId() {
        Tag tag = new Tag("tag_id_1", "profile_id_1", "tag1", 1111, 2222, 0);
        TagsRepository tagsRepository = new TagsRepository();
        tagsRepository.openDatabaseConnection();
        tagsRepository.add(tag);
        tagsRepository.closeDatabaseConnection();

        notesRepository.add(note1);
        notesRepository.add(note2);

        notesRepository.addTagsToNote(note1, Collections.singletonList(tag));
        notesRepository.addTagsToNote(note2, Collections.singletonList(tag));

        List<Note> notes1 = notesRepository.getByTag(tag);

        assertThat(notes1.size()).isNotEqualTo(notes.size());
        assertThat(notes1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) notes1).isEqualToComparingFieldByFieldRecursively(notes);
    }

    @Test
    public void repositoryShouldRemoveTagsOfNote() {
        Tag tag = new Tag("tag_id_1", "profile_id_1", "tag1", 1111, 2222, 0);
        TagsRepository tagsRepository = new TagsRepository();
        tagsRepository.openDatabaseConnection();
        tagsRepository.add(tag);
        tagsRepository.closeDatabaseConnection();



        notesRepository.add(note1);
        notesRepository.add(note2);

        notesRepository.addTagsToNote(note1, Collections.singletonList(tag));
        notesRepository.addTagsToNote(note2, Collections.singletonList(tag));

        notesRepository.removeTagsFromNote(note1, Collections.singletonList(tag));

        List<Note> notes1 = notesRepository.getByTag(tag);

        assertThat(notes1.size()).isNotEqualTo(notes.size());
        assertThat(notes1.size()).isEqualTo(notes.size() - 2);

        notes.remove(note1);
        notes.remove(note3);
        assertThat((Object) notes1).isEqualToComparingFieldByFieldRecursively(notes);
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

        notesRepository.remove(note1);

        assertThat(notesRepository.get(note1.getId()).isPresent()).isFalse();
    }

    @After
    public void finish() {
        notesRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
