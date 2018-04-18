package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.valhallalabs.laverna.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NoteRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NoteRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NotebookRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.TagRepositoryImpl;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.google.common.base.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
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
        DatabaseManager.initializeInstance(InstrumentationRegistry.getTargetContext());

        ProfileRepositoryImpl profilesRepository = new ProfileRepositoryImpl();
        profilesRepository.openDatabaseConnection();
        profile = new Profile("profile_id_1", "first profile");
        profilesRepository.add(profile);
        profilesRepository.add(new Profile("profile_id_2", "second profile"));
        profilesRepository.closeDatabaseConnection();


        noteRepository = new NoteRepositoryImpl();

        notebook = new Notebook("notebook_id_1", "profile_id_1", null, "notebook1", 1111, 2222, 0, false);
        NotebookRepositoryImpl notebooksRepository = new NotebookRepositoryImpl();
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
                "content1",
                false,
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
                "content2",
                false,
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
                "content3",
                false,
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
                .getByProfile(profile.getId(),new PaginationArgs());

        assertThat(noteEntities1.size()).isNotEqualTo(notes.size());
        assertThat(noteEntities1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) noteEntities1).isEqualToComparingFieldByField(notes);
    }

    @Test
    public void repositoryShouldGetEntitiesByNotebookId() {
        noteRepository.add(note1);
        noteRepository.add(note2);
        noteRepository.add(note3);

        List<Note> noteEntities1 = noteRepository
                .getByNotebook(notebook.getId(), new PaginationArgs());

        assertThat(noteEntities1.size()).isNotEqualTo(notes.size());
        assertThat(noteEntities1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) noteEntities1).isEqualToComparingFieldByField(notes);
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

        List<Note> notes1 = noteRepository.getByTag(tag.getId(), new PaginationArgs());

        assertThat(notes1.size()).isNotEqualTo(notes.size());
        assertThat(notes1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) notes1).isEqualToComparingFieldByField(notes);
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

        List<Note> notes1 = noteRepository.getByTag(tag.getId(), new PaginationArgs());

        assertThat(notes1.size()).isNotEqualTo(notes.size());
        assertThat(notes1.size()).isEqualTo(notes.size() - 2);

        notes.remove(note1);
        notes.remove(note3);
        assertThat((Object) notes1).isEqualToComparingFieldByField(notes);
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

        List<Note> result1 = noteRepository.getByTitle(profile.getId(), "title", new PaginationArgs());

        assertThat(result1).hasSize(2);

        List<Note> result2 = noteRepository.getByTitle(profile.getId(), "title_1", new PaginationArgs());

        assertThat(result2).hasSize(1);
    }

    @After
    public void finish() {
        noteRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
