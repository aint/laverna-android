package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NoteRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NoteRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NotebookRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.TagRepositoryImpl;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NoteRepositoryTest {

    private NoteRepository noteRepository;

    private Note note1;

    private Note note2;

    private Note note3;

    private Notebook notebook;

    private Profile profile;

    private List<Note> notes;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        ProfileRepositoryImpl profilesRepository = new ProfileRepositoryImpl();
        profilesRepository.openDatabaseConnection();
        profile = new Profile("profile_id_1", "first profile");
        profilesRepository.add(profile);
        profilesRepository.add(new Profile("profile_id_2", "second profile"));
        profilesRepository.closeDatabaseConnection();


        noteRepository = new NoteRepositoryImpl();

        notebook = new Notebook("notebook_id_1", "profile_id_1", Optional.absent(), "notebook1", 1111, 2222, 0);
        NotebookRepositoryImpl notebooksRepository = new NotebookRepositoryImpl();
        notebooksRepository.openDatabaseConnection();
        notebooksRepository.add(notebook);
        notebooksRepository.closeDatabaseConnection();

        note1 = new Note(
                "note_id_1",
                "profile_id_1",
                Optional.of("notebook_id_1"),
                "title_1",
                1111,
                2222,
                "content1",
                "content1",
                false
        );

        note2 = new Note(
                "note_id_2",
                "profile_id_1",
                Optional.of("notebook_id_1"),
                "title_2",
                1111,
                2222,
                "content2",
                "content2",
                false
        );

        note3 = new Note(
                "note_id_3",
                "profile_id_2",
                Optional.of("notebook_id_2"),
                "title_3",
                1111,
                2222,
                "content3",
                "content3",
                false
        );

        notes = new ArrayList<>();
        Arrays.asList(note1, note2, note3).forEach(note -> notes.add(note));

        noteRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldGetEntityById() {
        noteRepository.add(note1);
        Optional<Note> noteOptional = noteRepository.getById(note1.getId());
        assertThat(noteOptional.isPresent()).isTrue();
        assertThat(noteOptional.get()).isEqualToComparingFieldByField(note1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        noteRepository.add(note1);
        noteRepository.add(note2);
        noteRepository.add(note3);

        List<Note> noteEntities1 = noteRepository
                .getByProfile(profile.getId(), 1, 3);

        assertThat(noteEntities1.size()).isNotEqualTo(notes.size());
        assertThat(noteEntities1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) noteEntities1).isEqualToComparingFieldByFieldRecursively(notes);
    }

    @Test
    public void repositoryShouldGetEntitiesByNotebookId() {
        noteRepository.add(note1);
        noteRepository.add(note2);
        noteRepository.add(note3);

        List<Note> noteEntities1 = noteRepository
                .getByNotebook(notebook.getId(), 1, 3);

        assertThat(noteEntities1.size()).isNotEqualTo(notes.size());
        assertThat(noteEntities1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) noteEntities1).isEqualToComparingFieldByFieldRecursively(notes);
    }

    @Test
    public void repositoryShouldGetNotesByTagId() {
        Tag tag = new Tag("tag_id_1", "profile_id_1", "tag1", 1111, 2222, 0);
        TagRepositoryImpl tagsRepository = new TagRepositoryImpl();
        tagsRepository.openDatabaseConnection();
        tagsRepository.add(tag);
        tagsRepository.closeDatabaseConnection();

        noteRepository.add(note1);
        noteRepository.add(note2);

        noteRepository.addTagToNote(note1.getId(), tag.getId());
        noteRepository.addTagToNote(note2.getId(), tag.getId());

        List<Note> notes1 = noteRepository.getByTag(tag.getId(), 1, 5);

        assertThat(notes1.size()).isNotEqualTo(notes.size());
        assertThat(notes1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) notes1).isEqualToComparingFieldByFieldRecursively(notes);
    }

    @Test
    public void repositoryShouldRemoveTagsOfNote() {
        Tag tag = new Tag("tag_id_1", "profile_id_1", "tag1", 1111, 2222, 0);
        TagRepositoryImpl tagsRepository = new TagRepositoryImpl();
        tagsRepository.openDatabaseConnection();
        tagsRepository.add(tag);
        tagsRepository.closeDatabaseConnection();



        noteRepository.add(note1);
        noteRepository.add(note2);

        noteRepository.addTagToNote(note1.getId(), tag.getId());
        noteRepository.addTagToNote(note2.getId(), tag.getId());

        noteRepository.removeTagFromNote(note1.getId(), tag.getId());

        List<Note> notes1 = noteRepository.getByTag(tag.getId(), 1, 5);

        assertThat(notes1.size()).isNotEqualTo(notes.size());
        assertThat(notes1.size()).isEqualTo(notes.size() - 2);

        notes.remove(note1);
        notes.remove(note3);
        assertThat((Object) notes1).isEqualToComparingFieldByFieldRecursively(notes);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        noteRepository.add(note1);

        note1.setTitle("new title");

        noteRepository.update(note1);

        Optional<Note> noteOptional = noteRepository.getById(note1.getId());
        assertThat(noteOptional.get()).isEqualToComparingFieldByField(note1);

    }

    @Test
    public void repositoryShouldRemoveEntity() {
        noteRepository.add(note1);

        noteRepository.remove(note1.getId());

        assertThat(noteRepository.getById(note1.getId()).isPresent()).isFalse();
    }

    @Test
    public void repositoryShouldGetNoteByName() {
        noteRepository.add(note1);
        note2.setTitle("title2");
        noteRepository.add(note2);

        List<Note> result1 = noteRepository.getByTitle(profile.getId(), "title", 1, 5);

        assertThat(result1).hasSize(2);

        List<Note> result2 = noteRepository.getByTitle(profile.getId(), "title_1", 1, 5);

        assertThat(result2).hasSize(1);
    }

    @After
    public void finish() {
        noteRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
